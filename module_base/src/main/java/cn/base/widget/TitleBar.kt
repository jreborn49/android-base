package cn.base.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.base.R
import cn.base.adapter.BaseClicksQuickAdapter
import cn.lib.base.ex.click
import cn.lib.base.ex.gone
import cn.lib.base.ex.visible
import com.blankj.utilcode.util.BarUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlin.math.max
import kotlin.math.min

class TitleBar : FrameLayout {

    private lateinit var root: View
    private lateinit var ivBack: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var rvMenu: RecyclerView
    private lateinit var vStatus: View
    private lateinit var tvTextRight: TextView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(context: Context, attrs: AttributeSet, defStyle: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyle,
        defStyleRes
    )

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        root = LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this, false)
        ivBack = root.findViewById(R.id.iv_bar_back)
        tvTitle = root.findViewById(R.id.tv_bar_title)
        rvMenu = root.findViewById(R.id.rv_bar_menu)
        vStatus = root.findViewById(R.id.v_bar_status)
        vStatus.layoutParams.height = BarUtils.getStatusBarHeight()
        tvTextRight = root.findViewById(R.id.tv_text_right)

        addView(root)
    }

    fun bindTitle(title: Title) {
        if (title.resBack == 0) {
            ivBack.gone()
        } else {
            ivBack.visible()
            ivBack.setImageResource(title.resBack)
            ivBack.click {
                title.backClick.invoke(context, it)
            }
        }
        tvTitle.text = title.title
        title.titleColor?.let {
            tvTitle.setTextColor(it)
        }
        if (title.backgroundRes != null) {
            root.setBackgroundResource(title.backgroundRes)
        } else if (title.backgroundColor != null) {
            root.setBackgroundColor(title.backgroundColor)
        }
        rvMenu.layoutManager = GridLayoutManager(context, min(3, max(1, title.menus.size)))
        val menuAdapter = object : BaseClicksQuickAdapter<Title.Menu, BaseViewHolder>(
            R.layout.item_title_bar_menu,
            title.menus
        ) {
            override fun convertEx(holder: BaseViewHolder, item: Title.Menu) {
                val ivMenu = holder.getView<ImageView>(R.id.iv_menu)
                ivMenu.setImageResource(item.resMenu)
                item.padding?.let {
                    ivMenu.setPadding(it, it, it, it)
                }
                item.url?.let {
                    item.bindUrl?.invoke(item, it)
                }
            }
        }
        menuAdapter.setOnItemClickListener { adapter, view, position ->
            val menu = title.menus[position]
            menu.menuClick?.invoke(menu, view)
        }
        rvMenu.adapter = menuAdapter
    }

    data class Title(
        val resBack: Int = R.drawable.icon_back,
        val backClick: (Context, ImageView) -> Unit = { cxt, _ ->
            if (cxt is Activity) {
                cxt.finish()
            }
        },
        val title: CharSequence,
        val titleColor: Int? = null,
        val menus: MutableList<Menu> = mutableListOf(),
        val backgroundColor: Int? = null,
        val backgroundRes: Int? = null
    ) {
        data class Menu(
            val resMenu: Int = 0,
            val menuClick: ((Menu, View) -> Unit)? = null,
            val url: String? = null,
            val bindUrl: ((Menu, String) -> Unit)? = null,
            val children: MutableList<Menu> = mutableListOf(),
            val alias: String? = null,
            val tag: String? = null,
            val text: String? = null,
            val padding: Int? = null
        )
    }

    fun statusGone() {
        vStatus.gone()
    }

    fun setTitleText(title: String) {
        tvTitle.text = title
    }

    fun addMenu(menu: Title.Menu) {
        (rvMenu.adapter as BaseClicksQuickAdapter<Title.Menu, BaseViewHolder>).addData(menu)
    }

    fun removeMenu(menu: Title.Menu) {
        (rvMenu.adapter as BaseClicksQuickAdapter<Title.Menu, BaseViewHolder>).remove(menu)
    }

    fun handleMenu(add: Boolean, menu: Title.Menu, block: (Boolean) -> Unit) {
        if(add) {
            addMenu(menu)
        } else {
            removeMenu(menu)
        }
        block.invoke(add)
    }

    open fun getRightText() = tvTextRight
}