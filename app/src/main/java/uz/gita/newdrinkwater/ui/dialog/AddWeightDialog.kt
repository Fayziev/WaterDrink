package uz.gita.newdrinkwater.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.databinding.DialogWeightAddBinding

class AddWeightDialog : DialogFragment(R.layout.dialog_weight_add) {

    private var _binding: DialogWeightAddBinding? = null
    private val binding get() = _binding!!
    private var listener: ((Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogWeightAddBinding.inflate(inflater, container, false)
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

        binding.addButton.setOnClickListener {
            val weight = binding.inputUserEditText.text.toString()
            if (weight.isNotEmpty()) {
                if (weight.toInt() >= 1) {
                    listener?.invoke(weight.toInt())
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Please enter a value of more than 1 kg", Toast.LENGTH_SHORT).show()
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