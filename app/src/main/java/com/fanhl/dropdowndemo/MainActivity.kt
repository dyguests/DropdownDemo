package com.fanhl.dropdowndemo

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.PopupWindowCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.zyyoona7.lib.EasyPopup
import com.zyyoona7.lib.HorizontalGravity
import com.zyyoona7.lib.VerticalGravity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup_vehicle_picker.*
import kotlinx.android.synthetic.main.popup_vehicle_picker.view.*

class MainActivity : AppCompatActivity() {
    private val vehiclePicker by lazy { VehiclePicker() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_1.setOnClickListener {
            vehiclePicker.popup(btn_1)
//            window.attributes.alpha
        }

        btn_2.setOnClickListener {
            VehicleDialogFragment.popup(supportFragmentManager)
        }

        btn_3.setOnClickListener {
            EasyPopup(this@MainActivity)
                    .setContentView<EasyPopup>(R.layout.popup_vehicle_picker)
//                    .setAnimationStyle<EasyPopup>(R.style.Cir)
                    //是否允许点击PopupWindow之外的地方消失
                    .setFocusAndOutsideEnable<EasyPopup>(true)
                    //允许背景变暗
                    .setBackgroundDimEnable<EasyPopup>(true)
                    //变暗的透明度(0-1)，0为完全透明
                    .setDimValue<EasyPopup>(0.4f)
                    .createPopup<EasyPopup>()
                    .apply {
                        contentView.button.setOnClickListener {
                            dismiss()
                        }
                    }
                    .showAtAnchorView(btn_3, VerticalGravity.BELOW, HorizontalGravity.CENTER, 0, 0)
        }
    }
}

class VehiclePicker {

    private var popupWindow: PopupWindow? = null
    fun popup(anchor: View) {
        if (popupWindow?.isShowing == true) {
            return
        }

        val view = LayoutInflater.from(anchor.context).inflate(R.layout.popup_vehicle_picker, null)
        popupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )

        view.button.setOnClickListener {
            if (popupWindow?.isShowing == true) {
                popupWindow?.dismiss()
            }
        }

        //水平居中
        //需要先测量，PopupWindow还未弹出时，宽高为0
        popupWindow!!.contentView.measure(makeDropDownMeasureSpec(popupWindow!!.width), makeDropDownMeasureSpec(popupWindow!!.height))
        val offsetX = (anchor.width - popupWindow!!.contentView.measuredWidth) / 2
        val offsetY = 0

        popupWindow!!.isOutsideTouchable = false


        PopupWindowCompat.showAsDropDown(popupWindow!!, anchor, offsetX, offsetY, Gravity.START)
    }

    private fun makeDropDownMeasureSpec(measureSpec: Int): Int {
        val mode: Int
        if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mode = View.MeasureSpec.UNSPECIFIED
        } else {
            mode = View.MeasureSpec.EXACTLY
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode)
    }
}

class VehicleDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_vehicle_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        /** TAG */
        private val TAG = VehicleDialogFragment::class.java.simpleName!!

        fun popup(fragmentManager: FragmentManager) {
            VehicleDialogFragment().show(fragmentManager, TAG)
        }
    }
}
