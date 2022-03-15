package xyz.cloudcoffee.atlas.util

interface ScreenModel<T : androidx.viewbinding.ViewBinding> {
    fun onCreateModel(view : T)

}