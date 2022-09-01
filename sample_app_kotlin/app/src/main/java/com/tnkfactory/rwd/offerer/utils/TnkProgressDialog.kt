package com.tnkfactory.rwd.offerer.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.tnkfactory.ad.TnkStyle

/**
 * @author hanago
 * @email hans@tnkfactory.com
 * @since 2022/08/31
 **/
class TnkProgressDialog(context: Context) {

    private val progressDialog: Dialog by lazy { Dialog(context) }

    init {
        val dpSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, context.resources.displayMetrics).toInt()
        val layoutParam = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val dialogLayout = RelativeLayout(context)
        dialogLayout.layoutParams = layoutParam
        val pbLayoutParam = RelativeLayout.LayoutParams(dpSize, dpSize)
        pbLayoutParam.addRule(RelativeLayout.CENTER_IN_PARENT)
        val pb = ProgressBar(context)
        pb.isIndeterminate = true
        pb.layoutParams = pbLayoutParam
        dialogLayout.addView(pb)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setContentView(dialogLayout)
    }

    fun dismiss() {
        if (progressDialog.isShowing) {
            try {
                progressDialog.dismiss()
            } catch (ignored: Exception) {
            }
        }
    }

    fun show() {
        try {
            if (!TnkStyle.showProgressDim) {
                progressDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            progressDialog.show()
        } catch (e: Exception) {
        }
    }

}
