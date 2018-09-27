package br.com.concrete.desafio.data.livedata

import android.arch.lifecycle.MediatorLiveData
import br.com.concrete.desafio.data.async
import br.com.concrete.desafio.data.model.DataResult
import br.com.concrete.desafio.data.model.enum.DataResultStatus

class MediatorResponseLiveData<T> : ResponseLiveData<T>() {

    private val sourceLiveData = MediatorLiveData<Any>()
    private val sourceObserver: (Any?) -> Unit = {}
    private var lastSource: ResponseLiveData<*>? = null

    val hasDataSource: Boolean
        get() = lastSource != null

    fun swapSource(source: ResponseLiveData<T>, discardAfterLoading: Boolean = false) {
        clearSource()
        sourceLiveData.addSource(source) {
            value = it
            if (it?.status != DataResultStatus.LOADING && discardAfterLoading) value = null
        }
        lastSource = source
    }

    fun <R> swapSource(source: ResponseLiveData<R>, transformation: (R) -> T) {
        clearSource()
        sourceLiveData.addSource(source) { data ->
            async {
                if (data == null) return@async
                val newValue = when (data.status) {
                    DataResultStatus.SUCCESS -> DataResult(data.data?.let(transformation), null, DataResultStatus.SUCCESS)
                    DataResultStatus.ERROR -> DataResult(null, data.error, DataResultStatus.ERROR)
                    DataResultStatus.LOADING -> DataResult(null, null, DataResultStatus.LOADING)
                }
                if (value != newValue) postValue(newValue)
            }
        }
        lastSource = source
    }

    override fun onActive() {
        super.onActive()
        if (!sourceLiveData.hasObservers()) sourceLiveData.observeForever(sourceObserver)
    }

    override fun onInactive() {
        super.onInactive()
        sourceLiveData.removeObserver(sourceObserver)
    }

    private fun clearSource() {
        lastSource?.let { sourceLiveData.removeSource(it) }
        lastSource = null
    }

    override fun compute() = Unit

    override fun abort() = Unit
}