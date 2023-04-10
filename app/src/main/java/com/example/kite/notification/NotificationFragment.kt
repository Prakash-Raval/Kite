package com.example.kite.notification

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentNotificationBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.notification.adapter.NotificationAdapter
import com.example.kite.notification.listner.OnNotifyUpdate
import com.example.kite.notification.model.NotificationRequest
import com.example.kite.notification.model.NotificationResponse
import com.example.kite.notification.model.UpdateNotificationRequest
import com.example.kite.notification.model.UpdateNotificationResponse
import com.example.kite.notification.viewmodel.NotificationViewModel
import com.example.kite.utils.PrefManager

class NotificationFragment : BaseFragment(), OnNotifyUpdate {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var viewModel: NotificationViewModel
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_notification,
            container,
            false
        )
        setUpToolBar()
        setAdapter()
        getNotificationList()
        return binding.root
    }

    private fun setAdapter() {
        adapter = NotificationAdapter(requireContext(), this)
        binding.rvNotification.adapter = adapter

    }

    private fun setUpToolBar() {
        binding.inNotificationBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inNotificationBar.txtToolbarHeader.setText(R.string.notification)
    }


    private fun getViewModel(): NotificationViewModel {
        viewModel = ViewModelProvider(this)[NotificationViewModel::class.java]
        return viewModel
    }

    private fun getNotificationList() {
        viewModel = getViewModel()
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken

        viewModel.getNotificationListing(
            NotificationRequest(
                access_token = token, lang = 0
            )
        )
        setNotificationObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setNotificationObserver() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ViewTripFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<NotificationResponse>?> -> {
                    hideProgressBar()

                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        adapter.setList(
                            state.response.data?.notificationsData
                                    as ArrayList<NotificationResponse.NotificationsData>
                        )

                        binding.isSelected = state.response.data?.notificationsCount != 0
                    }
                }
            }
        })
    }

    private fun setNotificationObserverUpdate() {
        viewModel.liveDataUpdate.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ViewTripFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<UpdateNotificationResponse>?> -> {
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    hideProgressBar()
                }
            }
        })
    }

    override fun onClick(notificationID: String) {
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        val isRead = "0"
        viewModel.updateNotification(
            UpdateNotificationRequest(
                access_token = token,
                notification_id = notificationID,
                is_read = isRead
            )
        )
        setNotificationObserverUpdate()
    }
}