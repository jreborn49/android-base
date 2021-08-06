package com.lib.picker

import android.content.Context
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import java.util.*

fun <T> singleOption(
    context: Context,
    options: List<T>,
    view: View? = null,
    block: (Int, View?) -> Unit
) {
    val builder = OptionsPickerBuilder(
        context
    ) { options1, options2, options3, v -> block.invoke(options1, v) }
    builder.setContentTextSize(16)
        .setTitleSize(18)
        .setSubCalSize(16)
        .setLineSpacingMultiplier(2F)
        .isCenterLabel(true)
    val picker = builder.build<T>()
    picker.setPicker(options)
    if (view != null) {
        picker.show(view)
    } else {
        picker.show()
    }
}

fun timePicker(context: Context, view: View? = null, timeSelect: (Date, View?) -> Unit) {
    val builder = createPickerBuilder(context, timeSelect)
    builder.setType(booleanArrayOf(true, true, true, true, true))
    val picker = builder.build()
    if (view != null) {
        picker.show(view)
    } else {
        picker.show()
    }
}

fun datePicker(context: Context, view: View? = null, dateSelect: (Date, View?) -> Unit) {
    val picker = createPickerBuilder(context, dateSelect).build()
    if (view != null) {
        picker.show(view)
    } else {
        picker.show()
    }
}

fun createPickerBuilder(
    context: Context,
    block: (Date, View?) -> Unit
): TimePickerBuilder {
    val builder = TimePickerBuilder(
        context
    ) { date, v -> block.invoke(date, v) }
    builder.isAlphaGradient(true)
        .setLineSpacingMultiplier(2F)
        .setContentTextSize(16)
        .setTitleSize(18)
        .setSubCalSize(16)
    return builder
}

