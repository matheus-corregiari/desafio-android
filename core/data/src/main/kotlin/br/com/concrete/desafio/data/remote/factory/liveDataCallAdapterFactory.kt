package br.com.concrete.desafio.data.remote.factory

import br.com.concrete.desafio.data.extension.loadingResponse
import br.com.concrete.desafio.data.extension.nextPage
import br.com.concrete.desafio.data.extension.toDataResponse
import br.com.concrete.desafio.data.extension.toErrorResponse
import br.com.concrete.desafio.data.livedata.ResponseLiveData
import br.com.concrete.desafio.data.model.Page
import br.com.concrete.desafio.data.model.enum.DataResultStatus.SUCCESS
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != ResponseLiveData::class.java) return null

        val type = getParameterUpperBound(0, returnType as ParameterizedType) as? ParameterizedType
                ?: throw IllegalArgumentException("Resource must be Parameterized")
        return LiveDataCallAdapter<Any>(type)
    }
}

internal class LiveDataCallAdapter<RESULT>(private val responseType: Type) : CallAdapter<RESULT, ResponseLiveData<RESULT>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<RESULT>): ResponseLiveData<RESULT> {
        return object : ResponseLiveData<RESULT>() {
            override fun abort() {
                call.cancel()
            }

            @Throws(Exception::class)
            override fun compute() {
                try {
                    postValue(loadingResponse())

                    val response = makeRequest(call)
                    val data = response.body()
                    if (data is Page<*>) data.nextPage = response.nextPage()

                    postValue(data.toDataResponse(SUCCESS))
                } catch (error: Exception) {
                    postValue(error.toErrorResponse())
                    EventBus.getDefault().post(error)
                    throw error
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun <T> makeRequest(call: Call<T>): Response<T> {
        return if (call.isExecuted)
            call.clone().execute()
        else
            call.execute()
    }
}