package com.example.kite.bikelisting.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.R
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.databinding.ItemVehicleListingBinding

class Adapter(
    private val context: Context, private var onCellClicked: OnCellClicked
) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var list = ArrayList<BikeListingResponse.Data.VehicleDetail>()
    var selected = -1

    fun setList(list: ArrayList<BikeListingResponse.Data.VehicleDetail>) {
        this.list = list
    }

    inner class ViewHolder(val binding: ItemVehicleListingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            binding.data = list[position]
            binding.root.setOnClickListener {
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
        val binding: ItemVehicleListingBinding =
            ItemVehicleListingBinding.inflate(
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
            onCellClicked.isClicked(position)
            holder.binding.constraintLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.bg_main
                )
            )
            holder.binding.txtVehicleAvailibility.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.binding.txtVehicleCount.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.binding.txtVehicleModel.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.binding.txtVehicleRate.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        } else {
            holder.binding.constraintLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.bg_card_gray
                )
            )
            holder.binding.txtVehicleAvailibility.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.binding.txtVehicleCount.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.binding.txtVehicleModel.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.binding.txtVehicleRate.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
        }

    }

}