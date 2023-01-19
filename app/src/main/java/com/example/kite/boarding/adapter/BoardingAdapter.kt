package com.example.kite.boarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.kite.R

class BoardingAdapter(context: Context) : PagerAdapter() {
    private var context: Context
    private var layoutInflater: LayoutInflater

    init {
        this.context = context
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private val slides = arrayOf("")
    private val slidingHeader = arrayOf("")
   private val slidingDesc = arrayOf("")

    override fun getCount(): Int {
        return slidingHeader.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===  `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = layoutInflater.inflate(R.layout.item_silde_layout, container, false)
       /* val imageView = itemView.findViewById<ImageView>(R.id.imgScreenSlide)
        val textTitle = itemView.findViewById<TextView>(R.id.txtText1)
        val textDesc = itemView.findViewById<TextView>(R.id.txtText2)
        container.addView(itemView)

        imageView.setImageResource(slides[position])
        textTitle.setText(slidingHeader[position])
        textDesc.setText(slidingDesc[position])*/

        return itemView
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}