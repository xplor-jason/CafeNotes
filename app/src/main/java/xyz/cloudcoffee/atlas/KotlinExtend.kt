package xyz.cloudcoffee.atlas

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun <T> MutableLiveData<T>.postSelf() {
    this.postValue(this.value)
}

fun <T> MutableLiveData<T>.transaction(success : () -> Unit, action :(data : T) -> Unit){
    with(this){
        this.value?.let {
            action(it)
        }
        success()
    }
    postSelf()
}
fun <T> MutableLiveData<T>.uiTransaction(scope : CoroutineScope, action :(data : T) -> Unit){
    scope.launch {
        withContext(Dispatchers.Main){
            this@uiTransaction.value?.let {
                action(it)
            }
            postSelf()
        }
    }
}



