package br.com.concrete.desafio.test

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TestActivity : AppCompatActivity() {

    private lateinit var content: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = FrameLayout(this)
        content.setBackgroundColor(Color.WHITE)
        content.id = android.R.id.content
        setContentView(content)
    }

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(error: Throwable) {
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(android.R.id.content, fragment, "TAG").commit()
    }

    fun setView(view: (Context) -> View) = runOnUiThread {
        content.addView(view.invoke(this))
    }
}
