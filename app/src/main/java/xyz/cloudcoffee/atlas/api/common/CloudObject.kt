package xyz.cloudcoffee.atlas.api.common

interface CloudObject<T>
{
    companion object {
        fun <T> removeFrom(list : ArrayList<CloudObject<T>>?, ref : CloudObject<T>){
            list?.remove(ref)
        }
    }

    val page : String
    fun value(callback : (data : T) -> Unit)

}