package uz.gita.newdrinkwater.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.RoomData
import uz.gita.newdrinkwater.database.SharedPref
import uz.gita.newdrinkwater.databinding.FragmentSettingsBinding
import uz.gita.newdrinkwater.ui.dialog.EditTargetWaterDialog
import uz.gita.newdrinkwater.ui.dialog.EditWeightDialog
import uz.gita.newdrinkwater.ui.dialog.GenderDialogFragment
import java.util.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val pref = SharedPref.getSharedPref()
    private lateinit var picker: MaterialTimePicker
    private var hour = 0
    private var time: StringBuilder = StringBuilder()
    private var minute = 0
    private val dao = RoomData.getDatabase().getDao()
    private lateinit var calendar: Calendar

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.genderText.text = pref.gender
        binding.bottleText.text = pref.targetWater.toString()
        binding.weightText.text = "${pref.weight} kg"
        binding.wakeText.text = pref.dateWake
        binding.bedTime.text = pref.dateBed
        binding.reminderSwitch.isChecked = pref.reminderSwitch
        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.share.setOnClickListener {
            shareApp()
        }

        binding.bottleText.setOnClickListener {

            val target = EditTargetWaterDialog()
            target.setListener {
                pref.targetWater = it
                binding.bottleText.text = "$it ml"
            }
            target.show(childFragmentManager, "dialog")
        }

        binding.genderText.setOnClickListener {
            val genderDialog = GenderDialogFragment()
            genderDialog.setListener {
                if (it) {
                    pref.gender = "Male"
                    binding.genderText.text = "Male"
                } else {
                    pref.gender = "Female"
                    binding.genderText.text = "Female"
                }
            }
            genderDialog.show(childFragmentManager, "genderDialog")
        }

        binding.weightText.setOnClickListener {
            val weight = EditWeightDialog()
            weight.setListener {
                pref.weight = it.toString()
                binding.weightText.text = "${pref.weight} kg"
                val water = it * 42.222.toInt()
                pref.targetWater = water
                binding.bottleText.text = "$water ml"
            }
            weight.show(childFragmentManager, "weightDialog")
        }
        binding.wakeText.setOnClickListener {
            showTimePicker()
        }
        binding.bedTime.setOnClickListener {
            showTimePicker2()
        }

        binding.resetCheckBox.setOnClickListener {
            binding.resetCheckBox.isChecked = true

            if (binding.resetCheckBox.isChecked) {
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setCancelable(false)
                    .setMessage("Do you really want to reset data?")
                    .setTitle("Reset data")
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, which ->
                        pref.resetData = true
                        val data = dao.getAll()
                        for (i in data.indices) {
                            dao.delete(data[i])
                        }
                        pref.progressCount = 0
                        pref.cup = 100
                        pref.reminderSwitch = false
                        findNavController().navigate(R.id.action_settingsFragment_to_introductionFragment)
                        dialog.dismiss()
                    }
                    .setNegativeButton(
                        "No"
                    ) { dialog, which ->
                        binding.resetCheckBox.isChecked = false
                        dialog.dismiss()
                    }
                alertDialog.show()
            }

        }
        binding.reminderSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                pref.reminderSwitch = true
            } else {
                pref.reminderSwitch = false
                WorkManager.getInstance().cancelAllWorkByTag("wifiJob")
            }
        }
    }


    private fun shareApp() {
        try {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Your Subject")
            var sAux = "\nLet me recommend you this application\n\n"
            sAux = """
            ${sAux}https://play.google.com/store/apps/details?id=uz.gita.newdrinkwater 
            """.trimIndent() // here define package name of you app
            i.putExtra(Intent.EXTRA_TEXT, sAux)
            startActivity(Intent.createChooser(i, "choose one"))
        } catch (e: Exception) {
            Log.e(">>>", "Error: $e")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showTimePicker() {

        val hourCurrent = "${pref.dateWake[0]}${pref.dateWake[1]}"
        val minuteCurrent = "${pref.dateWake[3]}${pref.dateWake[4]}"
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hourCurrent.toInt())
            .setMinute(minuteCurrent.toInt())
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(requireFragmentManager(), "foxandroid")

        picker.addOnPositiveButtonClickListener {
            var string = ""
            string =
                String.format("%02d", picker.hour) + ":" + String.format(
                    "%02d",
                    picker.minute
                )
            hour = picker.hour
            minute = picker.minute
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            time.delete(0, time.toString().length)
            time.append(string)
            binding.wakeText.text = time
            pref.dateWake = time.toString()

        }
    }

    @SuppressLint("SetTextI18n")
    private fun showTimePicker2() {

        val hourCurrent = "${pref.dateBed[0]}${pref.dateBed[1]}"
        val minuteCurrent = "${pref.dateBed[3]}${pref.dateBed[4]}"
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hourCurrent.toInt())
            .setMinute(minuteCurrent.toInt())
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(requireFragmentManager(), "foxandroid")

        picker.addOnPositiveButtonClickListener {
            var string = ""
            string =
                String.format("%02d", picker.hour) + ":" + String.format(
                    "%02d",
                    picker.minute
                )
            hour = picker.hour
            minute = picker.minute
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            time.delete(0, time.toString().length)
            time.append(string)
            binding.bedTime.text = time
            pref.dateBed = time.toString()
        }
    }

}