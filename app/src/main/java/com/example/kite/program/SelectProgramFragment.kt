package com.example.kite.program

import android.annotation.SuppressLint
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.example.kite.R
import com.example.kite.basefragment.BaseFragment
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentSelectProgramBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.program.adapter.ThirdPartyListAdapter
import com.example.kite.program.model.ThirdPartyListResponse
import com.example.kite.program.repository.ThirdPartyListRepository
import com.example.kite.program.viewmodel.TPLViewModelFactory
import com.example.kite.program.viewmodel.ThirdPartyListingViewModel
import com.example.kite.utils.BaseResponse
import com.example.kite.utils.PrefManager
import com.example.kite.utils.onTextChanged
import com.example.kite.utils.showKeyboard
import kotlinx.coroutines.launch


class SelectProgramFragment : BaseFragment() {
    private lateinit var binding: FragmentSelectProgramBinding
    private lateinit var viewModel: ThirdPartyListingViewModel
    private lateinit var adapter: ThirdPartyListAdapter
    private lateinit var list: ArrayList<ThirdPartyListResponse.Data?>

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
        getData()
        viewPagerStyle()
        spannableText()
        setUpTextWatcher()
        navigate()

        list = ArrayList()
        adapter = ThirdPartyListAdapter(
            list, requireContext()
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

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        val thirdPartyService =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = ThirdPartyListRepository(thirdPartyService)
        viewModel =
            ViewModelProvider(
                this,
                TPLViewModelFactory(repository)
            )[ThirdPartyListingViewModel::class.java]

        // adding response data to list for viewpager
        viewModel.listLiveData.observe(viewLifecycleOwner) {

            when (it) {
                is BaseResponse.Loading -> {
                    showProgressDialog()
                }
                is BaseResponse.Error -> {
                    hideProgressBar()
                }
                is BaseResponse.Success -> {
                    hideProgressBar()
                    it.data?.let { it1 -> it1.data?.let { it2 -> list.addAll(it2) } }
                    adapter.notifyDataSetChanged()
                    Log.d("ListDataFromApi", list.toString())
                }
            }
        }

        //getting access token for listing
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")
        lifecycleScope.launch {
            if (token != null) {
                token.data?.accessToken?.let { viewModel.getToken(it) }
            }
        }

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

}