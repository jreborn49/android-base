package cn.lib.widget.textview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.IntDef
import cn.lib.widget.R
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@SuppressLint("AppCompatCustomView")
open class SupportTextView: TextView {

    private var mFontStyle: Int = NORMAL
    private var mPrefix: String? = null
    private var mPrefixColor: Int = 0
    private var mSuffix: String? = null
    private var mSuffixColor: Int = 0

    companion object{
        const val NORMAL = 0
        const val MEDIUM = 1
        const val BOLD = 2
    }

    @IntDef(value = [NORMAL, MEDIUM, BOLD])
    @Retention(RetentionPolicy.SOURCE)
    annotation class FontStyle{}

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
            super(context, attrs, defStyleAttr){
        init(context, attrs, defStyleAttr, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):
            super(context, attrs, defStyleAttr, defStyleRes){
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int){
        val arr: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SupportTextView,
                defStyleAttr, defStyleRes)
        mFontStyle = try {
            mPrefixColor = arr.getColor(R.styleable.SupportTextView_prefixTextColor, 0)
            mPrefix = arr.getString(R.styleable.SupportTextView_prefixText)
            mSuffixColor = arr.getColor(R.styleable.SupportTextView_suffixTextColor, 0)
            mSuffix = arr.getString(R.styleable.SupportTextView_suffixText)
            arr.getInt(R.styleable.SupportTextView_fontStroke, NORMAL)
        } catch (e: Exception){
            NORMAL
        }
        //设置画笔的描边宽度值
        paint.strokeWidth = mFontStyle.toFloat()
        paint.style = Paint.Style.FILL_AND_STROKE
        text = text
    }

    fun setFontStroke(@FontStyle font: Int) {
        if(mFontStyle != font) {
            mFontStyle = font
            invalidate()
        }
    }

    override fun setTypeface(tf: Typeface?) {
        super.setTypeface(tf)
    }

    override fun setTypeface(tf: Typeface?, style: Int) {
        super.setTypeface(tf, style)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        val builder = SpannableStringBuilder()
        if(!mPrefix.isNullOrEmpty()) {
            if(mPrefixColor != 0) {
                val spannable = SpannableString(mPrefix)
                //设置前缀文字颜色
                val colorSpan = ForegroundColorSpan(mPrefixColor)
                spannable.setSpan(colorSpan, 0, mPrefix!!.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.append(spannable)
            } else {
                builder.append(mPrefix)
            }
        }
        if(!text.isNullOrEmpty()) {
            builder.append(text)
        }
        if(!mSuffix.isNullOrEmpty()) {
            if(mSuffixColor != 0) {
                val spannable = SpannableString(mSuffix)
                //设置前缀文字颜色
                val colorSpan = ForegroundColorSpan(mSuffixColor)
                spannable.setSpan(colorSpan, 0, mSuffix!!.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.append(spannable)
            } else {
                builder.append(mSuffix)
            }
        }
        super.setText(builder, type)
    }
}