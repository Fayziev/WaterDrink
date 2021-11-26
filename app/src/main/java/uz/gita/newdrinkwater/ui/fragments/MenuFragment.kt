package uz.gita.newdrinkwater.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.RoomData
import uz.gita.newdrinkwater.database.SharedPref
import uz.gita.newdrinkwater.database.entity.Data
import uz.gita.newdrinkwater.databinding.MenuLayoutBinding
import uz.gita.newdrinkwater.ui.DrinkWaterManager
import uz.gita.newdrinkwater.ui.adapter.WaterAdapter
import uz.gita.newdrinkwater.ui.dialog.CupDialogFragment
import uz.gita.newdrinkwater.ui.dialog.CustomDialogFragment
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MenuFragment : Fragment(R.layout.menu_layout) {
    private val binding by viewBinding(MenuLayoutBinding::bind)
    private val adapter by lazy { WaterAdapter(list) }
    private val dao = RoomData.getDatabase().getDao()
    private val list = dao.getAll() as ArrayList
    private val sharedPref = SharedPref.getSharedPref()
    private var count = sharedPref.progressCount
    private lateinit var request: PeriodicWorkRequest
    private val formatTime = SimpleDateFormat("HH:mm", Locale.getDefault())
    private var cup = sharedPref.cup

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.maxValue.text = "${sharedPref.targetWater} ml"
        binding.current.text = "$count ml"
        binding.circleProgressBar1.progressMax = sharedPref.targetWater.toFloat()
        binding.circleProgressBar1.progress = count.toFloat()
        binding.listView.adapter = adapter
        binding.listView.layoutManager = LinearLayoutManager(requireContext())
        notFound()
        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
        }
        binding.circle.setOnClickListener {
            val dialogFragment = CustomDialogFragment()
            dialogFragment.setListener { cupValue ->
                cup = cupValue
                sharedPref.cup = cupValue
            }
            dialogFragment.show(parentFragmentManager, "dialog")
        }

        adapter.setTrashListener { pos, data ->
            val alertDialog = AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setMessage("Do you really want to delete it?")
                .setTitle("Delete")
                .setPositiveButton(
                    "Yes"
                ) { dialog, which ->
                    dao.delete(data)
                    list.removeAt(pos)
                    adapter.notifyItemRemoved(pos)
                    val size = data.size.substring(0, 3)
                    count -= size.toInt()
                    binding.current.text = "$count ml"
                    binding.circleProgressBar1.progress -= size.toInt()
                    notFound()
                    dialog.dismiss()
                }
                .setNegativeButton(
                    "No"
                ) { dialog, which ->
                    dialog.dismiss()
                }
            alertDialog.show()
        }

        binding.circleloading.setOnClickListener {

            binding.circleProgressBar1.progress += cup
            count += cup
            val cupDialog = CupDialogFragment()
            cupDialog.show(childFragmentManager, "cup")
            binding.current.text = "$count ml"
            myWorkManager()
            val time = formatTime.format(Date())
            val data = Data(0, time, "$cup ml")
            dao.insert(data)
            loadData()
            notFound()
            adapter.notifyDataSetChanged()
        }
    }

    private fun notFound() {
        if (adapter.itemCount == 0) binding.notFound.visibility = View.VISIBLE
        else binding.notFound.visibility = View.GONE
    }

    private fun myWorkManager() {
        createNotificationChannel()
        request = PeriodicWorkRequestBuilder<DrinkWaterManager>(1, TimeUnit.HOURS)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(request)
    }

    override fun onPause() {
        super.onPause()
        sharedPref.progressCount = count
    }


    private fun loadData() {
        list.clear()
        list.addAll(dao.getAll())
        adapter.notifyDataSetChanged()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "foxandroidReminderChannel"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("foxandroid", name, importance)
            channel.description = description
            val notificationManager = ContextCompat.getSystemService(
                requireContext(), NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)
        }
    }


}

