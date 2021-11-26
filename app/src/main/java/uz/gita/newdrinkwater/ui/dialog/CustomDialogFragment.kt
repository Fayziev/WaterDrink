package uz.gita.newdrinkwater.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.database.SharedPref
import uz.gita.newdrinkwater.databinding.CustomDialogFragmentBinding

class CustomDialogFragment : DialogFragment(R.layout.custom_dialog_fragment) {
    private val binding by viewBinding(CustomDialogFragmentBinding::bind)
    private val pref = SharedPref.getSharedPref()
    private var cupValue = 100
    private var listener: ((Int) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (pref.cup) {
            100 -> {
                binding.cup1Back.visibility = View.GONE
            }
            150 -> {
                binding.cup2Back.visibility = View.GONE
            }
            200 -> {
                binding.cup3Back.visibility = View.GONE
            }
            250 -> {
                binding.cup4Back.visibility = View.GONE
            }
            300 -> {
                binding.cup5Back.visibility = View.GONE
            }
            500 -> {
                binding.cup6Back.visibility = View.GONE
            }
        }

        binding.select.setOnClickListener {
            listener?.invoke(cupValue)
            dismiss()
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
        binding.cup1.setOnClickListener {
            selectCup()
            binding.cup1Back.visibility = View.GONE
            cupValue = 100
        }
        binding.cup2.setOnClickListener {
            selectCup()
            binding.cup2Back.visibility = View.GONE
            cupValue = 150
        }
        binding.cup3.setOnClickListener {
            selectCup()
            binding.cup3Back.visibility = View.GONE
            cupValue = 200
        }
        binding.cup4.setOnClickListener {
            selectCup()
            binding.cup4Back.visibility = View.GONE
            cupValue = 250
        }
        binding.cup5.setOnClickListener {
            selectCup()
            binding.cup5Back.visibility = View.GONE
            cupValue = 300
        }
        binding.cup6.setOnClickListener {
            selectCup()
            binding.cup6Back.visibility = View.GONE
            cupValue = 500
        }
    }

    private fun selectCup() {
        binding.cup1Back.visibility = View.VISIBLE
        binding.cup2Back.visibility = View.VISIBLE
        binding.cup3Back.visibility = View.VISIBLE
        binding.cup4Back.visibility = View.VISIBLE
        binding.cup5Back.visibility = View.VISIBLE
        binding.cup6Back.visibility = View.VISIBLE
    }

    fun setListener(f: (Int) -> Unit) {
        listener = f
    }
}