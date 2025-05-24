package com.example.postsapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.postsapp.api.RetrofitClient
import com.example.postsapp.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetailActivity : AppCompatActivity() {
    private lateinit var tvPostId: TextView
    private lateinit var tvUserId: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvBody: TextView

    companion object {
        private const val TAG = "PostDetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        initViews()

        val postId = intent.getIntExtra("POST_ID", 0)
        if (postId != 0) {
            fetchPostDetail(postId)
        } else {
            showError("Invalid Post ID")
            finish()
        }
    }

    private fun initViews() {
        tvPostId = findViewById(R.id.tvPostId)
        tvUserId = findViewById(R.id.tvUserId)
        tvTitle = findViewById(R.id.tvTitle)
        tvBody = findViewById(R.id.tvBody)
    }

    private fun fetchPostDetail(postId: Int) {
        RetrofitClient.apiService.getPost(postId).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful && response.body() != null) {
                    val post = response.body()!!
                    displayPostDetail(post)
                    Log.d(TAG, "Post detail loaded successfully")
                } else {
                    showError("Failed to load post detail")
                    Log.e(TAG, "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                showError("Network error: ${t.message}")
                Log.e(TAG, "Network error", t)
            }
        })
    }

    private fun displayPostDetail(post: Post) {
        tvPostId.text = "Post ID: ${post.id}"
        tvUserId.text = "User ID: ${post.userId}"
        tvTitle.text = post.title
        tvBody.text = post.body
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}