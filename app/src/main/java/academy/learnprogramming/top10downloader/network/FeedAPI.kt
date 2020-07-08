package academy.learnprogramming.top10downloader.network

import academy.learnprogramming.top10downloader.models.Entry
import academy.learnprogramming.top10downloader.models.Feed
import academy.learnprogramming.top10downloader.models.FeedResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedAPI {
    @GET("{limit}/explicit.json")
    fun getResults(@Path("limit") limit: Int): Flowable<FeedResponse>
}