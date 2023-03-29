package com.example.kite.history.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.databinding.ItemRideHistoryBinding
import com.example.kite.history.lis.OnRideClick
import com.example.kite.history.model.RideHistoryResponse
import com.example.kite.utils.Util
import java.time.ZonedDateTime

class RideHistoryAdapter(val onRideClick: OnRideClick) :
    RecyclerView.Adapter<RideHistoryAdapter.ViewHolder>() {
    private var list = ArrayList<RideHistoryResponse.RideHistory>()
    var selected = -1

    fun setList(list: ArrayList<RideHistoryResponse.RideHistory>) {
        this.list = list
    }

    inner class ViewHolder(val binding: ItemRideHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.rideHistory = list[position]
            binding.root.setOnClickListener {
                list[position].bookingId?.let { it1 -> onRideClick.onClick(it1) }
                selected = position
                notifyDataSetChanged()
            }
        }
    }


    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRideHistoryBinding =
            ItemRideHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.txtIRHDate.text = list[position].startDate?.let { Util.getDateFromTimeString(
            Util.getMillisFromTime(it)) }

    }

}