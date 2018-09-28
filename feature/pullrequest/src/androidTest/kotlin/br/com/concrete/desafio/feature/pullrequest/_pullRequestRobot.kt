package br.com.concrete.desafio.feature.pullrequest

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import br.com.concrete.desafio.data.RepositoryProvider
import br.com.concrete.desafio.data.livedata.ResponseLiveData
import br.com.concrete.desafio.data.model.dto.PullRequestDTO
import br.com.concrete.desafio.data.model.dto.RepoDTO
import br.com.concrete.desafio.test.BaseActivityRobot
import br.com.concrete.desafio.test.extension.mockResponse
import br.com.concrete.desafio.test.extension.postMockError
import br.com.concrete.desafio.test.extension.postMockLoading
import br.com.concrete.desafio.test.extension.postMockValue
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.notDisplayed
import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView

fun PullRequestTest.pullRequest(func: PullRequestRobot.() -> Unit) = PullRequestRobot(rule).apply(func)

class PullRequestRobot(rule: ActivityTestRule<PullRequestListActivity>) : BaseActivityRobot<PullRequestListActivity>(rule) {
    private lateinit var pullRequestListLiveData: ResponseLiveData<List<PullRequestDTO>>

    fun enterWithValidRepo() {
        val repo = RepoDTO()
        pullRequestListLiveData = RepositoryProvider.pullRequestRepository.list(repo).mockResponse()
        launchActivity(Intent().putExtra("EXTRA_REPO", repo))
    }

    fun enterWithoutRepo() {
        launchActivity()
    }

    fun withListDisplayed() {
        enterWithValidRepo()
        receiveList {
            listStateIsDisplayed()
        }
    }

    fun errorStateIsDisplayed() {
        displayed { id(R.id.feature_pullrequest_error) }

        notDisplayed {
            id(R.id.feature_pullrequest_empty)
            id(R.id.feature_pullrequest_recycler)
            id(R.id.feature_pullrequest_loading)
        }
    }

    fun emptyStateIsDisplayed() {
        displayed { id(R.id.feature_pullrequest_empty) }

        notDisplayed {
            id(R.id.feature_pullrequest_recycler)
            id(R.id.feature_pullrequest_loading)
            id(R.id.feature_pullrequest_error)
        }
    }

    fun loadingStateIsDisplayed() {
        displayed { id(R.id.feature_pullrequest_loading) }

        notDisplayed {
            id(R.id.feature_pullrequest_recycler)
            id(R.id.feature_pullrequest_empty)
            id(R.id.feature_pullrequest_error)
        }
    }

    fun listStateIsDisplayed() {
        displayed { id(R.id.feature_pullrequest_recycler) }

        notDisplayed {
            id(R.id.feature_pullrequest_empty)
            id(R.id.feature_pullrequest_loading)
            id(R.id.feature_pullrequest_error)
        }
    }

    fun itemMessageIsDisplayed() {
        displayed { text("PR: 0 ¯\\_(ツ)_/¯") }
    }

    infix fun receiveError(func: PullRequestRobot.() -> Unit): PullRequestRobot {
        pullRequestListLiveData.postMockError(IllegalStateException(""))
        return apply(func)
    }

    infix fun receiveEmptyList(func: PullRequestRobot.() -> Unit): PullRequestRobot {
        pullRequestListLiveData.postMockValue(emptyList())
        return apply(func)
    }

    infix fun receiveList(func: PullRequestRobot.() -> Unit): PullRequestRobot {
        pullRequestListLiveData.postMockValue(listOf(PullRequestDTO()))
        return apply(func)
    }

    infix fun receiveLoading(func: PullRequestRobot.() -> Unit): PullRequestRobot {
        pullRequestListLiveData.postMockLoading()
        return apply(func)
    }

    infix fun clickOnItem(func: PullRequestRobot.() -> Unit): PullRequestRobot {
        recyclerView(R.id.feature_pullrequest_recycler) {
            atPosition(0) { click() }
        }
        return apply(func)
    }
}