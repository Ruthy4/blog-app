package com.decagon.android.sq007.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.adapters.PostAdapter
import com.decagon.android.sq007.model.PostModel
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.services.PostService
import com.decagon.android.sq007.services.RetrofitClient
import com.decagon.android.sq007.viewmodels.PostViewModel
import com.decagon.android.sq007.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), PostAdapter.OnItemClickListener {
    lateinit var progressDialog : ProgressDialog
    lateinit var viewModel:PostViewModel
    lateinit var postRecyclerView: RecyclerView
    lateinit var recyclerViewAdapter: PostAdapter
    lateinit var viewModelFactory: ViewModelFactory
    private val service: PostService

    init {
        val retrofit: Retrofit = RetrofitClient.instance
        service = retrofit.create(PostService::class.java)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository
        viewModelFactory = ViewModelFactory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
        postRecyclerView = findViewById(R.id.post_list_recyclerView)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()


        //observe the list of post from the view model and attach to adapter
        viewModel.post.observe(this, Observer {
            // get the recycler view, set fixed size and the layout manager
            if (it != null) {
                postRecyclerView.setHasFixedSize(true)
                postRecyclerView.layoutManager = LinearLayoutManager(this)
                recyclerViewAdapter = PostAdapter(it, this)
                postRecyclerView.adapter = recyclerViewAdapter
                postRecyclerView.isNestedScrollingEnabled = false
                recyclerViewAdapter.notifyDataSetChanged()
                progressDialog.dismiss()
            } else {
                progressDialog.dismiss()
            }
        })

        //check for changes as query text is been inputed
        posts_search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (posts_search_bar.isEmpty()){
                    postRecyclerView.adapter = recyclerViewAdapter
                    //posts_search_bar.onActionViewCollapsed()

                }
                else{
                    filter(newText)
                    posts_search_bar.setIconifiedByDefault(true)
                }

                return false
            }



            override fun onQueryTextSubmit(query: String): Boolean {
              //  posts_search_bar.clearFocus()
//                filter(query)
//               posts_search_bar.onActionViewCollapsed()
             //   posts_search_bar.setIconifiedByDefault(true)

                return false
            }


        })
        //receive the intent passing the new post from the AddPostActivity
        val receiver: Bundle? = intent.extras
        if (receiver != null){
            val newPost = receiver.getString("newPost")
            val postObject = newPost?.let { PostModel(0,0,"Lagos Today", it) }

            if (postObject != null){
                viewModel.addPost(0, postObject)
            }
            //observe the new postList and implement changes
            viewModel.postsList.observe(this, Observer {
                if (it != null) {
                    postRecyclerView.setHasFixedSize(true)
                    postRecyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerViewAdapter = PostAdapter(it, this)
                    postRecyclerView.adapter = recyclerViewAdapter
                    postRecyclerView.isNestedScrollingEnabled = false
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            })
        }

        //open the activity to make a new post
        make_new_postFAB.setOnClickListener{
            val intent = Intent(this, MakeAPostActivity::class.java)
            startActivity(intent)
        }

    }
    //function to search post list by title and attach the filtered list to the adapter
    fun filter (text: String){
        val filteredPost = ArrayList<PostModel>()
        viewModel.post.observe(this, Observer { post ->
            post.filterTo(filteredPost) {postModel->
            postModel.title.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
            }

            recyclerViewAdapter.filteredList(filteredPost)
            Log.i("List","$filteredPost")
        })


    }

    //passes the content on the post to the comments activity
    override fun onItemClick(post: PostModel, view: View) {
        val intent = Intent(this, CommentsActivity::class.java)
        intent.putExtra("userId", post.userId)
        intent.putExtra("id", post.id)
        intent.putExtra("title", post.title)
        intent.putExtra("body", post.body)
        intent.putExtra("position", post.id)


            startActivity(intent)

    }
}
