package com.example.kite.dateandtime.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.R
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
            binding.timeSlot = list[position].time?.let { convertTo12Hours(it) }

            binding.root.setOnClickListener {
                selected = position
                list[position].time?.let { it1 ->
                    convertTo12Hours(it1)?.let { it2 ->
                        lis.onClick(
                            position, list[position],
                            it2
                        )
                    }
                }
                notifyItemChanged(selected)
                notifyDataSetChanged()
                Toast.makeText(context, "hello : $position", Toast.LENGTH_SHORT).show()
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
        val binding: ItemTimeSlotDayBinding =
            ItemTimeSlotDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        if (list[position].available == false) {
            holder.binding.txtTSTime.isClickable = false
            holder.binding.txtTSTime.apply {
                background =
                    (ContextCompat.getDrawable(context, R.drawable.bg_card_grey_round))
                setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }

        if (selected == position) {
            holder.binding.txtTSTime.apply {
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                background =
                    (ContextCompat.getDrawable(context, R.drawable.bg_card_red_round))
            }
        } else {
            holder.binding.txtTSTime.apply {
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.black
                    )
                )
                background =
                    (ContextCompat.getDrawable(context, R.drawable.bg_card_black_round))
            }

        }

    }
}