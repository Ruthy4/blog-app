package com.decagon.android.sq007.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.model.CommentModel
import com.decagon.android.sq007.repository.Repository

class CommentViewModel(private val repository: Repository) : ViewModel() {

    var comments: MutableLiveData<MutableList<CommentModel>> = Repository.listOfComments
    //var postsList: LiveData<MutableList<PostModel>> = PostRepository.listOfPosts

    private var _commentNew = MutableLiveData<CommentModel>()
    val commentNew : LiveData<CommentModel>
    get() = _commentNew


    fun getComment(position: Int) {
        comments = repository.getComments(position)
    }


    fun postComment(position: Int, comment: CommentModel){
        _commentNew = repository.addNewComment(position, comment)
//        comments.value = comments.value


    }



}