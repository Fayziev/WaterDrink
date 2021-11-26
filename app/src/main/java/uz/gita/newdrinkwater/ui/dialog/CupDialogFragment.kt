package uz.gita.newdrinkwater.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.databinding.CustomDialogFragmentBinding

class CupDialogFragment : DialogFragment(R.layout.cup_dialog_fragment) {
    private val binding by viewBinding(CustomDialogFragmentBinding::bind)
    private var listener:(()->Unit)?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            listener?.invoke()
            dismiss()
        }, 3000)
        isCancelable=false
    }
    fun setListener(f:()->Unit){
        listener=f
    }
}