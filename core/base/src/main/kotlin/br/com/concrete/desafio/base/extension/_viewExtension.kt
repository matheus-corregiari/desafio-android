package br.com.concrete.desafio.base.extension

import android.arch.lifecycle.LifecycleOwner
import android.content.ContextWrapper
import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.StyleRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.concrete.desafio.base.R
import br.com.concrete.desafio.base.picasso.transformation.CircleTransform
import com.squareup.picasso.Picasso

fun View.addStatusBarPadding() {
    setPadding(paddingLeft,
            paddingTop + context.statusBarHeight(), paddingRight,
            paddingBottom)
}

fun View.addStatusBarMargin() {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.topMargin += context.statusBarHeight()
}

fun ActionBar?.enableBack() {
    if (this == null) return
    setDisplayHomeAsUpEnabled(true)
    setDisplayShowHomeEnabled(true)
}

fun ImageView.loadUrl(url: String) {
    if (url.isEmpty()) setImageResource(R.drawable.ic_avatar)
    else Picasso.with(context).load(url).placeholder(R.drawable.ic_avatar).transform(CircleTransform()).into(this)
}

val View.owner: LifecycleOwner?
    get() = when (context) {
        is LifecycleOwner -> context as LifecycleOwner
        is ContextWrapper -> (context as ContextWrapper).baseContext as? LifecycleOwner
        else -> null
    }

val View.activity: AppCompatActivity?
    get() = when (context) {
        is AppCompatActivity -> context as AppCompatActivity
        is ContextWrapper -> (context as ContextWrapper).baseContext as? AppCompatActivity
        else -> null
    }

@Suppress("DEPRECATION")
fun TextView.setCustomTextAppearance(@StyleRes styleRes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) setTextAppearance(styleRes)
    else setTextAppearance(context, styleRes)
}

inline fun TypedArray.obtain(func: TypedArray.() -> Unit) {
    func()
    recycle()
}