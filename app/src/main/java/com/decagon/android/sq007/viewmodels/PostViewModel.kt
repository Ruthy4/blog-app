package com.decagon.android.sq007.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.model.PostModel
import com.decagon.android.sq007.repository.Repository
import kotlinx.coroutines.flow.MutableSharedFlow

class PostViewModel(private val repository: Repository) : ViewModel(){


    private var _newPost = MutableLiveData<PostModel>()
        val newPost: LiveData<PostModel>
    get() = _newPost

    //listen for changes in the getPost function
    var post = repository.getPosts()

    //function to listen for changes in the addPost function in the repository
    fun addPost(posts: PostModel) {
        _newPost = repository.addPost(posts)
    }
}