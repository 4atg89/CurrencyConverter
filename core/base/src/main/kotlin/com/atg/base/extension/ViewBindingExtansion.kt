package com.atg.base.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.atg.base.BaseFragment
import java.lang.reflect.ParameterizedType

@Suppress("Detekt.TooGenericExceptionCaught", "Detekt.SwallowedException")
internal fun Class<*>.checkIfMethodExists(): Boolean = try {
    getMethod("inflate", LayoutInflater::class.java)
    true
} catch (ex: Exception) {
    false
}

/**
 * this method simply trying to find viewBinding class
 */
internal fun Any.findViewBindingClass(): Class<*> {
    var javaClass: Class<*> = this.javaClass
    var result: Class<*>? = null
    while (result == null || !result.checkIfMethodExists()) {
        result = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments
            ?.firstOrNull { if (it is Class<*>) it.checkIfMethodExists() else false } as? Class<*>
        javaClass = javaClass.superclass
    }
    return result
}

internal fun <V : ViewBinding> BaseFragment<V>.inflate(
    inflater: LayoutInflater,
    container: ViewGroup?
): V {
    return findViewBindingClass().inflate(inflater, container)
}
@Suppress("Detekt.TooGenericExceptionCaught")
internal fun <V : ViewBinding> Class<*>.inflate(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
): V {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            .invoke(null, layoutInflater, container, false) as V
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}