package br.com.concrete.desafio.test

import android.content.Intent
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.stubIntent

fun enqueueLauncher() {
    stubIntent {
        action(Intent.ACTION_MAIN)
        respondWith { ok() }
    }
}

fun assertLauncher() {
    sentIntent {
        action(Intent.ACTION_MAIN)
    }
}

fun BaseActivityRobot<*>.enqueueOtp() {
    stubIntent {
        action(rule.activity.packageName + ".OTP")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertOtp() {
    sentIntent {
        action(rule.activity.packageName + ".OTP")
    }
}

fun BaseActivityRobot<*>.enqueueMain() {
    stubIntent {
        action(rule.activity.packageName + ".MAIN")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertMain() {
    sentIntent {
        action(rule.activity.packageName + ".MAIN")
    }
}

fun BaseActivityRobot<*>.enqueueTerms() {
    stubIntent {
        action(rule.activity.packageName + ".TERMS")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertTerms() {
    sentIntent {
        action(rule.activity.packageName + ".TERMS")
    }
}

fun BaseActivityRobot<*>.enqueueHeader() {
    stubIntent {
        action(rule.activity.packageName + ".HEADER")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertHeader() {
    sentIntent {
        action(rule.activity.packageName + ".HEADER")
    }
}

fun BaseActivityRobot<*>.enqueueNoContact() {
    stubIntent {
        action(rule.activity.packageName + ".NO_CONTACT")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertNoContact() {
    sentIntent {
        action(rule.activity.packageName + ".NO_CONTACT")
    }
}

fun BaseActivityRobot<*>.enqueueSummary() {
    stubIntent {
        action(rule.activity.packageName + ".SUMMARY")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertSummary() {
    sentIntent {
        action(rule.activity.packageName + ".SUMMARY")
    }
}

fun BaseActivityRobot<*>.enqueueUserBlocked() {
    stubIntent {
        action(rule.activity.packageName + ".BLOCKED")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertUserBlocked() {
    sentIntent {
        action(rule.activity.packageName + ".BLOCKED")
    }
}

fun BaseActivityRobot<*>.assertNoCardListError() {
    sentIntent {
        action(rule.activity.packageName + ".WITHOUT_CARD")
    }
}

fun BaseActivityRobot<*>.enqueueNoCardListError() {
    stubIntent {
        action(rule.activity.packageName + ".WITHOUT_CARD")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertMaintenanceError() {
    sentIntent {
        action(rule.activity.packageName + ".MAINTENANCE")
    }
}

fun BaseActivityRobot<*>.enqueueMaintenanceError() {
    stubIntent {
        action(rule.activity.packageName + ".MAINTENANCE")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.assertInactiveUserError() {
    sentIntent {
        action(rule.activity.packageName + ".INACTIVE")
    }
}

fun BaseActivityRobot<*>.enqueueInactiveUserError() {
    stubIntent {
        action(rule.activity.packageName + ".INACTIVE")
        respondWith { ok() }
    }
}

fun BaseActivityRobot<*>.enqueueTimeline() {
    enqueueMain()
}

fun BaseActivityRobot<*>.assertTimeline() {
    assertMain()
}

fun enqueueDial() {
    stubIntent {
        action(Intent.ACTION_DIAL)
        url("tel:0810–999–5700")
        respondWith { ok() }
    }
}

fun assertDial() {
    sentIntent {
        action(Intent.ACTION_DIAL)
        url("tel:0810–999–5700")
    }
}

fun enqueueInternet() {
    stubIntent {
        action(Intent.ACTION_VIEW)
        url("https://intranet.co.itau/institucional")
        respondWith { ok() }
    }
}

fun assertInternet() {
    sentIntent {
        action(Intent.ACTION_VIEW)
        url("https://intranet.co.itau/institucional")
    }
}