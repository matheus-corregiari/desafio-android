package br.com.concrete.desafio.base.extension

import android.support.v7.app.ActionBar
import android.view.View
import android.widget.ImageView
import br.com.concrete.desafio.base.R
import br.com.concrete.desafio.base.picasso.transformation.CircleTransform
import com.squareup.picasso.Picasso

fun View.addStatusBarPadding() {
    setPadding(paddingLeft,
            paddingTop + context.statusBarHeight(), paddingRight,
            paddingBottom)
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