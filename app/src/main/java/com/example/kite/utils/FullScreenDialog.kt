package com.example.kite.utils

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

object FullScreenDialog {
    fun setupFullHeight(bottomSheetDialog: BottomSheetDialog,activity: Activity) {
        val bottomSheet: FrameLayout =
            bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight(activity)
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
    }

    private fun getWindowHeight(activity: Activity): Int {
        val windowMetrics: WindowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(
                activity
            )
        return windowMetrics.bounds.height()
    }
}