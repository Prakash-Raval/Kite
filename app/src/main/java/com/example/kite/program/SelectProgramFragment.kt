package com.example.kite.program

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.*
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentSelectProgramBinding
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.program.adapter.ThirdPartyListAdapter
import com.example.kite.program.model.ThirdPartyListResponse
import com.example.kite.program.repository.ThirdPartyListRepository
import com.example.kite.program.viewmodel.TPLViewModelFactory
import com.example.kite.program.viewmodel.ThirdPartyListingViewModel
import com.example.kite.utils.onTextChanged
import com.example.kite.utils.showKeyboard
import kotlinx.coroutines.launch


class SelectProgramFragment : Fragment() {
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
        //binding.vpProgram.animation =  getItemAnimator().setSupportsChangeAnimations(false)
        //adding textWatcher for search view
       // binding.edtSearch.addTextChangedListener(txtSearch)


        list = ArrayList()
        adapter = ThirdPartyListAdapter(
            list, requireContext()
        )
        binding.vpProgram.adapter = adapter
        //adapter.notifyItemChanged(binding.vpProgram.currentItem)
        return binding.root
    }

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
            it.data?.let { it1 -> list.addAll(it1) }
            adapter.notifyDataSetChanged()
            Log.d("ListDataFromApi", list.toString())
        }

        //getting access token for listing
        val sharedPreference =
            activity?.getSharedPreferences("TOKEN_PREFERENCE", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        val token = sharedPreference?.getString("token", "")
        editor?.apply()
        lifecycleScope.launch {
            if (token != null) {
                viewModel.getToken(token)
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

    private fun setUpTextWatcher() {
        binding.edtSearch.onTextChanged {
            adapter.filter.filter(it)        }
    }

    /*//searching text for third party listing
    private val txtSearch = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.edtSearch.setCompoundDrawables(
                null,
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_search),
                null
            )
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.edtSearch.setCompoundDrawables(
                null,
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_close),
                null
            )
            adapter.filter.filter(p0)
        }

        override fun afterTextChanged(p0: Editable?) {
            binding.edtSearch.setCompoundDrawables(
                null,
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_close),
                null
            )
        }
    }*/
}