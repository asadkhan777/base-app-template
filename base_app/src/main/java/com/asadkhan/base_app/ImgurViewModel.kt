package com.asadkhan.base_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asadkhan.global.AbstractApplication.Companion.component
import com.asadkhan.global.domain.DataWrapper
import com.asadkhan.global.domain.PostData
import io.reactivex.Observable
import timber.log.Timber

class ImgurViewModel : ViewModel() {

    private val query = linkedMapOf(
            "q" to "science and technology",
            "q_type" to "jpg"
    )
    private val networkService by lazy { component().networkService() }

    private val posts: MutableLiveData<List<PostData>> by lazy {
        MutableLiveData<List<PostData>>().apply {
            println("Creating & Returning Post LiveData")
        }
    }

    fun getPosts(): LiveData<List<PostData>> = posts.also { println("Retrieving LiveData") }

    public fun getImages() {
        val subscribe = getPostsFromAPI().subscribe(posts::postValue) {
            it?.printStackTrace() ?: Timber.e("Something went wrong")
        }
    }

    private fun getPostsFromAPI(): Observable<List<PostData>> {
        return this.networkService
                .getWithQueries(DataWrapper::class.java, "/gallery/search/", query)
                .map { r -> r.body() }
                .map { t ->
                    t.data.filter {
                        !it.images.isNullOrEmpty()
                                && !it.images[0].link.isNullOrEmpty()
                                && (it.images[0].link?.endsWith(".jpg") ?: false)
                    }
                }

    }
}