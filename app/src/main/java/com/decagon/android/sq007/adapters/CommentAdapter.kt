package com.decagon.android.sq007.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.model.CommentModel


class CommentAdapter(/*private var recyclerViewCommentList: List<CommentModel>*/):
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var recyclerViewCommentList = mutableListOf<CommentModel>()
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.name_textView)
        val email: TextView = view.findViewById(R.id.email_textView)
        val bodyTextView: TextView = view.findViewById(R.id.Comment_body_textView)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("CommentAdapter", "getItemCount: ${recyclerViewCommentList.size}")
        return recyclerViewCommentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.name.text = recyclerViewCommentList[position].name
        holder.email.text = recyclerViewCommentList[position].email
        holder.bodyTextView.text = recyclerViewCommentList[position].body

    }

    fun addNewComment(comment : CommentModel){
        this.recyclerViewCommentList.add(comment)
        notifyDataSetChanged()
    }

    fun addAllComment(comments: List<CommentModel>){
        recyclerViewCommentList.clear()
        this.recyclerViewCommentList = comments as MutableList<CommentModel>
    }

}