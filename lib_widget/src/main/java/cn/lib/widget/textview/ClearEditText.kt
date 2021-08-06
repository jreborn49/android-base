package cn.lib.widget.textview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.R.attr.editTextStyle
import androidx.core.content.ContextCompat
import cn.lib.widget.R

/**
 * @Create_time: 2020/7/22 17:42
 * @Author: wr
 * @Description: ()
 */
@SuppressLint("AppCompatCustomView")
class ClearEditText : EditText, TextWatcher, View.OnFocusChangeListener {

    private lateinit var mClearDrawable: Drawable

    //是否要有焦点才显示删除按钮
    private var mIsFocusVisibility: Boolean = false
    private var mIsFocus: Boolean = false

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = editTextStyle) :
            super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val arr: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.ClearEditText,
            defStyleAttr, defStyleRes
        )
        try {
            val clearIcon: Drawable? = arr.getDrawable(R.styleable.ClearEditText_clearIcon)
            if (clearIcon != null) {
                setClearDrawable(clearIcon)
            } else {
                setClearDrawable(androidx.appcompat.R.drawable.abc_ic_clear_material)
            }
            val visibility: Boolean = arr.getBoolean(
                R.styleable.ClearEditText_focusVisibility,
                mIsFocusVisibility
            )
            setFocusVisibility(visibility)
        } finally {
            arr.recycle()
        }
        //监听文本内容变化
        addTextChangedListener(this)
        onFocusChangeListener = this
        setSingleLine()
        inputType = inputType
    }

    fun setClearDrawable(drawableId: Int) {
        setClearDrawable(ContextCompat.getDrawable(context, drawableId)!!)
    }

    fun setClearDrawable(drawable: Drawable) {
        mClearDrawable = drawable
        //设置删除按钮的边界
        val width: Int = mClearDrawable.intrinsicWidth
        val height: Int = mClearDrawable.intrinsicHeight
        mClearDrawable.setBounds(0, 0, width, height)
        if (mIsFocusVisibility) {
            setClearIconVisibility(text.toString().isNotEmpty() && mIsFocus)
        } else {
            setClearIconVisibility(text.toString().isNotEmpty())
        }
    }

    fun setFocusVisibility(visibility: Boolean) {
        if (mIsFocusVisibility != visibility) {
            mIsFocusVisibility = visibility
            if (mIsFocusVisibility) {
                setClearIconVisibility(text.toString().isNotEmpty() && mIsFocus)
            } else {
                setClearIconVisibility(text.toString().isNotEmpty())
            }
        }
    }

    /**
     * 控制EditText右边制删除按钮的显示、隐藏
     */
    private fun setClearIconVisibility(isShow: Boolean) {
        val clearDrawable: Drawable? = if (isShow) mClearDrawable else null
        setCompoundDrawables(
            compoundDrawables[0], compoundDrawables[1],
            clearDrawable, compoundDrawables[3]
        )
    }

    override fun afterTextChanged(s: Editable) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(
        text: CharSequence?, start: Int, lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (mIsFocusVisibility) {
            setClearIconVisibility(text.toString().isNotEmpty() && mIsFocus)
        } else {
            setClearIconVisibility(text.toString().isNotEmpty())
        }
    }

    /**
     * 通过手指的触摸位置模式删除按钮的点击事件
     *
     * @param event
     * @return
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableWith: Int = mClearDrawable.intrinsicWidth
                val areaStartX: Int = width - paddingRight - drawableWith
                val areaEndX: Int = width - paddingRight
                if (event.x >= areaStartX && event.x <= areaEndX) {
                    if (event.y >= paddingTop && event.y <= height - paddingBottom) {
                        setText("")
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        mIsFocus = hasFocus
        if (hasFocus && text.toString().isNotEmpty()) {
            setClearIconVisibility(true)
        } else {
            if (mIsFocusVisibility) {
                setClearIconVisibility(false)
            } else {
                setClearIconVisibility(text.toString().isNotEmpty())
            }
        }
    }

}