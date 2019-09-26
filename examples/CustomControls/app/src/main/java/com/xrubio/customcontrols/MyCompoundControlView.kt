package com.xrubio.customcontrols

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class MyCompoundControlView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val titleTextView by lazy { findViewById<TextView>(R.id.title) }
    private val subTitleTextView by lazy { findViewById<TextView>(R.id.subtitle) }

    var title: String
        get() = titleTextView.text.toString()
        set(value) { titleTextView.text = value }

    var subTitle: String
        get() = subTitleTextView.text.toString()
        set(value) { subTitleTextView.text = value }

    init {
        View.inflate(context, R.layout.my_compount_control_layout, this)
        this.orientation = VERTICAL
    }

}
