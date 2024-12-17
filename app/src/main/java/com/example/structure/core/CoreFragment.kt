package com.example.structure.core

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.Fragment
import java.util.Calendar

abstract class CoreFragment : Fragment(), View.OnClickListener {

    var coreActivity: CoreActivity? = null

    val cal = Calendar.getInstance()
    val tz = cal.timeZone

    override fun onAttach(context: Context) {
        super.onAttach(context)
        coreActivity = activity as CoreActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun msgDialog(msg: String) {
        coreActivity!!.msgDialog(msg)
    }

    fun setFullScreenMode(window: Window) {
        coreActivity!!.setFullScreenMode(window)
    }

    fun setFullLightScreenMode(window: Window) {
        coreActivity!!.setFullLightScreenMode(window)
    }
}