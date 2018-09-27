package br.com.concrete.desafio.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import android.view.View
import br.com.concretesolutions.kappuccino.utils.doWait

open class BaseActivityRobot<T : Activity>(val rule: ActivityTestRule<T>) {

    fun launchActivity(intent: Intent = Intent()) {
        rule.launchActivity(intent)
        doWait(200L)
    }
}

open class BaseFragmentRobot(rule: ActivityTestRule<TestActivity>) : BaseActivityRobot<TestActivity>(rule) {

    fun launchFragment(fragment: Fragment) {
        rule.activity?.setFragment(fragment)
        doWait(200L)
    }
}

open class BaseViewRobot(rule: ActivityTestRule<TestActivity>) : BaseActivityRobot<TestActivity>(rule) {

    fun launchView(view: (Context) -> View) {
        rule.activity?.setView(view)
//        doWait(200L)
    }
}