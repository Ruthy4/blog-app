package com.decagon.android.sq007.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.activities.PostActivity
import com.decagon.android.sq007.model.PostModel

class PostAdapter(private var recyclerViewPostList: MutableList<PostModel>, private val onClickListener: PostActivity):
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

  inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {


      val userId: TextView = view.findViewById(R.id.user_idTV)
      val id: TextView = view.findViewById(R.id.idTV)
      val titleTextView: TextView = view.findViewById(R.id.title_textView)
      val bodyTextView:  TextView = view.findViewById(R.id.body_textView)

      init {
          view.setOnClickListener(this)
      }

      //function to respond to click on the recycler item
      override fun onClick(v: View?) {
          val position = adapterPosition
          if (position != RecyclerView.NO_POSITION){
              Log.i("Position","$adapterPosition")
              onClickListener.onItemClick(recyclerViewPostList[adapterPosition], v!!)
          }
      }

  }

fun filteredList(filteredPost: MutableList<PostModel>){
    recyclerViewPostList = filteredPost
    notifyDataSetChanged()

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)

        return ViewHolder(view)
    }

    //get list size
    override fun getItemCount(): Int {
       return recyclerViewPostList.size
    }

    //binds the view with the data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.userId.text = recyclerViewPostList[position].userId.toString()
        holder.id.text = recyclerViewPostList[position].id.toString()
        holder.titleTextView.text = recyclerViewPostList[position].title
        holder.bodyTextView.text = recyclerViewPostList[position].body
    }


    //add the new post to the list of posts
    fun addNewPost(post : PostModel){
        recyclerViewPostList.add(0, post)
        notifyItemChanged(0)
    }

    //interface for the click on recyclerview
    interface OnItemClickListener{
        fun onItemClick(post: PostModel, view: View)
    }
}