package com.issuelistapp.data.network

import com.issuelistapp.data.network.responses.CommentResponse
import com.issuelistapp.data.network.responses.IssueResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface MyApi {

    @GET("issues")
    suspend fun getIssues() : Response<List<IssueResponse>>

    @GET
    suspend fun getComments(@Url url: String) : Response<List<CommentResponse>>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://api.github.com/repos/square/okhttp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

