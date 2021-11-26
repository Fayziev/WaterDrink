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
import uz.gita.newdrinkwater.databinding.FragmentBadtimeBinding
import java.util.*


class BedtimeFragment : Fragment(R.layout.fragment_badtime) {
    private val binding by viewBinding(FragmentBadtimeBinding::bind)
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
            binding.genderSrc.setImageResource(R.drawable.sleep_boy)
            binding.gender.text = "Male"
        } else {
            binding.genderSrc.setImageResource(R.drawable.sleep_girl)
            binding.gender.text = "Female"
        }
        val bedTime = pref.dateWake
        binding.timeDay.text = bedTime
        binding.timeDay.setTextColor(Color.parseColor("#499BE1"))

        val weight = pref.weight
        binding.weight.text = weight
        binding.weight.setTextColor(Color.parseColor("#499BE1"))

        binding.timeDay.text = pref.dateWake
        binding.timeDay.setTextColor(Color.parseColor("#499BE1"))
        binding.timeNight.setTextColor(Color.parseColor("#499BE1"))

        binding.nextBtn.setOnClickListener {
            if (binding.selectAdd.text.isEmpty()) {
                pref.dateBed = "00:00"
            }
            pref.isStart = true
            findNavController().navigate(R.id.action_bedtimeFragment_to_menuFragment)
        }
        binding.backCircle.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.selectWeight.setOnClickListener {
            showTimePicker()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showTimePicker() {

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(22)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(requireFragmentManager(), "foxandroid")

        picker.addOnPositiveButtonClickListener {

            val string =
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
            pref.dateBed = time
            binding.timeNight.text = time
        }
    }
}