package uz.gita.newdrinkwater.ui.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.database.SharedPref
import uz.gita.newdrinkwater.databinding.GenderDialogBinding

class GenderDialogFragment : DialogFragment(R.layout.gender_dialog) {
    private val pref = SharedPref.getSharedPref()
    private val binding by viewBinding(GenderDialogBinding::bind)
    private var position = pref.pos
    private var listener: ((Boolean) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (position) {
            binding.girlTransparent.visibility = View.VISIBLE
            binding.boyTransparent.visibility = View.GONE
            val male = "Male"
            val female = "Female"
            binding.male.text = male
            binding.male.setTextColor(Color.parseColor("#3BA1FE"))
            binding.female.text = female
            binding.female.setTextColor(Color.parseColor("#D6D6D6"))
        } else {
            binding.boyTransparent.visibility = View.VISIBLE
            binding.girlTransparent.visibility = View.GONE
            val female = "Female"
            val male = "Male"
            binding.male.text = male
            binding.male.setTextColor(Color.parseColor("#D6D6D6"))
            binding.female.text = female
            binding.female.setTextColor(Color.parseColor("#3BA1FE"))
        }
        binding.boy.setOnClickListener {
            binding.girlTransparent.visibility = View.VISIBLE
            binding.boyTransparent.visibility = View.GONE
            val male = "Male"
            val female = "Female"
            binding.male.text = male
            binding.male.setTextColor(Color.parseColor("#3BA1FE"))
            binding.female.text = female
            binding.female.setTextColor(Color.parseColor("#D6D6D6"))
            position = true
            pref.pos = position
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
            position = false
            pref.pos = position
        }
        binding.select.setOnClickListener {
            listener?.invoke(position)
            dismiss()
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
    }

    fun setListener(f: (Boolean) -> Unit) {
        listener = f
    }
}