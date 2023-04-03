package com.example.kite.countrylisting.statelisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.countrylisting.OnCellClickedRegion
import com.example.kite.databinding.ItemCountryListBinding

class StateListingAdapter(
    private val mState: String,
    private val onCellClickedState: OnCellClickedRegion
) :
    RecyclerView.Adapter<StateListingAdapter.ViewHolder>() {

    private lateinit var binding: ItemCountryListBinding
    private var list = ArrayList<String?>()
    var selectedItem = -1

    inner class ViewHolder(val binding: ItemCountryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.rbCountry.text = list[position]
            if (mState.isNotEmpty()) {
                if (mState == list[position]) {
                    selectedItem = position
                }
            }
            binding.rbCountry.setOnClickListener {
                selectedItem = position
                notifyItemChanged(position)
                onCellClickedState.isClickedState(binding.rbCountry.text.toString(), position)
            }
        }
    }

    fun setList(list: ArrayList<String?>) {
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