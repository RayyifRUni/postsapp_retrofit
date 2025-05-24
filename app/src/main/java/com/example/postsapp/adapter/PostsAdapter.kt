package com.example.postsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postsapp.PostDetailActivity
import com.example.postsapp.R
import com.example.postsapp.model.Post

class PostsAdapter(
    private val context: Context,
    private val posts: List<Post>
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PostDetailActivity::class.java).apply {
                putExtra("POST_ID", post.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = posts.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvBody: TextView = itemView.findViewById(R.id.tvBody)
        private val tvUserId: TextView = itemView.findViewById(R.id.tvUserId)

        fun bind(post: Post) {
            tvTitle.text = post.title
            tvBody.text = post.body
            tvUserId.text = "User ID: ${post.userId}"
        }
    }
}