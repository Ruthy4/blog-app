package com.decagon.android.sq007.util

import com.decagon.android.sq007.model.CommentModel


const val BASE_URL = "https://jsonplaceholder.typicode.com"

fun getPostsComment (id: Int, listOfComments: List<CommentModel>) {
    var commentId = "God help ruth"
    for (i in listOfComments.indices) {
        if (id == listOfComments[i].postId) {
            commentId = listOfComments[i].postId.toString()
        }
    }
}

fun getCommentsList (comments: List<CommentModel>, postId: Int): List<CommentModel> {
    return comments.filter{ it.postId == postId}
}