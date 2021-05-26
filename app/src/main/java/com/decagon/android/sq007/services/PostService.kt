package com.decagon.android.sq007.services

import com.decagon.android.sq007.model.CommentModel
import com.decagon.android.sq007.model.PostModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {
    @GET("/Posts")
    fun getPostsList (): Call<List<PostModel>>

    @POST("/Posts")
    fun addPost(post1: Int, @Body post: PostModel)  : Call<PostModel>

    @GET("/Posts/{position}/Comments")
    fun getComments(@Path("position")url:Int): Call<List<CommentModel>>

    @POST("/Posts/{position}/Comments")
    fun postComments(@Path("position") url: Int, @Body comment: CommentModel): Call<CommentModel>

}