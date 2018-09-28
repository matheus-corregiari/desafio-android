package br.com.concrete.desafio.base.delegate

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class ViewModelProviderDelegate<out T : ViewModel>(private val clazz: KClass<T>, private val fromActivity: Boolean, private val fromParentFragment: Boolean) {

    private var viewModel: T? = null

    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>) = buildViewModel(activity = thisRef)

    operator fun getValue(thisRef: Fragment, property: KProperty<*>) = if (fromActivity)
        buildViewModel(activity = thisRef.activity as? AppCompatActivity)
    else buildViewModel(fragment = if (fromParentFragment) thisRef.parentFragment else thisRef)

    private fun buildViewModel(activity: AppCompatActivity? = null, fragment: Fragment? = null): T {
        if (viewModel != null) return viewModel!!

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(clazz.java)
        } ?: fragment?.let {
            viewModel = ViewModelProviders.of(it).get(clazz.java)
        } ?: throw IllegalStateException("Activity and Fragment null! =(")

        return viewModel!!
    }
}

fun <T : ViewModel> viewModelProvider(clazz: KClass<T>, fromActivity: Boolean = false, fromParentFragment: Boolean = false) =
        ViewModelProviderDelegate(clazz, fromActivity, fromParentFragment)