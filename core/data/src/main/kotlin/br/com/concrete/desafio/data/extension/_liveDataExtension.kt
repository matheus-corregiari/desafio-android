@file:JvmName("LiveDataUtils")

package br.com.concrete.desafio.data.extension

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import br.com.concrete.desafio.data.livedata.MediatorResponseLiveData
import br.com.concrete.desafio.data.livedata.ResponseLiveData

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: ((T) -> Unit)) = observe(owner, Observer { it?.let(observer) })

fun <T> LiveData<T>.observeUntil(owner: LifecycleOwner, observer: ((T) -> Boolean)) {
    observe(owner, object : Observer<T> {
        override fun onChanged(data: T?) {
            if (data?.let(observer) == true) removeObserver(this)
        }
    })
}

fun <T, R> ResponseLiveData<List<T>>.mapList(transformation: ((T) -> R)): ResponseLiveData<List<R>> {
    val result = MediatorResponseLiveData<List<R>>()
    result.swapSource(this) {
        it.map(transformation)
    }
    return result
}

fun MediatorLiveData<Throwable>.addErrorSource(source: LiveData<Throwable>) = addSource(source) {
    value = it
}