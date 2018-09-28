package br.com.concrete.desafio.test

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import br.com.concretesolutions.kappuccino.utils.doWait

open class BaseActivityRobot<T : Activity>(val rule: ActivityTestRule<T>) {

    fun launchActivity(intent: Intent = Intent()) {
        rule.launchActivity(intent)
        doWait(200L)
    }
}