package br.com.concrete.desafio.feature.pullrequest

import android.arch.lifecycle.ViewModel
import br.com.concrete.desafio.data.RepositoryProvider
import br.com.concrete.desafio.data.livedata.MediatorResponseLiveData
import br.com.concrete.desafio.data.model.dto.PullRequestDTO
import br.com.concrete.desafio.data.model.dto.RepoDTO

class PullRequestListViewModel : ViewModel() {

    val listPullRequest = MediatorResponseLiveData<List<PullRequestDTO>>()

    fun requestPullRequest(repo: RepoDTO) {
        listPullRequest.swapSource(RepositoryProvider.pullRequestRepository.list(repo))
    }
}