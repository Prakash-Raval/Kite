package com.example.kite.bikelisting.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.databinding.ItemVehicleListingBinding


class Adapter(
    private var onCellClicked: OnCellClicked
) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var list = ArrayList<BikeListingResponse.VehicleDetail>()
    var selected = -1

    fun setList(list: ArrayList<BikeListingResponse.VehicleDetail>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemVehicleListingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            binding.data = list[position]
            binding.root.setOnClickListener {
                selected = position
                onCellClicked.isClicked(position)
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
        return ViewHolder(
            ItemVehicleListingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.isSelected = selected == position
    }

}