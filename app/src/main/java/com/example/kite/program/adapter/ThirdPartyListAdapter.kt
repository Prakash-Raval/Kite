package com.example.kite.program.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.databinding.ItemThirdpartyListBinding
import com.example.kite.program.lis.OnThirdPartyListing
import com.example.kite.program.model.ThirdPartyListResponse

class ThirdPartyListAdapter(
    private var thirdPartyList: ArrayList<ThirdPartyListResponse>,
    private val onThirdPartyListing: OnThirdPartyListing
) :
    RecyclerView.Adapter<ThirdPartyListAdapter.ViewHolder>(), Filterable {
    var selectedItem = 0
    var list = ArrayList<ThirdPartyListResponse>()

    init {
        list = thirdPartyList
    }

    inner class ViewHolder(val binding: ItemThirdpartyListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.listingData = list[position]
            binding.imageContainer.setOnClickListener {
                onThirdPartyListing.onClick(list[position].thirdPartyId.toString())
                val select = selectedItem
                selectedItem = position
                notifyItemChanged(select)
                notifyItemChanged(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemThirdpartyListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.isSelected = selectedItem == position
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSequence = constraint.toString()
                list = if (charSequence.isEmpty()) {
                    thirdPartyList
                } else {
                    val resultList = ArrayList<ThirdPartyListResponse>()
                    for (row in list) {
                        if (row.thirdPartyName?.lowercase()
                                ?.contains(constraint.toString().lowercase()) == true
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val results = FilterResults()
                results.values = list
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results?.values as ArrayList<ThirdPartyListResponse>
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
}