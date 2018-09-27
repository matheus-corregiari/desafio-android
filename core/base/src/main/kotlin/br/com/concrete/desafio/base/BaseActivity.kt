package br.com.concrete.desafio.base

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.concrete.desafio.base.extension.snack
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onHomeClick()
        return super.onOptionsItemSelected(item)
    }

    protected open fun onHomeClick() = finish()

    /**
     * This method will be called whenever data module exception was detected
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onDefaultErrorReceived(error: Throwable) {
        // TODO Do something
    }

    open fun onErrorReceived(error: Throwable) {
        snack(error.message ?: "")
    }
}