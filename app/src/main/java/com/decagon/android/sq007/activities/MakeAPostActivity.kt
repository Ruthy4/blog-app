package com.decagon.android.sq007.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.decagon.android.sq007.R
import kotlinx.android.synthetic.main.activity_make_a_post.*

class MakeAPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_a_post)

        backIv.setOnClickListener{
            val newPost = findViewById<EditText>(R.id.make_a_postTV).text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("newPost", newPost)
            startActivity(intent)
        }

    }


}