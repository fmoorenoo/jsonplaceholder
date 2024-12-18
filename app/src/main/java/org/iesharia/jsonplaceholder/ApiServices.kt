package org.iesharia.jsonplaceholder

import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("/posts")
    suspend fun getPosts(): Response<List<PostResponse>>
}

data class PostResponse(
    val title: String,
    val body: String
)
