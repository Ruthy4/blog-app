package com.decagon.android.sq007.util

import com.decagon.android.sq007.model.CommentModel


const val BASE_URL = "https://jsonplaceholder.typicode.com"


fun getCommentsList (comments: List<CommentModel>, postId: Int): List<CommentModel> {
    return comments.filter{ it.postId == postId}
}