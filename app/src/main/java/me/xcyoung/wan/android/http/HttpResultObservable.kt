package me.xcyoung.wan.android.http

import androidx.annotation.NonNull
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.observers.ResourceObserver
import me.xcyoung.wan.android.common.WanAndroidException

/**
 * @author ChorYeung
 * @since 2022/1/13
 */
fun <T> Observable<WanResponse<T>>.httpResult(): HttpResultObservable<T> {
    return HttpResultObservable(this)
}

fun <T> Observable<T>.subscribeWith(
    onSuccess: (data: T) -> Unit,
    onFailed: (exception: WanAndroidException) -> Unit
): Disposable {
    return this.subscribe({
        onSuccess(it)
    }) {
        val exception = if (it is WanAndroidException) {
            it
        } else {
            WanAndroidException.create(it)
        }
        onFailed(exception)
    }
}

class HttpResultObservable<T>(private val source: Observable<WanResponse<T>>) :
    Observable<T>() {

    override fun subscribeActual(observer: Observer<in T>) {
        val parent = HttpResultObserver(observer)
        observer.onSubscribe(parent)
        source.subscribe(parent)
    }

    class HttpResultObserver<T>(private val observer: Observer<in T>) :
        ResourceObserver<WanResponse<T>>() {

        override fun onNext(@NonNull txResponse: WanResponse<T>) {
            if (txResponse.errorCode == 0) {
                observer.onNext(txResponse.data!!)
            } else {
                observer.onError(WanAndroidException(txResponse.errorCode, txResponse.errorMsg))
            }
        }

        override fun onError(@NonNull e: Throwable) {
            observer.onError(e)
        }

        override fun onComplete() {
            observer.onComplete()
        }
    }
}
