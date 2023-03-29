package com.example.kite.selectpayment.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.databinding.ItemCardDesignBinding
import com.example.kite.selectpayment.model.GetCardResponse

class GetCardAdapter : RecyclerView.Adapter<GetCardAdapter.ViewHolder>() {
    private var list = ArrayList<GetCardResponse.Detail>()
    fun setList(list: ArrayList<GetCardResponse.Detail>) {
        this.list = list
    }

    inner class ViewHolder(val binding: ItemCardDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            binding.getCard = list[position]
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
            ItemCardDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}