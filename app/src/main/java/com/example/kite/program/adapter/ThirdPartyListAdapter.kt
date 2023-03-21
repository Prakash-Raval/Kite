package com.example.kite.program.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions
import com.example.kite.R
import com.example.kite.databinding.ItemThirdpartyListBinding
import com.example.kite.program.lis.OnThirdPartyListing
import com.example.kite.program.model.ThirdPartyListResponse

class ThirdPartyListAdapter(
    private var thirdPartyList: ArrayList<ThirdPartyListResponse.Data?>,
    private val context: Context,
    private val onThirdPartyListing: OnThirdPartyListing
) :
    RecyclerView.Adapter<ThirdPartyListAdapter.ViewHolder>(), Filterable {
    private lateinit var binding: ItemThirdpartyListBinding
    var selectedItem = 0
    var list = ArrayList<ThirdPartyListResponse.Data?>()

    init {
        list = thirdPartyList
    }

    inner class ViewHolder(val binding: ItemThirdpartyListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.listingData = list[position]

            binding.imageContainer.setOnClickListener {
                val select = selectedItem
                selectedItem = position
                notifyItemChanged(select)
                notifyItemChanged(position)
            }

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(FitCenter())
            val url = list[position]?.thirdPartyImage

            Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(binding.imgList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemThirdpartyListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)

        //setting custom style for card view
        if (selectedItem == position) {
            onThirdPartyListing.onClick(list[position]?.thirdPartyId.toString())
            holder.binding.imageContainer.apply {
                radius = 30f
                elevation = 10f
                strokeWidth = 5
                strokeColor = ContextCompat.getColor(context, R.color.bg_main)

            }
        } else {
            holder.binding.imageContainer.apply {
                radius = 30f
                elevation = 10f
                strokeColor = ContextCompat.getColor(context, R.color.white)
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSequence = constraint.toString()
                list = if (charSequence.isEmpty()) {
                    thirdPartyList
                } else {
                    val resultList = ArrayList<ThirdPartyListResponse.Data?>()
                    for (row in list) {
                        if (row?.thirdPartyName?.lowercase()
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
                list = results?.values as ArrayList<ThirdPartyListResponse.Data?>
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