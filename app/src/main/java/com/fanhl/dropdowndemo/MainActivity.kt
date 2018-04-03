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
        }

        btn_2.setOnClickListener {
            VehicleDialogFragment.popup(supportFragmentManager)
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
