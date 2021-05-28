package com.decagon.android.sq007.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.decagon.android.sq007.model.CommentModel
import com.decagon.android.sq007.model.PostModel
import com.decagon.android.sq007.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

   var listOfPosts = MutableLiveData<MutableList<PostModel>>()
    var listOfComments = MutableLiveData<MutableList<CommentModel>>()



    //a call to get posts
    fun getPosts() : MutableLiveData<MutableList<PostModel>> {

            RetrofitClient.api.getPostsList()
                .enqueue(object : Callback<List<PostModel>> {
                    override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>) {
                        if (response.isSuccessful) {
                            listOfPosts.value = response.body()!! as MutableList<PostModel>

                        } else {
                            Log.i("POSTS", "list of Posts -> Unsuccessful" + response.errorBody())
                        }
                    }
                    override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                        Log.d("LIST_ERROR", "List Of Posts: Error -> " + t.message)
                    }
                })

        return listOfPosts

    }

    //make a call to get comments
    fun getComments(position: Int): MutableLiveData<MutableList<CommentModel>> {
        RetrofitClient.api.getComments(position)
            .enqueue(object : Callback<List<CommentModel>>{
                override fun onFailure(call: Call<List<CommentModel>>, t: Throwable) {
                }

                override fun onResponse(call: Call<List<CommentModel>>, response: Response<List<CommentModel>>) {
                    if (response.isSuccessful){
                        listOfComments.value = response.body() as MutableList<CommentModel>?
                    }

                }

            })
        return listOfComments
    }



    //function to make the call to get new comments
    fun addNewComment(position: Int,comment: CommentModel) : MutableLiveData<CommentModel>{
        val newComment = MutableLiveData<CommentModel>()

        RetrofitClient.api.postComments(position, comment).enqueue(
            object: Callback<CommentModel>{

                override fun onFailure(call: Call<CommentModel>, t: Throwable) {

                }

                override fun onResponse(call: Call<CommentModel>, response: Response<CommentModel>) {
                    if (response.isSuccessful){
                        newComment.value = response.body()
                        Log.d("RepositoryMain", "addComments: ${newComment.value}")

                    }
                }

            })
        return newComment

    }



    //make a call to get a new post
    fun addPost(posts: PostModel): MutableLiveData<PostModel>{
        val newPost = MutableLiveData<PostModel>()

        RetrofitClient.api.addPost(posts).enqueue(
            object : Callback<PostModel>{

                override fun onFailure(call: Call<PostModel>, t: Throwable) {
                }

                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                   if (response.isSuccessful){
                       newPost.value = response.body()
                   }
                }

            }
        )

        return newPost
    }

}
