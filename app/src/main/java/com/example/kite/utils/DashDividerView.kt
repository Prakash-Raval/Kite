package com.example.kite.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.kite.R.style
import com.example.kite.R.styleable.*

class DashDividerView : View {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)

    /*
    * for horizontal view and vertical view
    *
    * */
    companion object {
        const val DIRECTION_VERTICAL = 0
        const val DIRECTION_HORIZONTAL = 1
    }

    private var dGap = 5.25f
    private var dWidth = 5.25f
    private var dColor = Color.parseColor("#EE0606")
    private var direction = DIRECTION_HORIZONTAL
    private val paint = Paint()
    private val path = Path()

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            DashedDividerView,
            defStyleAttr,
            style.DashedDividerDefault
        )

        dGap = typedArray.getDimension(DashedDividerView_dividerDashGap, dGap)
        dWidth = typedArray.getDimension(DashedDividerView_dividerDashWidth, dWidth)
        dColor = typedArray.getColor(DashedDividerView_dividerDashColor, dColor)
        direction =
            typedArray.getInt(DashedDividerView_dividerDirection, DIRECTION_HORIZONTAL)

        paint.color = dColor
        paint.style = Paint.Style.STROKE
        paint.pathEffect = DashPathEffect(floatArrayOf(dWidth, dGap), 0f)
        paint.strokeWidth = dWidth

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path.moveTo(0f, 0f)

        if (direction == DIRECTION_HORIZONTAL) {
            path.lineTo(measuredWidth.toFloat(), 0f)
        } else {
            path.lineTo(0f, measuredHeight.toFloat())
        }
        canvas.drawPath(path, paint)
    }

}