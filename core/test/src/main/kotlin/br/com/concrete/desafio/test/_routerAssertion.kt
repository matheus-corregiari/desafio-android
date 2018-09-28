package br.com.concrete.desafio.test

import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.stubIntent

fun BaseActivityRobot<*>.enqueuePullRequestList() {
    stubIntent {
        action(rule.activity.packageName + ".PULL_REQUEST_LIST")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertPullRequestList() {
    sentIntent {
        action(rule.activity.packageName + ".PULL_REQUEST_LIST")
    }
}