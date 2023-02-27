package com.example.kite.countrylisting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.databinding.ItemCountryListBinding

class CountryListingAdapter(
    private val onCellClickedCountry: OnCellClickedCountry,
    private val mCountry: String
) :
    RecyclerView.Adapter<CountryListingAdapter.ViewHolder>() {

    private lateinit var binding: ItemCountryListBinding
    private var list = ArrayList<CountryResponse.Country>()
    var selectedItem = 1

    inner class ViewHolder(val binding: ItemCountryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position : Int) {
            binding.rbCountry.text = list[position].country.toString()
            if (mCountry.isNotEmpty()){
                if (mCountry==list[position].country){
                    selectedItem = position
                }
            }
            binding.rbCountry.setOnClickListener {
                selectedItem = position
                notifyItemChanged(position)
                onCellClickedCountry.isClicked(binding.rbCountry.text.toString(),position)

            }

        }
    }

    fun setList(list: ArrayList<CountryResponse.Country>) {
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
        if(selectedItem == position){
            holder.binding.rbCountry.isChecked = true
        }
    }
}