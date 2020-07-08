package academy.learnprogramming.top10downloader.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface FeedAPI {
    @GET
    fun getFakeStuff(): Call<ResponseBody>
}