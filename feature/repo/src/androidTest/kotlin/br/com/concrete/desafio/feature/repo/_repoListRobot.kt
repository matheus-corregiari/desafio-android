package br.com.concrete.desafio.feature.repo

import android.support.test.rule.ActivityTestRule
import br.com.concrete.desafio.data.RepositoryProvider
import br.com.concrete.desafio.data.model.Page
import br.com.concrete.desafio.data.model.dto.RepoDTO
import br.com.concrete.desafio.test.BaseActivityRobot
import br.com.concrete.desafio.test.assertPullRequestList
import br.com.concrete.desafio.test.enqueuePullRequestList
import br.com.concrete.desafio.test.extension.assertCall
import br.com.concrete.desafio.test.extension.mockResponse
import br.com.concrete.desafio.test.extension.postMockError
import br.com.concrete.desafio.test.extension.postMockLoading
import br.com.concrete.desafio.test.extension.postMockValue
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.notDisplayed
import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView

fun RepoListTest.repoList(func: RepoListRobot.() -> Unit) = RepoListRobot(rule).apply(func)

class RepoListRobot(rule: ActivityTestRule<RepoListActivity>) : BaseActivityRobot<RepoListActivity>(rule) {
    private val repoListLiveData = RepositoryProvider.repoRepository.list().mockResponse()
    private val nextPageLiveData = RepositoryProvider.repoRepository.requestPage(1).mockResponse()

    fun withListDisplayed() {
        launchActivity()
        receiveList {
            listStateIsDisplayed()
        }
    }

    fun withListWithNextPage() {
        launchActivity()
        receiveListWithNextPage {
            nextPageWasCalled()
        }
    }

    fun errorStateIsDisplayed() {
        displayed { id(R.id.feature_repo_error) }

        notDisplayed {
            id(R.id.feature_repo_empty)
            id(R.id.feature_repo_recycler)
            id(R.id.feature_repo_loading)
        }
    }

    fun emptyStateIsDisplayed() {
        displayed { id(R.id.feature_repo_empty) }

        notDisplayed {
            id(R.id.feature_repo_recycler)
            id(R.id.feature_repo_loading)
            id(R.id.feature_repo_error)
        }
    }

    fun loadingStateIsDisplayed() {
        displayed { id(R.id.feature_repo_loading) }

        notDisplayed {
            id(R.id.feature_repo_recycler)
            id(R.id.feature_repo_empty)
            id(R.id.feature_repo_error)
        }
    }

    fun listStateIsDisplayed() {
        displayed { id(R.id.feature_repo_recycler) }

        notDisplayed {
            id(R.id.feature_repo_empty)
            id(R.id.feature_repo_loading)
            id(R.id.feature_repo_error)
        }
    }

    fun pullRequestListIsDisplayed() {
        assertPullRequestList()
    }

    fun nextPageWasCalled() {
        RepositoryProvider.repoRepository.assertCall(1).requestPage(1)
    }

    fun newItemsAddedIntoList() {
        recyclerView(R.id.feature_repo_recycler) { sizeIs(2) }
    }

    fun tryAgainItemIsDisplayed() {
        recyclerView(R.id.feature_repo_recycler) {
            sizeIs(2)
            atPosition(1) {
                displayed { text(R.string.paginating_try_again) }
            }
        }
    }

    infix fun receiveError(func: RepoListRobot.() -> Unit): RepoListRobot {
        repoListLiveData.postMockError(IllegalStateException(""))
        return apply(func)
    }

    infix fun receiveEmptyList(func: RepoListRobot.() -> Unit): RepoListRobot {
        repoListLiveData.postMockValue(Page(0, false))
        return apply(func)
    }

    infix fun receiveList(func: RepoListRobot.() -> Unit): RepoListRobot {
        repoListLiveData.postMockValue(Page(0, false, listOf(RepoDTO(description = null))))
        return apply(func)
    }

    infix fun receiveLoading(func: RepoListRobot.() -> Unit): RepoListRobot {
        repoListLiveData.postMockLoading()
        return apply(func)
    }

    infix fun clickOnItem(func: RepoListRobot.() -> Unit): RepoListRobot {
        enqueuePullRequestList()
        recyclerView(R.id.feature_repo_recycler) {
            atPosition(0) { click() }
        }
        return apply(func)
    }

    infix fun receiveListWithNextPage(func: RepoListRobot.() -> Unit): RepoListRobot {
        repoListLiveData.postMockValue(Page(0, true, listOf(RepoDTO()), nextPage = 1))
        return apply(func)
    }

    infix fun receiveNextPageSuccess(func: RepoListRobot.() -> Unit): RepoListRobot {
        nextPageLiveData.postMockValue(Page(0, false, listOf(RepoDTO())))
        return apply(func)
    }

    infix fun receiveNextPageError(func: RepoListRobot.() -> Unit): RepoListRobot {
        nextPageLiveData.postMockError(IllegalStateException(""))
        return apply(func)
    }
}