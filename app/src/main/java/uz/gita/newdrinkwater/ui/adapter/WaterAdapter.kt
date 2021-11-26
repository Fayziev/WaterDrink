package uz.gita.newdrinkwater.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.database.entity.Data


class WaterAdapter(val data: ArrayList<Data>) : RecyclerView.Adapter<WaterAdapter.VH>() {
    private var trashListener: ((Int,Data) -> Unit)? = null

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val time: TextView = view.findViewById(R.id.time)
        private val cup: TextView = view.findViewById(R.id.capacity)
        private val trashBox: ImageView = view.findViewById(R.id.trashBox)

        init {
            trashBox.setOnClickListener {
                trashListener?.invoke(absoluteAdapterPosition,data[absoluteAdapterPosition])
            }
        }

        fun bind() {
            time.text = data[absoluteAdapterPosition].time
            cup.text = data[absoluteAdapterPosition].size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    fun setTrashListener(f: (Int,Data) -> Unit) {
        trashListener = f
    }

    override fun getItemCount(): Int = data.size
}