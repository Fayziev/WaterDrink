package uz.gita.newdrinkwater.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.database.SharedPref
import uz.gita.newdrinkwater.databinding.DialogTargetWaterBinding

class EditTargetWaterDialog : DialogFragment(R.layout.dialog_target_water) {
    private val pref = SharedPref.getSharedPref()
    private var _binding: DialogTargetWaterBinding? = null
    private val binding get() = _binding!!
    private var listener: ((Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogTargetWaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.inputUserEditText.setText(pref.targetWater.toString())
        binding.addButton.setOnClickListener {
            val water = binding.inputUserEditText.text.toString()
            if (water.isNotEmpty()) {
                if (water.toInt() >= 250) {
                    listener?.invoke(water.toInt())
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Please enter a value of more than 250 ml", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter your weight", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        isCancelable = true
    }

    fun setListener(f: (Int) -> Unit) {
        listener = f
    }
}