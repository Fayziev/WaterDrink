package uz.gita.newdrinkwater.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.database.SharedPref
import uz.gita.newdrinkwater.databinding.FragmentGenderBinding

class GenderFragment : Fragment(R.layout.fragment_gender) {
    private val binding by viewBinding(FragmentGenderBinding::bind)
    private var pos = true
    private var pref = SharedPref.getSharedPref()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.boy.setOnClickListener {
            binding.girlTransparent.visibility = View.VISIBLE
            binding.boyTransparent.visibility = View.GONE
            val male = "Male"
            val female = "Female"
            binding.male.text = male
            binding.male.setTextColor(Color.parseColor("#3BA1FE"))
            binding.female.text = female
            binding.female.setTextColor(Color.parseColor("#D6D6D6"))
            binding.gender.text = male
            pos = true
        }
        binding.girl.setOnClickListener {

            binding.boyTransparent.visibility = View.VISIBLE
            binding.girlTransparent.visibility = View.GONE
            val female = "Female"
            val male = "Male"
            binding.male.text = male
            binding.male.setTextColor(Color.parseColor("#D6D6D6"))
            binding.female.text = female
            binding.female.setTextColor(Color.parseColor("#3BA1FE"))
            binding.gender.text = female
            pos = false
        }
        binding.nextBtn.setOnClickListener {
            pref.pos = pos
            if (pos) {
                pref.gender = "Male"
            } else pref.gender = "Female"
            findNavController().navigate(R.id.action_genderFragment_to_weightFragment)
        }
    }
}