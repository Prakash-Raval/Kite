package com.example.kite.dateandtime.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.databinding.ItemTimeSlotBinding
import com.example.kite.dateandtime.listner.OnCellClicked
import com.example.kite.dateandtime.model.HeaderModel
import com.example.kite.dateandtime.model.TimeSlotResponse

class DateAndTimeAdapter(val context: Context, val lis: OnCellClicked) :
    RecyclerView.Adapter<DateAndTimeAdapter.ViewHolder>() {
    private var header = ArrayList<HeaderModel>()
    private var adapter: DayAdapter? = null

    fun setList(
        header: ArrayList<HeaderModel>
    ) {
        this.header = header
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTimeSlotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

        }
    }


    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTimeSlotBinding =
            ItemTimeSlotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return header.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Toast.makeText(context, "SIze $position", Toast.LENGTH_SHORT).show()
        Log.i("Adapter", "onBindViewHolder: " + header[position])
        //holder.bind(position)
        if (header[position].isVisible == true) {
            holder.binding.txtTSDay.visibility = View.VISIBLE
            holder.binding.txtTSDay.text = header[position].header
        } else {
            holder.binding.txtTSDay.visibility = View.GONE
            holder.binding.viewDT1.visibility = View.GONE
        }

        for (i in 0 until header[position].list?.size!!) {
            header[position].list?.get(i)?.position = position

        }

        adapter = DayAdapter(holder.itemView.context, lis)
        adapter?.setList(header[position].list as ArrayList<TimeSlotResponse.AllTimeSlot>)
        holder.binding.rvSTDay.adapter = adapter
    }

}