package br.com.concrete.desafio.feature.repo

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import br.com.concrete.desafio.base.BaseActivity
import br.com.concrete.desafio.base.adapter.PaginatingRecyclerAdapter
import br.com.concrete.desafio.base.delegate.viewModelProvider
import br.com.concrete.desafio.base.delegate.viewProvider
import br.com.concrete.desafio.base.extension.addStatusBarPadding
import br.com.concrete.desafio.base.intentPullRequestList
import br.com.concrete.desafio.data.model.Page
import br.com.concrete.desafio.data.model.dto.RepoDTO
import br.com.concrete.desafio.feature.repo.item.RepoItemView

private const val LIST_STATE = 0
private const val LOADING_STATE = 1
private const val EMPTY_STATE = 2
private const val ERROR_STATE = 3

class RepoListActivity : BaseActivity() {

    private val viewModel by viewModelProvider(RepoListViewModel::class)

    private val stateMachine = ViewStateMachine()
    private val adapter = PaginatingRecyclerAdapter(::RepoItemView)
            .loadMore(::onLoadMore)

    // region View
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val recyclerView: RecyclerView by viewProvider(R.id.feature_repo_recycler)
    private val loading: View by viewProvider(R.id.feature_repo_loading)
    private val error: View by viewProvider(R.id.feature_repo_error)
    private val empty: View by viewProvider(R.id.feature_repo_empty)
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        setupToolbar()
        setupStateMachine()
        setupRecycler()

        // Start Observe Data Changes
        viewModel.repoList
                .observeSingleData(this, ::onRepoReceive)
                .observeSingleShowLoading(this, ::onRepoLoadingReceive)
                .observeSingleError(this, ::onRepoErrorReceive)
    }

    private fun onLoadMore(nextPage: Int) {
        viewModel.search(nextPage)
                .observeSingleData(this, adapter::addPage)
                .observeSingleError(this, adapter::failPage)
    }

    // region Data Handler
    private fun onRepoReceive(repoPage: Page<RepoDTO>) {
        adapter.addPage(repoPage)
        stateMachine.changeState(if (adapter.itemCount == 0) EMPTY_STATE else LIST_STATE)
    }

    private fun onRepoErrorReceive() {
        stateMachine.changeState(ERROR_STATE)
    }

    private fun onRepoLoadingReceive() {
        stateMachine.changeState(LOADING_STATE)
    }
    // endregion

    // region Setup UI
    private fun setupToolbar() {
        toolbar.addStatusBarPadding()
        setSupportActionBar(toolbar)
    }

    private fun setupStateMachine() = stateMachine.setup {
        state(LOADING_STATE) {
            gones(recyclerView, error, empty)
            visibles(loading)
        }
        state(LIST_STATE) {
            gones(loading, error, empty)
            visibles(recyclerView)
        }
        state(EMPTY_STATE) {
            gones(recyclerView, error, loading)
            visibles(empty)
        }
        state(ERROR_STATE) {
            gones(recyclerView, loading, empty)
            visibles(error)
        }
        config {
            initialState = LOADING_STATE
        }
    }

    private fun setupRecycler() {
        adapter.withListener(::onRepoItemClick)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }
    // endregion

    // region Click Handler
    private fun onRepoItemClick(repo: RepoDTO) {
        startActivity(intentPullRequestList(repo))
    }
    // endregion
}