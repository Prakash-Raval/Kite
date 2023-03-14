package com.example.kite.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

/*
class RectOverlay constructor(context: Context?, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Pass it a list of RectF (rectBounds)
        val rectF = RectF(200F, 400F, 200F, 400F)
        rectF.forEach { canvas.drawRect(it, paint) }
    }
}*/
class RectOverlay constructor(context: Context?, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    private val faceBounds: MutableList<RectF> = mutableListOf()

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context!!, android.R.color.black)
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        faceBounds.add(0, RectF(200f, 200f, 200f, 200f))
        faceBounds.forEach { canvas.drawRect(it, paint) }
    }

    fun drawFaceBounds(faceBounds: List<RectF>) {
        this.faceBounds.clear()
        this.faceBounds.addAll(faceBounds)
        invalidate()
    }

}