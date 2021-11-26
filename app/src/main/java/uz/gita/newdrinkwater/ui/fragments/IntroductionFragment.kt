package uz.gita.newdrinkwater.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.databinding.FragmentIntroductionBinding

class IntroductionFragment : Fragment(R.layout.fragment_introduction) {
    private val binding by viewBinding(FragmentIntroductionBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.letsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introductionFragment_to_genderFragment)
        }
    }
}