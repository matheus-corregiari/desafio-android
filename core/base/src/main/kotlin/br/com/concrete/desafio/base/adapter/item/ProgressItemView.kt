package br.com.concrete.desafio.base.adapter.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import br.com.arch.toolkit.recycler.adapter.ViewBinder
import br.com.concrete.desafio.base.R
import br.com.concrete.desafio.base.delegate.viewProvider

class ProgressItemView : FrameLayout, ViewBinder<Boolean> {

    // region View
    private val progress: View by viewProvider(R.id.progress_item_progress)
    private val error: View by viewProvider(R.id.progress_item_try_again)
    // endregion

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, R.style.ProgressItemViewStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        View.inflate(context, R.layout.item_progress, this)
    }

    override fun bind(model: Boolean) {
        progress.visibility = if (model) GONE else VISIBLE
        error.visibility = if (model) VISIBLE else GONE
    }

    override fun setOnClickListener(l: OnClickListener?) {
        error.setOnClickListener(l)
    }
}