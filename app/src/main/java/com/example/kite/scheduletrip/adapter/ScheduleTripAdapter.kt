package com.example.kite.scheduletrip.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.R
import com.example.kite.databinding.ItemScheduleTripDurationBinding
import com.example.kite.scheduletrip.listner.OnTripClick
import com.example.kite.scheduletrip.model.ScheduleTimeDuration
import com.example.kite.scheduletrip.model.ScheduleTripResponse

class ScheduleTripAdapter(val context: Context, val onTripClick: OnTripClick) :
    RecyclerView.Adapter<ScheduleTripAdapter.ViewHolder>() {
    private var list = ArrayList<ScheduleTripResponse.Data.TripDuration>()
    var selected = 0

    private var listTimeDuration = ArrayList<ScheduleTimeDuration>()

    fun setList(
        list: ArrayList<ScheduleTripResponse.Data.TripDuration>,
        listTimeDuration: ArrayList<ScheduleTimeDuration>
    ) {
        this.list = list
        this.listTimeDuration = listTimeDuration
    }

    inner class ViewHolder(val binding: ItemScheduleTripDurationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            binding.scheduleTripData = list[position]
            binding.root.setOnClickListener {
                selected = position
                notifyDataSetChanged()
                list[position].duration?.let { it1 -> onTripClick.onClickTrip(it1) }
            }
            if (list[position].duration == listTimeDuration[position].time) {
                binding.imgTimeDuration.setImageResource(listTimeDuration[position].image)
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
        val binding: ItemScheduleTripDurationBinding =
            ItemScheduleTripDurationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        if (selected == position) {

            holder.binding.txtTripDesc.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.binding.txtTripHours.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.binding.txtTripPricing.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.binding.clScheduleTrip.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.bg_main
                )
            )
        } else {
            holder.binding.txtTripDesc.setTextColor(ContextCompat.getColor(context, R.color.black))
            holder.binding.txtTripHours.setTextColor(ContextCompat.getColor(context, R.color.black))
            holder.binding.txtTripPricing.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.binding.clScheduleTrip.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.schedule_card_gray
                )
            )

        }

    }

}