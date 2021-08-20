package com.jreborn.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.lib.base.ex.click
import com.lib.picker.createPickerBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.tv_datepicker_dialog).click {
            val builder = createPickerBuilder(this) {date, view ->

            }
            builder.isDialog(true)
            builder.build().show()
        }
    }
}