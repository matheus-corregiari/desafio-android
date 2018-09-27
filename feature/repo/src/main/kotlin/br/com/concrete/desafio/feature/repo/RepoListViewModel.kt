package br.com.concrete.desafio.feature.repo

import android.arch.lifecycle.ViewModel
import br.com.concrete.desafio.data.RepositoryProvider

class RepoListViewModel : ViewModel() {

    val repoList = RepositoryProvider.repoRepository.list()

    fun search(page: Int) = RepositoryProvider.repoRepository.requestPage(page).onNext {
        repoList.getData()?.let { data ->
            data.items.addAll(it.items)
            data.nextPage = it.nextPage
        }
    }
}