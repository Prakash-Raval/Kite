package com.example.kite.statelisting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.countrylisting.CountryResponse
import com.example.kite.databinding.ItemCountryListBinding

class StateListingAdapter :
    RecyclerView.Adapter<StateListingAdapter.ViewHolder>() {

    private lateinit var binding: ItemCountryListBinding
    private var list = ArrayList<StateResponse.State>()
    var selectedItem = 0

    inner class ViewHolder(val binding: ItemCountryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            binding.rbCountry.text = list[position].country.toString()
            binding.rbCountry.setOnClickListener {
                val select = selectedItem
                selectedItem = position
                notifyItemChanged(select)
                notifyItemChanged(position)
            }
        }
    }

    fun setList(list: ArrayList<StateResponse.State>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemCountryListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.rbCountry.isChecked = selectedItem == position
    }
}