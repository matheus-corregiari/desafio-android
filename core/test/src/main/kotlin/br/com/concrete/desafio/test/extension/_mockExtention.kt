package br.com.concrete.desafio.test.extension

import android.arch.lifecycle.LiveData
import br.com.concrete.desafio.data.livedata.MediatorResponseLiveData
import br.com.concrete.desafio.data.livedata.ResponseLiveData
import br.com.concrete.desafio.data.model.DataResult
import br.com.concrete.desafio.data.model.enum.DataResultStatus
import br.com.concretesolutions.kappuccino.utils.doWait
import net.vidageek.mirror.dsl.Mirror
import org.mockito.Mockito

fun <T> T.assertCall(numberOfCall: Int): T {
    return Mockito.verify(this, Mockito.times(numberOfCall))
}

fun <T> ResponseLiveData<T>?.mockResponse(): ResponseLiveData<T> {
    return mockResponse(MediatorResponseLiveData())!!
}

fun <T> T?.mockResponse(value: T?): T? {
    Mockito.`when`(this).thenReturn(value)
    return value
}

fun <T> ResponseLiveData<T>.postMockValue(value: T) = postMockResponse(DataResult(value, null, DataResultStatus.SUCCESS))

fun <T> ResponseLiveData<T>.postMockLoading() = postMockResponse(DataResult(null, null, DataResultStatus.LOADING))
fun <T> ResponseLiveData<T>.postMockError(error: Throwable) = postMockResponse(DataResult(null, error, DataResultStatus.ERROR))
fun <T> ResponseLiveData<T>.postMockResponse(value: DataResult<T>) = postMockValue(value) as ResponseLiveData<T>

fun <T> LiveData<T>.postMockValue(value: T): LiveData<T> {
    Mirror().on(this).invoke().method("postValue").withArgs(value)
    doWait(200)
    return this
}