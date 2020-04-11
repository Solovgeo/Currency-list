package com.solovgeo.presentation.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.solovgeo.presentation.BuildConfig
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val isPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (BuildConfig.DEBUG) {
            check(!hasActiveObservers()) { "Multiple observers registered but only one will be notified of changes." }
        }

        if (hasActiveObservers()) {
            super.removeObservers(owner)
            Log.d("SingleLiveEvent", "SingleLiveEvent previousObserver removed, owner=$owner")
        }

        super.observe(owner, Observer<T> {
            if (isPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        isPending.set(true)
        super.setValue(t)
    }
}