package xyz.cloudcoffee.atlas.api.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import xyz.cloudcoffee.atlas.api.Cloud
import xyz.cloudcoffee.atlas.api.common.Bookshelf
import xyz.cloudcoffee.atlas.api.common.CloudCaller
import xyz.cloudcoffee.atlas.api.common.CloudObject
import xyz.cloudcoffee.atlas.api.module.CloudDatabase
import xyz.cloudcoffee.atlas.api.module.CloudFactory
import xyz.cloudcoffee.atlas.api.module.ObjectParams
import xyz.cloudcoffee.atlas.framework.Atlas
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireDatabase(val cloud : Cloud, val factory : CloudFactory, app : FirebaseApp) : CloudDatabase {
    private val database = Firebase.firestore(app)

    private fun EMPTY(){}

    override suspend fun <T> publish(caller: CloudCaller, volume: String, data: T): CloudObject<T> {
        Atlas.out("DB >> Firebase.publish($volume)")
        return suspendCoroutine {  cont ->
            Atlas.out("Publishing $volume")
            database.collection(volume).add(data as Any).addOnSuccessListener {
                Atlas.out("Firebase published...> ${it.path}")
                val dataObject = factory.createCloudObject(ObjectParams(cloud, it.path, data))
                cont.resume(dataObject as CloudObject<T>)
            }.addOnFailureListener {
                Atlas.out("Error publishing to $volume")
                cont.resumeWith(Result.failure(it))
            }
        }
    }

    override suspend fun <T> volume(caller: CloudCaller, volume: String, convert : Class<T>): Bookshelf<T> {
        Atlas.out("DB >> Firebase.volume($volume)")
        return suspendCoroutine { cont ->
            database.collection(volume).get().addOnSuccessListener { query ->
                val items : ArrayList<CloudObject<T>> = ArrayList()

                query.documents.forEach { snap ->
                    val convertData = snap.toObject(convert)
                    val param = ObjectParams(cloud, snap.reference.path, convertData)
                    val cloudRef = factory.createCloudObject(param) as CloudObject<T>
                    items.add(cloudRef)
                }
                cont.resume(FireBookshelf(items))
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
        }
    }

    override suspend fun delistWithId(caller: CloudCaller, volume: String, pageId: String) {
        delist(caller, "$volume/$pageId")
    }

    override suspend fun delist(caller: CloudCaller, page: String) {
        Atlas.out("DB >> Firebase.delist($page)")
        return suspendCoroutine { cont ->
            database.document(page).delete().addOnSuccessListener {
                cont.resume(EMPTY())
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
        }
    }
}