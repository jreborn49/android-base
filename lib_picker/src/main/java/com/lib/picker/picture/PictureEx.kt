package com.lib.picker.picture

import android.app.Activity
import android.content.pm.ActivityInfo
import com.luck.picture.lib.PictureSelectionModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia

fun takeVideo(activity: Activity, onResult: (LocalMedia) -> Unit) {
    PictureSelector.create(activity)
        .openCamera(PictureMimeType.ofVideo())
        .recordVideoSecond(60)
        .forResult(object : SimpleOnResultListener() {
            override fun onResult(result: MutableList<LocalMedia>?) {
                result?.let { onResult.invoke(it[0]) }
            }
        })
}

fun selectVideo(activity: Activity, maxNum: Int = 1, onResult: (List<LocalMedia>) -> Unit) {
    val model = PictureSelector.create(activity)
        .openGallery(PictureMimeType.ofVideo())
        .imageEngine(GlideEngine.createGlideEngine())
        .minVideoSelectNum(1)
        .imageSpanCount(4)
        .videoMinSecond(1)
        .videoMaxSecond(60)
        .recordVideoSecond(60)
    if (maxNum > 1) {
        model.maxSelectNum(maxNum)
            .selectionMode(PictureConfig.MULTIPLE)
    } else {
        model.selectionMode(PictureConfig.SINGLE)
    }
    model.forResult(object : SimpleOnResultListener() {
        override fun onResult(result: MutableList<LocalMedia>?) {
            result?.let { onResult.invoke(it) }
        }
    })
}

fun takePhoto(activity: Activity, crop: Boolean = false, onResult: (LocalMedia) -> Unit) {
    PictureSelector.create(activity)
        .openCamera(PictureMimeType.ofImage())
        .default(crop)
        .forResult(object : SimpleOnResultListener() {
            override fun onResult(result: MutableList<LocalMedia>?) {
                result?.let { onResult.invoke(it[0]) }
            }
        })
}

fun selectPicture(
    activity: Activity,
    crop: Boolean = true,
    maxNum: Int = 9,
    onResult: (List<LocalMedia>) -> Unit
) {
    val model = PictureSelector.create(activity)
        .openGallery(PictureMimeType.ofImage())
        .default(crop)
        .minSelectNum(1)
    if (maxNum > 1) {
        model.maxSelectNum(maxNum)
            .selectionMode(PictureConfig.MULTIPLE)
    } else {
        model.selectionMode(PictureConfig.SINGLE)
    }
    model.forResult(object : SimpleOnResultListener() {
        override fun onResult(result: MutableList<LocalMedia>?) {
            result?.let { onResult.invoke(it) }
        }
    })
}

fun PictureSelectionModel.default(crop: Boolean = true): PictureSelectionModel {
    imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
    isMaxSelectEnabledMask(true)//选择数到了最大阀值列表是否启用蒙层效果
    imageSpanCount(4)//每行显示个数
    isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//设置相册Activity方向
    isPreviewImage(true)//是否可预览图片
    isCamera(true)//是否显示拍照按钮
    isEnableCrop(crop)//是否裁剪
    cutOutQuality(80)//裁剪输出质量 默认100
    isCompress(true)//是否压缩
    minimumCompressSize(100)//小于多少kb的图片不压缩
    synOrAsy(false)//同步true或异步false 压缩 默认同步
    isGif(false)//是否显示gif图片
    isReturnEmpty(false)//未选择数据时点击按钮是否可以返回
    if (crop) {
        freeStyleCropEnabled(true)
        showCropFrame(true)
    }
    return this
}

