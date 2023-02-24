package com.example.kite.useragreement.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.bikelisting.adapter.OnCellClicked
import com.example.kite.databinding.ItemGuestPropertyTypeBinding
import com.example.kite.useragreement.model.GuestModel

class UserAgreementAdapter(private var onCellClicked: OnCellClicked) :
    RecyclerView.Adapter<UserAgreementAdapter.ViewHolder>() {
    private var list = ArrayList<GuestModel>()
    private lateinit var binding: ItemGuestPropertyTypeBinding
    var selected = -1

    fun setList(list: ArrayList<GuestModel>) {
        this.list = list
    }

    inner class ViewHolder(val binding: ItemGuestPropertyTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            binding.imgGuestPropertyType.setImageResource(list[position].id)
            binding.txtGuestPropertyName.text = list[position].name
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
        binding =
            ItemGuestPropertyTypeBinding.inflate(
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
        }

    }

}