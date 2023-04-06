package com.example.kite.dateandtime.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.databinding.ItemTimeSlotDayBinding
import com.example.kite.dateandtime.listner.OnCellClicked
import com.example.kite.dateandtime.model.TimeSlotResponse
import java.text.SimpleDateFormat
import java.util.*

class DayAdapter(val context: Context, val lis: OnCellClicked) :
    RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    private var list = ArrayList<TimeSlotResponse.AllTimeSlot>()
    private var selected = -1

    fun setList(list: ArrayList<TimeSlotResponse.AllTimeSlot>) {
        this.list = list
    }

    inner class ViewHolder(val binding: ItemTimeSlotDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            list[position].isItemSelected = selected == position
            list[position].convertedTime = list[position].time?.let { convertTo12Hours(it) }
            binding.model = list[position]

            binding.root.setOnClickListener {
                if (list[position].available != false) {
                    list[position].isItemSelected = true
                    selected = position
                    lis.onClick(position, list[position])
                    notifyDataSetChanged()
                }
            }
        }
    }


    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun convertTo12Hours(militaryTime: String): String? {
        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(militaryTime)
        return date?.let { outputFormat.format(it) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTimeSlotDayBinding = ItemTimeSlotDayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}