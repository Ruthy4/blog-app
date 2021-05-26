package com.decagon.android.sq007.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.adapters.CommentAdapter
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.viewmodels.CommentViewModel
import com.decagon.android.sq007.viewmodels.CommentViewModelFactory

class AddCommentActivity : AppCompatActivity() {
    lateinit var commentViewModelFactory: CommentViewModelFactory
    lateinit var commentViewModel: CommentViewModel
    lateinit var commentRecyclerView: RecyclerView
    lateinit var commentRecyclerViewAdapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)

        val getComment = intent.getStringExtra("key")
        val getPostTitle = intent.getStringExtra("title")
        val getPostId = intent.getIntExtra("idKey", 0)
        val getPostUserId = intent.getIntExtra("useridKey", 0)

        val bodyTv = findViewById<TextView>(R.id.post_body_textView)
        bodyTv.text = getComment

        val PostTitle = findViewById<TextView>(R.id.post_title_textView)
        PostTitle.text = getPostTitle

        val PostId = findViewById<TextView>(R.id.post_idTV)
        PostId.text = getPostId.toString()

        val PostUserId = findViewById<TextView>(R.id.post_user_idTV)
        PostUserId.text = getPostUserId.toString()

        val repository = Repository
        commentViewModelFactory = CommentViewModelFactory(repository)
        commentViewModel = ViewModelProvider(this, commentViewModelFactory).get(CommentViewModel::class.java)

//        send_button.setOnClickListener{
//
//
//            val newComment = findViewById<EditText>(R.id.comment_edit_text).text.toString()
//            Log.d("SEND", "onCreate: $newComment")
//            val commentObj =   CommentModel(0,0,"Ruth", "Unokaruth@gmail.com", newComment)
//
//
//            commentViewModel.postComment(commentObj)
//             onBackPressed()
//            finish()

          //  commentRecyclerView.adapter = commentRecyclerViewAdapter

           // fun pushComment(){

    //            val newComment = findViewById<EditText>(R.id.comment_edit_text).text.toString()
//                val intent = Intent(this, CommentsActivity::class.java)
//                intent.putExtra("comment",newComment )
//
//                startActivity(intent)
//                finish()
        //    }
        }
    }

//    private fun pushComment(){
//
//        val newComment = findViewById<EditText>(R.id.comment_edit_text).text.toString()
//        val intent = Intent(this, CommentsActivity::class.java)
//        intent.putExtra("comment",newComment )
//
//        startActivity(intent)
//        finish()
//    }
