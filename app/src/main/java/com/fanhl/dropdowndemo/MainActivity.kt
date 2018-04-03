package com.fanhl.dropdowndemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup_vehicle_picker.view.*

class MainActivity : AppCompatActivity() {
    private val vehiclePicker by lazy { VehiclePicker() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_1.setOnClickListener {
            vehiclePicker.popup(btn_1)
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

        popupWindow?.showAsDropDown(anchor)
    }

}
