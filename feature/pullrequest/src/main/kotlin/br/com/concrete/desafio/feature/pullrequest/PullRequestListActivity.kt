package br.com.concrete.desafio.feature.pullrequest

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import br.com.arch.toolkit.recycler.adapter.SimpleAdapter
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import br.com.concrete.desafio.base.BaseActivity
import br.com.concrete.desafio.base.delegate.extraProvider
import br.com.concrete.desafio.base.delegate.viewModelProvider
import br.com.concrete.desafio.base.delegate.viewProvider
import br.com.concrete.desafio.base.extension.addStatusBarPadding
import br.com.concrete.desafio.base.extension.enableBack
import br.com.concrete.desafio.base.extension.snack
import br.com.concrete.desafio.data.model.dto.RepoDTO
import br.com.concrete.desafio.feature.pullrequest.item.PullRequestItemView

private const val LIST_STATE = 0
private const val LOADING_STATE = 1
private const val EMPTY_STATE = 2
private const val ERROR_STATE = 3

class PullRequestListActivity : BaseActivity() {

    private val viewModel by viewModelProvider(PullRequestListViewModel::class)
    private val stateMachine = ViewStateMachine()
    private val adapter = SimpleAdapter(::PullRequestItemView)
            .withListener(::onPullRequestClick)

    private val repo: RepoDTO? by extraProvider("EXTRA_REPO")

    // region View
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val recyclerView: RecyclerView by viewProvider(R.id.feature_pullrequest_recycler)
    private val loading: View by viewProvider(R.id.feature_pullrequest_loading)
    private val error: View by viewProvider(R.id.feature_pullrequest_error)
    private val empty: View by viewProvider(R.id.feature_pullrequest_empty)
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request_list)

        setupToolbar()
        setupStateMachine()
        setupRecycler()

        // Start Observe Data Changes
        viewModel.listPullRequest
                .observeData(this, ::onPullRequestReceive)
                .observeShowLoading(this, ::onPullRequestLoadingReceive)
                .observeError(this, ::onPullRequestErrorReceive)

        // Request Data
        repo?.let(viewModel::requestPullRequest) ?: onPullRequestErrorReceive()
    }

    // region Data Handler
    private fun onPullRequestReceive(pullRequestList: List<PullRequestVO>) {
        adapter.setList(pullRequestList)
        stateMachine.changeState(if (adapter.itemCount == 0) EMPTY_STATE else LIST_STATE)
    }

    private fun onPullRequestErrorReceive() {
        stateMachine.changeState(ERROR_STATE)
    }

    private fun onPullRequestLoadingReceive() {
        stateMachine.changeState(LOADING_STATE)
    }
    // endregion

    // region Setup UI
    private fun setupToolbar() {
        toolbar.addStatusBarPadding()
        setSupportActionBar(toolbar)
        supportActionBar.enableBack()
        repo?.let { toolbar.title = it.name.capitalize() }
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
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }
    // endregion

    // region Click Handler
    private fun onPullRequestClick(pullRequest: PullRequestVO) {
        snack("PR: ${pullRequest.id} ¯\\_(ツ)_/¯")
    }
    // endregion
}