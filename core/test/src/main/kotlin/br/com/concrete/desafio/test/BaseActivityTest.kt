package br.com.concrete.desafio.test

import android.app.Activity
import android.support.test.espresso.intent.rule.IntentsTestRule
import br.com.concrete.desafio.data.RepositoryProvider
import net.vidageek.mirror.dsl.Mirror
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.jvm.kotlinProperty

open class BaseActivityTest<AC : Activity>(activityClass: KClass<AC>) {

    @Rule
    @JvmField
    val rule = IntentsTestRule(activityClass.java, true, false)

    @Before
    fun setup() {
        mockRepositories()
    }

    private fun mockRepositories() {
        val fieldsToMock = RepositoryProvider::class.java.declaredFields.filter { it.type.name == Lazy::class.java.name }
        fieldsToMock.onEach(::mockRepositoryInstance)
    }

    private fun mockRepositoryInstance(field: Field) {
        val classToMock = Class.forName(field.kotlinProperty!!.returnType.toString())
        val mockedField = Mockito.mock(classToMock)
        Mirror().on(RepositoryProvider::class.java).set().field(field).withValue(lazy { mockedField })
    }
}