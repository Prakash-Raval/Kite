package com.example.kite.notification.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.R
import com.example.kite.databinding.ItemNotificationRecyclerBinding
import com.example.kite.notification.listner.OnNotifyUpdate
import com.example.kite.notification.model.NotificationResponse

class NotificationAdapter(val context: Context, val onNotifyUpdate: OnNotifyUpdate) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var list = ArrayList<NotificationResponse.NotificationsData>()
    var selected = -1

    fun setList(list: ArrayList<NotificationResponse.NotificationsData>) {
        this.list = list
    }

    inner class ViewHolder(val binding: ItemNotificationRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            binding.dataNotification = list[position]
         //   binding.isSelected = selected == position
            //list[position].isSelected = selected == position
            if (list[position].notificationRead == 0)
                binding.constraintNotification.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )


            binding.root.setOnClickListener {
                binding.isSelected = true
                onNotifyUpdate.onClick(
                    list[position].notificationId.toString()
                )
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
        val binding: ItemNotificationRecyclerBinding =
            ItemNotificationRecyclerBinding.inflate(
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
            holder.binding.constraintNotification.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        }
    }
}