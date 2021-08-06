package cn.lib.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlin.math.ceil

class RecyclerViewItemDecoration(
    private val cuttingLineWidth: Int,
    private val horizontalPadding: Int = 0,
    private val isContext: Boolean = false
) : RecyclerView.ItemDecoration() {

    private val mLeftMap = hashMapOf<Int, Int>()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //获取当前的position
        val position: Int = parent.getChildAdapterPosition(view)
        //获取LayoutManager有几列
        val spanCount: Int = getSpanCount(parent)
        //获取所有的数据size
        val childCount: Int = parent.adapter?.itemCount ?: 0
        //获取方向
        val isHorizontal: Boolean = isHorizontal(parent)
        if (spanCount == 1) {
            itemOffsets(outRect, position, childCount, isHorizontal)
        } else {
            itemOffsetsMulti(outRect, position, spanCount, childCount, isHorizontal)
        }
    }

    //获取LayoutManager有几列
    private fun getSpanCount(parent: RecyclerView): Int {
        return when (val layoutManager: LayoutManager? = parent.layoutManager) {
            is GridLayoutManager -> return layoutManager.spanCount
            is StaggeredGridLayoutManager -> return layoutManager.spanCount
            else -> 1
        }
    }

    /**
     * 判断是否是横向
     * true 为横向 false 反之
     */
    private fun isHorizontal(parent: androidx.recyclerview.widget.RecyclerView): Boolean {
        return when (val layoutManager: LayoutManager? = parent.layoutManager) {
            is LinearLayoutManager -> layoutManager.orientation == HORIZONTAL
            is GridLayoutManager -> layoutManager.orientation == HORIZONTAL
            is StaggeredGridLayoutManager -> layoutManager.orientation == HORIZONTAL
            else -> true
        }
    }

    private fun itemOffsets(outRect: Rect, position: Int, childCount: Int, isHorizontal: Boolean) {
        val left: Int = horizontalPadding
        val top: Int = if (isContext && position == 0) cuttingLineWidth else 0
        val right: Int = horizontalPadding
        val bottom: Int = when {
            isContext -> cuttingLineWidth
            position == childCount - 1 -> 0
            else -> cuttingLineWidth
        }
        if (isHorizontal) {
            outRect.set(top, right, bottom, left)
        } else {
            outRect.set(left, top, right, bottom)
        }
    }

    private fun itemOffsetsMulti(
        outRect: Rect, position: Int, spanCount: Int, childCount: Int,
        isHorizontal: Boolean
    ) {
        //总共的padding
        val padding: Int = cuttingLineWidth * (spanCount - 1) + horizontalPadding * 2
        //单个item的padding
        val itemPadding: Int = ceil(padding.toFloat() / spanCount).toInt()
        //单边的padding
        val sidePadding: Int = cuttingLineWidth
        val remainder: Int = position % spanCount
        var left: Int
        var right: Int
        if (mLeftMap.containsKey(remainder)) {
            left = mLeftMap[remainder]!!
            right = itemPadding - left
        } else {
            if (remainder == 0) {
                //最左边
                left = horizontalPadding
                right = itemPadding - left
            } else if (remainder + 1 == spanCount) {
                //最右边
                right = horizontalPadding
                left = itemPadding - right
            } else {
                if (mLeftMap.containsKey(remainder - 1)) {
                    //拿到上一个item的Padding
                    val lastLeft: Int = mLeftMap[remainder - 1]!!
                    val lastRight: Int = itemPadding - lastLeft
                    left = sidePadding - lastRight
                    right = itemPadding - left
                } else if (mLeftMap.containsKey(remainder + 1)) {
                    //拿到上一个item的Padding
                    val lastLeft: Int = mLeftMap[remainder + 1]!!
                    right = sidePadding - lastLeft
                    left = itemPadding - right
                } else {
                    left = horizontalPadding
                    right = itemPadding - left
                    repeat(position) {
                        left = sidePadding - right
                        right = itemPadding - left
                    }
                }
            }
            mLeftMap[remainder] = left
        }
        val top: Int = if (isContext && isFirstRow(position, spanCount)) cuttingLineWidth else 0
        val bottom: Int = when {
            isContext -> cuttingLineWidth
            isLastRow(position, spanCount, childCount) -> 0
            else -> cuttingLineWidth
        }
        if (isHorizontal) {
            outRect.set(top, right, bottom, left)
        } else {
            outRect.set(left, top, right, bottom)
        }
    }

    /**
     * 判断是否是第一行
     * @param position 当前position
     * @param spanCount 几列
     * @return
     */
    private fun isFirstRow(position: Int, spanCount: Int): Boolean {
        return position / spanCount + 1 == 1
    }

    /**
     * 判断是否是最后一行
     * @param position 当前position
     * @param spanCount 几列
     * @param childCount 总的数据大小
     */
    private fun isLastRow(position: Int, spanCount: Int, childCount: Int): Boolean {
        val row: Int = if (childCount % spanCount == 0) {
            childCount / spanCount
        } else {
            childCount / spanCount + 1
        }
        return row == position / spanCount + 1
    }
}