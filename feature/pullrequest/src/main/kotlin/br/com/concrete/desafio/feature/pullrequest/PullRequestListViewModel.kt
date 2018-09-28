package br.com.concrete.desafio.feature.pullrequest

import android.arch.lifecycle.ViewModel
import br.com.concrete.desafio.data.RepositoryProvider
import br.com.concrete.desafio.data.extension.mapList
import br.com.concrete.desafio.data.livedata.MediatorResponseLiveData
import br.com.concrete.desafio.data.model.dto.RepoDTO

class PullRequestListViewModel : ViewModel() {

    val listPullRequest = MediatorResponseLiveData<List<PullRequestVO>>()

    fun requestPullRequest(repo: RepoDTO) {
        if (!listPullRequest.hasDataSource)
            listPullRequest.swapSource(RepositoryProvider.pullRequestRepository.list(repo).mapList(::PullRequestVO))
    }
}