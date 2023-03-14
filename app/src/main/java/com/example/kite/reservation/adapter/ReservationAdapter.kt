package com.example.kite.reservation.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.databinding.ItemReservationRecycylerBinding
import com.example.kite.databinding.ItemVehicleListingBinding
import com.example.kite.reservation.listner.OnReservationViewClick
import com.example.kite.reservation.model.ListReservationResponse

class ReservationAdapter(val onReservationViewClick: OnReservationViewClick) :
    RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {
    private var list = ArrayList<ListReservationResponse.ReservationData>()

    fun setList(list: ArrayList<ListReservationResponse.ReservationData>) {
        this.list = list
    }

    inner class ViewHolder(val binding: ItemReservationRecycylerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            binding.reservationTrip = list[position]
            binding.btnHView.setOnClickListener {
                onReservationViewClick.onclick(list[position].reservationId.toString())
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
        val binding: ItemReservationRecycylerBinding =
            ItemReservationRecycylerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}