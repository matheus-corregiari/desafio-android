package br.com.concrete.desafio.feature.repo

import android.support.test.runner.AndroidJUnit4
import br.com.concrete.desafio.test.BaseActivityTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListTest : BaseActivityTest<RepoListActivity>(RepoListActivity::class) {

    @Test
    fun whenReceiveEmptyList_shouldDisplayEmptyState() {
        repoList {
            launchActivity()
        } receiveEmptyList {
            emptyStateIsDisplayed()
        }
    }

    @Test
    fun whenReceiveLoading_shouldDisplayLoadingState() {
        repoList {
            launchActivity()
        } receiveLoading {
            loadingStateIsDisplayed()
        }
    }

    @Test
    fun whenReceiveError_shouldDisplayErrorState() {
        repoList {
            launchActivity()
        } receiveError {
            errorStateIsDisplayed()
        }
    }

    @Test
    fun whenReceiveList_shouldDisplayListState() {
        repoList {
            launchActivity()
        } receiveList {
            listStateIsDisplayed()
        }
    }

    @Test
    fun whenReceiveListWithNextPage_shouldRequestNextPage() {
        repoList {
            launchActivity()
        } receiveListWithNextPage {
            nextPageWasCalled()
        }
    }

    @Test
    fun whenReceiveNextPageSuccess_shouldAddItIntoTheList() {
        repoList {
            withListWithNextPage()
        } receiveNextPageSuccess {
            newItemsAddedIntoList()
        }
    }

    @Test
    fun whenReceiveNextPageError_shouldDisplayTryAgainItem() {
        repoList {
            withListWithNextPage()
        } receiveNextPageError {
            tryAgainItemIsDisplayed()
        }
    }

    @Test
    fun clickOnListItem_shouldOpenPullRequestList() {
        repoList {
            withListDisplayed()
        } clickOnItem {
            pullRequestListIsDisplayed()
        }
    }
}