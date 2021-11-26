package uz.gita.newdrinkwater.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.database.SharedPref
import uz.gita.newdrinkwater.databinding.FragmentWakeUpBinding
import java.text.SimpleDateFormat
import java.util.*


class WakeupFragment : Fragment(R.layout.fragment_wake_up) {
    private val binding by viewBinding(FragmentWakeUpBinding::bind)
    private var pref = SharedPref.getSharedPref()
    private lateinit var picker: MaterialTimePicker
    private var hour = 0
    private var time = ""
    private var minute = 0
    private lateinit var calendar: Calendar

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pos = pref.pos
        if (pos) {
            binding.genderSrc.setImageResource(R.drawable.wake_up_boy)
            binding.gender.text = "Male"
        } else {
            binding.genderSrc.setImageResource(R.drawable.wake_up_girl)
            binding.gender.text = "Female"
        }
        val weight = pref.weight

        binding.weight.text = weight
        binding.weight.setTextColor(Color.parseColor("#499BE1"))


        binding.selectAdd.setOnClickListener {
            showTimePicker()
        }

        binding.timeDay.setTextColor(Color.parseColor("#499BE1"))
        binding.nextBtn.setOnClickListener {
            if (binding.selectAdd.text.isEmpty()) {
                pref.dateWake = "06:00"
            }
            findNavController().navigate(R.id.action_wakeupFragment_to_bedtimeFragment)
        }
        binding.backCircle.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showTimePicker() {


        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(6)
            .setMinute(0)
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
            binding.selectAdd.text = string
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            time = string
            binding.timeDay.text = time
            pref.dateWake = time
        }
    }

}