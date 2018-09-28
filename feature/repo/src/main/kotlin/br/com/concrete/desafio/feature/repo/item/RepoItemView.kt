package br.com.concrete.desafio.feature.repo.item

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
import br.com.concrete.desafio.data.model.dto.RepoDTO
import br.com.concrete.desafio.feature.repo.R

class RepoItemView : RelativeLayout, ViewBinder<RepoDTO> {

    // region View
    private val title: TextView by viewProvider(R.id.title)
    private val description: TextView by viewProvider(R.id.description)
    private val forks: TextView by viewProvider(R.id.forks)
    private val stars: TextView by viewProvider(R.id.stars)
    private val avatar: ImageView by viewProvider(R.id.avatar)
    private val userLogin: TextView by viewProvider(R.id.userLogin)
    // endregion

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, R.style.RepoItemViewStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        View.inflate(context, R.layout.item_repo, this)
    }

    override fun bind(model: RepoDTO) {
        title.text = model.name.capitalize()
        description.text = model.description?.capitalize()
        forks.text = "${model.forks}"
        stars.text = "${model.stargazersCount}"
        avatar.loadUrl(model.owner.avatarUrl)
        userLogin.text = model.owner.login.capitalize()
    }
}