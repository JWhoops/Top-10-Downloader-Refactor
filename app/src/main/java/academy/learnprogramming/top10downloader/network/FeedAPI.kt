package academy.learnprogramming.top10downloader.network

import academy.learnprogramming.top10downloader.models.FeedResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedAPI {
    @GET("{type}/top-{category}/all/{limit}/explicit.json")
    fun getTopList(@Path("type") type: String,
                   @Path("category") category: String,
                   @Path("limit") limit: Int): Flowable<FeedResponse>
}