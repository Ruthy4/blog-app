package com.decagon.android.sq007.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.model.PostModel
import com.decagon.android.sq007.repository.Repository

class PostViewModel(private val repository: Repository) : ViewModel(){

    var postsList: LiveData<MutableList<PostModel>> = Repository.listOfPosts
//    private val listOfPosts = MutableLiveData<List<PostModel>>()
//
//    val postDataLive: LiveData<List<PostModel>>
//    get() = listOfPosts

    var post = repository.getPosts()

//    fun getUsers() {
//       listOfPosts.value = repository.getPosts()
//    }


//    fun returnPost() : List<PostModel>{
//        return repository.listOfPosts
//    }

    fun addPost(position: Int, posts: PostModel) {
      var  postsList = repository.addPost(position, posts)
    }
}