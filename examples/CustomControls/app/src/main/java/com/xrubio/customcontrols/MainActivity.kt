package com.xrubio.customcontrols

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val compoundControlView
            by lazy { findViewById<MyCompoundControlView>(R.id.my_compound_control) }
    private val customControlView1
            by lazy { findViewById<MyCustomControlView>(R.id.my_custom_control1) }
    private val customControlView2
            by lazy { findViewById<MyCustomControlView>(R.id.my_custom_control2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compoundControlView.title = "Title set by code"
        compoundControlView.subTitle = "Look, mum, a subtitle!"

        // Notice once is disabled, click will not be triggered again... 😅
        customControlView1.setOnClickListener {
            it.isEnabled = false
        }

        customControlView2.setOnClickListener {
            customControlView2.radius *= 1.1f
        }
    }
}
