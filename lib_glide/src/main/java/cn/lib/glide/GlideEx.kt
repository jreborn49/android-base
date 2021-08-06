package cn.lib.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.concurrent.thread

fun ImageView.bindUrl(
    url: Any?,
    @DrawableRes holder: Int? = null,
    radius: Int? = null,
    circle: Boolean = false,
    loadCompleted: ((Bitmap) -> Unit)? = null
) {
    holder?.let {
        setImageResource(it)
    }
    if (url == null || TextUtils.isEmpty(url.toString())) {
        holder?.let {
            setImageResource(it)
        }
        return
    }
    var requestOptions = RequestOptions()
    holder?.let {
        setImageResource(it)
    }
    radius?.let {
        requestOptions.transform(CenterCrop(), RoundedCorners(it))
    }
    if (circle) {
        requestOptions = requestOptions.transform(CircleCrop())
    }
    val request = GlideApp.with(context)
        .asBitmap()
        .transition(BitmapTransitionOptions.withCrossFade(context.resources.getInteger(android.R.integer.config_mediumAnimTime)))
        .apply(requestOptions)
    request.load(url).into(object : CustomViewTarget<ImageView, Bitmap>(this) {
        override fun onLoadFailed(errorDrawable: Drawable?) {
            setImageDrawable(errorDrawable)
        }

        override fun onResourceCleared(placeholder: Drawable?) {
            setImageDrawable(placeholder)
        }

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            setImageBitmap(resource)
            loadCompleted?.invoke(resource)
        }
    })
}

fun ImageView.bindFrame(
    url: String,
    @DrawableRes holder: Int? = null,
    radius: Int? = null
) {
    var requestOptions = RequestOptions()
    holder?.let {
        setImageResource(it)
    }
    requestOptions = requestOptions.skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .frame(500000)
    radius?.let {
        requestOptions.transform(CenterCrop(), RoundedCorners(it))
    }

    GlideApp.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .into(this)
}

@SuppressLint("CheckResult")
fun Context.clearGlideCache() {
    Glide.get(this).clearMemory()
    thread {
        Glide.get(this).clearDiskCache()
    }
}