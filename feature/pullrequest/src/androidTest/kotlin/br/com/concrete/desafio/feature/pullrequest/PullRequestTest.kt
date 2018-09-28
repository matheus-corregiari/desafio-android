package br.com.concrete.desafio.feature.pullrequest

import android.support.test.runner.AndroidJUnit4
import br.com.concrete.desafio.test.BaseActivityTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PullRequestTest : BaseActivityTest<PullRequestListActivity>(PullRequestListActivity::class) {

    @Test
    fun enterWithoutRepo_shouldDisplayErrorState() {
        pullRequest {
            enterWithoutRepo()
            errorStateIsDisplayed()
        }
    }

    @Test
    fun whenReceiveEmptyList_shouldDisplayEmptyState() {
        pullRequest {
            enterWithValidRepo()
        } receiveEmptyList {
            emptyStateIsDisplayed()
        }
    }

    @Test
    fun whenReceiveLoading_shouldDisplayLoadingState() {
        pullRequest {
            enterWithValidRepo()
        } receiveLoading {
            loadingStateIsDisplayed()
        }
    }

    @Test
    fun whenReceiveError_shouldDisplayErrorState() {
        pullRequest {
            enterWithValidRepo()
        } receiveError {
            errorStateIsDisplayed()
        }
    }

    @Test
    fun whenReceiveList_shouldDisplayListState() {
        pullRequest {
            enterWithValidRepo()
        } receiveList {
            listStateIsDisplayed()
        }
    }

    @Test
    fun clickOnListItem_shouldDisplayMessage() {
        pullRequest {
            withListDisplayed()
        } clickOnItem {
            itemMessageIsDisplayed()
        }
    }
}