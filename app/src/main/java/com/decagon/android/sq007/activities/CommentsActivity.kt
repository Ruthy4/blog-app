package com.decagon.android.sq007.activities

import android.app.ProgressDialog
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
import com.decagon.android.sq007.services.ApiEndPointInterface
import com.decagon.android.sq007.services.RetrofitClient
import com.decagon.android.sq007.viewmodels.CommentViewModel
import com.decagon.android.sq007.viewmodels.CommentViewModelFactory
import kotlinx.android.synthetic.main.activity_comments.*
import retrofit2.Retrofit


class CommentsActivity : AppCompatActivity() {

    lateinit var commentViewModel: CommentViewModel
    lateinit var progressDialog : ProgressDialog
    lateinit var commentRecyclerView: RecyclerView
    lateinit var commentRecyclerViewAdapter: CommentAdapter
    lateinit var commentViewModelFactory: CommentViewModelFactory
    private val service: ApiEndPointInterface
    private var commentList = mutableListOf<CommentModel>()

    init {
        val retrofit: Retrofit = RetrofitClient.instance
        service = retrofit.create(ApiEndPointInterface::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)


        //initialise the view model repository class
        val repository = Repository()
        commentViewModelFactory = CommentViewModelFactory(repository)
        commentViewModel = ViewModelProvider(this, commentViewModelFactory).get(CommentViewModel::class.java)

        //build the progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()


        // get the views and the input
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

        commentRecyclerView = findViewById(R.id.comment_recyclerView)


        //observe the list cof comments from the view model
        //if its not empty, attach the list to populate the adapter
        commentViewModel.comments.observe(this, Observer {
            if (it != null){

               commentRecyclerView.setHasFixedSize(true)
                commentRecyclerView.layoutManager = LinearLayoutManager(this)
                commentRecyclerViewAdapter = CommentAdapter(it)
                commentRecyclerViewAdapter.addAllComment(getCommentsList(it,posId))
                commentRecyclerView.adapter = commentRecyclerViewAdapter
                progressDialog.dismiss()
            }else{
                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show()
            }
        })



        //onclick of the comment button, observe the new comment input
        comment_button.setOnClickListener{
            val newComment = findViewById<EditText>(R.id.add_commentET).text.toString()
            val commentObj =   CommentModel(0,0,"Ruth", "Unokaruth@gmail.com", newComment)

            if (newComment.isNotEmpty()) {
                commentViewModel.postComment(posId, commentObj)

                commentViewModel.commentNew.observe(this, Observer {
                    Log.d("NewComment", "onCreate: $it")
                    if (it != null) {
                        commentList.add(it)
                        val posId = intent.getIntExtra("position", 0)
                        commentRecyclerView.setHasFixedSize(true)
                        commentRecyclerView.layoutManager = LinearLayoutManager(this)
                        commentRecyclerViewAdapter.addNewComment(it)
                        commentRecyclerView.adapter = commentRecyclerViewAdapter
                        add_commentET.text.clear()
                    }
                })


            } else {
                Toast.makeText(this, "Add a new comment", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //get the list of comments and filter it by post id to display comment related to the post
    private fun getCommentsList (comments: List<CommentModel>, postId: Int): List<CommentModel> {
        return comments.filter{ it.postId == postId}
    }




}




