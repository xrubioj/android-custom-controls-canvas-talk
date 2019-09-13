package com.xrubio.customcontrols

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class MyCompoundControlView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        View.inflate(context, R.layout.my_compount_control_layout, this)
        this.orientation = VERTICAL
    }
}
