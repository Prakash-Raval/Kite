package com.example.kite.program

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentSelectProgramBinding
import com.example.kite.extensions.showKeyboard
import com.example.kite.login.model.LoginResponse
import com.example.kite.program.adapter.ThirdPartyListAdapter
import com.example.kite.program.lis.OnThirdPartyListing
import com.example.kite.program.model.ThirdPartyListRequest
import com.example.kite.program.model.ThirdPartyListResponse
import com.example.kite.program.viewmodel.ThirdPartyViewModel
import com.example.kite.utils.PrefManager
import com.example.kite.utils.onTextChanged


class SelectProgramFragment : BaseFragment(), OnThirdPartyListing {
    /*
    * variables
    * */
    private lateinit var binding: FragmentSelectProgramBinding
    private lateinit var viewModel: ThirdPartyViewModel
    private lateinit var adapter: ThirdPartyListAdapter


    /*
    * list
    * */
    private lateinit var list: ArrayList<ThirdPartyListResponse>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_select_program,
            container,
            false
        )
        /*
        *
        * method calls
        * */
        getData()
        viewPagerStyle()
        spannableText()
        setUpTextWatcher()
        navigate()
        setUPToolbar()
        //setAdapterThirdPartyList()
        list = ArrayList()
        adapter = ThirdPartyListAdapter(
            list,
            this
        )
        binding.vpProgram.adapter = adapter
        return binding.root
    }

    //navigation
    private fun navigate() {
        binding.btnConfirm.setOnClickListener {
            findNavController().navigate(SelectProgramFragmentDirections.actionSelectProgramFragmentToHomeFragment())
        }
    }

    /*
    * setting up the toolbar
    * */
    fun setUPToolbar() {
        binding.inProgramBar.imgBack.visibility = View.GONE
        binding.inProgramBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.inProgramBar.txtToolbarHeader.setText(R.string.select_your_program)
    }

    private fun getViewModel(): ThirdPartyViewModel {
        viewModel = ViewModelProvider(this)[ThirdPartyViewModel::class.java]
        return viewModel
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        viewModel = getViewModel()
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        viewModel.getThirdPartyList(
            ThirdPartyListRequest(
                access_token = token,
                ThirdPartyListRequest.UserLocation()
            )
        )
        setObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver() {
        //handling error event in snack bar
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
                is ResponseHandler.OnSuccessResponse<ResponseListData<ThirdPartyListResponse>?> -> {
                    hideProgressBar()
                    state.response?.data?.let { it1 -> it1.let { it2 -> list.addAll(it2) } }
                    adapter.notifyDataSetChanged()
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                }
            }
        })
    }

    //style  for viewpager 2
    //setting multiple pages on single tab
    private fun viewPagerStyle() {

        with(binding.vpProgram) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
        binding.vpProgram.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
    }

    // span text for search view click
    private fun spannableText() {
        binding.txtSearch.movementMethod = LinkMovementMethod.getInstance()
        val search: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

            override fun onClick(p0: View) {
                binding.edtSearch.showKeyboard()
            }
        }
        val spannable =
            SpannableString(resources.getString(R.string.don_t_see_your_property_search_now))
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)),
            25,
            35,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            boldSpan,
            25,
            35,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            search,
            25,
            35,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        binding.txtSearch.text = spannable
    }

    //filtering adapter list
    private fun setUpTextWatcher() {
        binding.edtSearch.onTextChanged {
            adapter.filter.filter(it)
        }
    }


    override fun onClick(thirdPartyID: String) {
        val sharedPreferences =
            activity?.getSharedPreferences("THIRD_PARTY_ID", MODE_PRIVATE)?.edit()
        sharedPreferences?.putString("ThirdPartyID", thirdPartyID)?.apply()
    }

    /*
    * calling out generic recycler view adapter
    * */
    /*private fun setAdapterThirdPartyList() {
        var selected = 0
        val myAdapter = object :
            GenericAdapter<ThirdPartyListResponse, ItemThirdpartyListBinding>(
                requireContext(),
                thirdPartyList
            ){
            override val layoutResId: Int
                get() = R.layout.item_thirdparty_list

            override fun onBindData(
                model: ThirdPartyListResponse,
                position: Int,
                dataBinding: ItemThirdpartyListBinding
            ) {
                dataBinding.listingData = model
                dataBinding.executePendingBindings()
                dataBinding.isSelected = selected == position

            }

            override fun onItemClick(model: ThirdPartyListResponse, position: Int) {
                selected = position
                val select = selected
                notifyItemChanged(select)
                notifyItemChanged(position)
                val sharedPreferences =
                    activity?.getSharedPreferences("THIRD_PARTY_ID", MODE_PRIVATE)?.edit()
                sharedPreferences?.putString("ThirdPartyID", model.thirdPartyId)?.apply()
            }

        }

        binding.vpProgram.adapter = myAdapter
    }*/
}