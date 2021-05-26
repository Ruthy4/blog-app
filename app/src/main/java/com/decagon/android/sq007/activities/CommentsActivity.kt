package com.decagon.android.sq007.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.adapters.CommentAdapter
import com.decagon.android.sq007.model.CommentModel
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.services.PostService
import com.decagon.android.sq007.services.RetrofitClient
import com.decagon.android.sq007.util.getCommentsList
import com.decagon.android.sq007.viewmodels.CommentViewModel
import com.decagon.android.sq007.viewmodels.CommentViewModelFactory
import kotlinx.android.synthetic.main.activity_comments.*
import retrofit2.Retrofit

class CommentsActivity : AppCompatActivity() {

    lateinit var commentViewModel: CommentViewModel
    lateinit var commentRecyclerView: RecyclerView
    lateinit var commentRecyclerViewAdapter: CommentAdapter
    lateinit var commentViewModelFactory: CommentViewModelFactory
    private val service: PostService
    private var commentList = mutableListOf<CommentModel>()

    init {
        val retrofit: Retrofit = RetrofitClient.instance
        service = retrofit.create(PostService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        //open the activity to add new comments
        add_commentFAB.setOnClickListener{
            addComment()
        }

        //initialise the view model repository class
        val repository = Repository
        commentViewModelFactory = CommentViewModelFactory(repository)
        commentViewModel = ViewModelProvider(this, commentViewModelFactory).get(CommentViewModel::class.java)


        val postUserId = findViewById<TextView>(R.id.post_user_idTV)
        val postId = findViewById<TextView>(R.id.post_idTV)
        val postTitle = findViewById<TextView>(R.id.post_title_textView)
        val postBody = findViewById<TextView>(R.id.post_body_textView)

        postUserId.text = intent.getIntExtra("userId",0).toString()
        postId.text = intent.getIntExtra("id",0).toString()
        postTitle.text = intent.getStringExtra("title")
        postBody.text = intent.getStringExtra("body")
         val posId = intent.getIntExtra("position", 0)


        commentViewModel.getComment(posId)

       // commentViewModel.postComment(posId)


        commentRecyclerView = findViewById(R.id.comment_recyclerView)


        commentViewModel.comments.observe(this, Observer {
            if (it != null){

               commentRecyclerView.setHasFixedSize(true)
                commentRecyclerView.layoutManager = LinearLayoutManager(this)
                commentRecyclerViewAdapter = CommentAdapter()
                commentRecyclerViewAdapter.addAllComment(getCommentsList(it,posId))
                commentRecyclerView.adapter = commentRecyclerViewAdapter

            }else{
                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show()
            }
        })



//        val receiver: Bundle? = intent?.extras
//
//        if (receiver != null ){
//
//        val newCom = receiver.getString("comment")
//        val commentObj = newCom?.let { CommentModel(0,0,"Ruth", "Unokaruth@gmail.com", it) }
//
//            if (commentObj != null) {
//                commentViewModel.postComment(commentObj)
//            }

        //observe the comments
        send_button.setOnClickListener{

            val newComment = findViewById<EditText>(R.id.add_commentET).text.toString()
            val commentObj =   CommentModel(0,0,"Ruth", "Unokaruth@gmail.com", newComment)

//            Log.d("CHECKING", "onCreate: $commentObj")
            commentViewModel.postComment(posId ,commentObj)
            Log.d("CHECKING", "onCreate: ${commentViewModel.postComment(posId, commentObj)}")

            commentViewModel.commentNew.observe(this, Observer {
                Log.d("NewComment", "onCreate: $it")
                if(it != null){
                    commentList.add(it)
                    val posId = intent.getIntExtra("position", 0)
                   commentRecyclerView.setHasFixedSize(true)
                  commentRecyclerView.layoutManager = LinearLayoutManager(this)
                    commentRecyclerViewAdapter.addNewComment(it)
                    commentRecyclerView.adapter = commentRecyclerViewAdapter
                }
            })
        }
    }



    private fun addComment(){

        val commentBody = findViewById<TextView>(R.id.post_body_textView).text.toString()
        val postTitle = findViewById<TextView>(R.id.post_title_textView).text.toString()
        val postId = findViewById<TextView>(R.id.post_idTV).text.toString()
        val userId = findViewById<TextView>(R.id.post_user_idTV).text.toString()
        val intent = Intent(this, AddCommentActivity::class.java)


        intent.putExtra("key", commentBody)
        intent.putExtra("title", postTitle)
        intent.putExtra("idkey", postId)
        intent.putExtra("useridKey", userId)
        startActivity(intent)
        finish()
    }




}




