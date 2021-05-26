package com.decagon.android.sq007.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decagon.android.sq007.model.CommentModel
import com.decagon.android.sq007.model.PostModel
import com.decagon.android.sq007.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

   // val listOfPosts = List<PostModel>()
   var listOfPosts = MutableLiveData<MutableList<PostModel>>()
    //var listOfComments = MutableLiveData<List<CommentModel>>()
    var listOfComments = MutableLiveData<MutableList<CommentModel>>()
    var newComment = MutableLiveData<CommentModel>()




    fun getPosts() : MutableLiveData<MutableList<PostModel>> {

            RetrofitClient.api.getPostsList()
                .enqueue(object : Callback<List<PostModel>> {
                    override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>) {
                        if (response.isSuccessful) {
                            listOfPosts.value = response.body()!! as MutableList<PostModel>
                        //  Log.d("Repository", "onResponse: ${listOfPosts}")
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


    fun addComments(position: Int, comment: CommentModel): MutableLiveData<MutableList<CommentModel>>{
        Log.d("RepositoryM", "addComments: ${listOfComments.value?.size}")

        RetrofitClient.api.postComments(position, comment).enqueue(
            object: Callback<CommentModel>{

                override fun onFailure(call: Call<CommentModel>, t: Throwable) {

                }

                override fun onResponse(call: Call<CommentModel>, response: Response<CommentModel>) {
                    if (response.isSuccessful){
                        newComment.value = response.body()
                        listOfComments.value?.add(0, newComment.value!!)
                        Log.d("UPDATED", "onResponse: ${newComment.value}")
                        Log.d("UPDATED", "onResponse: ${listOfComments.value?.size}")
                        Log.d("UPDATED", "onResponse: ${listOfComments.value}")
                    }
                }

            })

        Log.d("RepositoryM", "addComments: ${listOfComments.value?.size}")

        return listOfComments

    }

    fun addNewComment(position: Int,comment: CommentModel) : MutableLiveData<CommentModel>{

        RetrofitClient.api.postComments(position, comment).enqueue(
            object: Callback<CommentModel>{

                override fun onFailure(call: Call<CommentModel>, t: Throwable) {

                }

                override fun onResponse(call: Call<CommentModel>, response: Response<CommentModel>) {
                    if (response.isSuccessful){
                        newComment.value = response.body()
                        Log.d("RepositoryM", "addComments: ${newComment.value?.body}")
                       // listOfComments.value?.add(0, newComment.value!!)
                        Log.d("UPDATED", "onResponse: ${newComment.value}")
                        Log.d("UPDATED", "onResponse: ${listOfComments.value?.size}")
                        Log.d("UPDATED", "onResponse: ${listOfComments.value}")
                    }
                }

            })



        return newComment

    }



    fun addPost(position: Int, posts: PostModel): MutableLiveData<MutableList<PostModel>>{
        RetrofitClient.api.addPost(position, posts).enqueue(
            object : Callback<PostModel>{
                override fun onFailure(call: Call<PostModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                   if (response.isSuccessful){
                       var newPost = listOfPosts.value
                       newPost!!.add(0, posts)
                       listOfPosts.value = newPost
                   }
                }

            }
        )

        return listOfPosts
    }

}
