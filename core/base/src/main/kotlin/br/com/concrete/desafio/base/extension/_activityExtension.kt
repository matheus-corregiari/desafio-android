package br.com.concrete.desafio.base.extension

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

fun AppCompatActivity.snack(@StringRes msgRes: Int) {
    Snackbar.make(findViewById<View>(android.R.id.content), msgRes, Snackbar.LENGTH_SHORT).show()
}

fun AppCompatActivity.snack(msg: String) {
    Snackbar.make(findViewById<View>(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
}