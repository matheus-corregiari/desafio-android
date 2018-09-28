package br.com.concrete.desafio.feature.pullrequest.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.arch.toolkit.recycler.adapter.ViewBinder
import br.com.concrete.desafio.base.delegate.viewProvider
import br.com.concrete.desafio.base.extension.loadUrl
import br.com.concrete.desafio.feature.pullrequest.PullRequestVO
import br.com.concrete.desafio.feature.pullrequest.R

class PullRequestItemView : RelativeLayout, ViewBinder<PullRequestVO> {

    // region View
    private val title: TextView by viewProvider(R.id.title)
    private val description: TextView by viewProvider(R.id.description)
    private val avatar: ImageView by viewProvider(R.id.avatar)
    private val username: TextView by viewProvider(R.id.userLogin)
    // endregion

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, R.style.PullRequestItemViewStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        View.inflate(context, R.layout.item_pull_request, this)
    }

    override fun bind(model: PullRequestVO) {
        title.text = model.title
        description.text = model.description
        avatar.loadUrl(model.avatar)
        username.text = model.username
    }
}