package uz.gita.newdrinkwater.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.database.SharedPref
import uz.gita.newdrinkwater.databinding.FragmentWeightBinding
import uz.gita.newdrinkwater.ui.dialog.AddWeightDialog


class WeightFragment : Fragment(R.layout.fragment_weight) {
    private val binding by viewBinding(FragmentWeightBinding::bind)

    private var pref = SharedPref.getSharedPref()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pos = pref.pos
        if (pos) {
            binding.genderSrc.setImageResource(R.drawable.boy_weight)
            binding.gender.text = "Male"
        } else {
            binding.genderSrc.setImageResource(R.drawable.girl_weight)
            binding.gender.text = "Female"
        }
        binding.selectWeight.setOnClickListener {

            val addWeight = AddWeightDialog()
            addWeight.show(childFragmentManager, "weight")
            addWeight.setListener {
                binding.selectAdd.text = "$it kg"
                pref.weight = it.toString()
                binding.weight.text = it.toString()
            }
        }

        binding.nextBtn.setOnClickListener {
            if (binding.selectAdd.text.isEmpty()) {
                pref.weight = "0"
            }
            findNavController().navigate(R.id.action_weightFragment_to_wakeupFragment)
        }
        binding.backCircle.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}