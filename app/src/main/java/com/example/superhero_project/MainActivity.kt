package com.example.superhero_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var superImg = ""
    var superName = ""
    var superSecret = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val next_Btn : Button = findViewById(R.id.super_btn)
        val sup_Img : ImageView = findViewById(R.id.super_pic)
        val sup_Name : TextView = findViewById(R.id.super_name)
        getNextImage(next_Btn,sup_Img)
    }


    private fun getSuperImg(){
        val client = AsyncHttpClient()
        val rand = (1..100).random()  // Generate a random number between 1 and 731
        val imgUrl = "https://superheroapi.com/api/9319002574807739/$rand/image"
        val nameUrl = "https://superheroapi.com/api/9319002574807739/$rand/biography"
        val secretUrl = "https://superheroapi.com/api/9319002574807739/$rand/biography"

        updateSuperSecret(superSecret)
        updateSuperName(superName)

        client.get(imgUrl, object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.d("pic loaded", "pic successful$json")
                if (json != null) {
                    superImg = json.jsonObject.getString("url")


                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
               Log.d("fail", "errorResponse")
            }

        })

        client.get(nameUrl, object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
            Log.d("name loaded", "name successful$json")
            if (json != null) {
                superName = json.jsonObject.getString("name")
            }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("fail", "errorResponse")
            }
        })
        client.get(secretUrl, object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.d("secret loaded", "secret successful$json")
                if (json != null) {
                    superSecret = json.jsonObject.getString("full-name")
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("fail", "errorResponse")
            }
        })

    }
    private fun getNextImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            getSuperImg()

            Glide.with(this)
                .load(superImg)
                .fitCenter()
                .into(imageView)
        }


    }
    private fun updateSuperName(superheroName: String) {
        val sup_Name : TextView = findViewById(R.id.super_name)
        sup_Name.text = superheroName
    }

    private fun updateSuperSecret(superheroSecret: String) {
        val sup_Name : TextView = findViewById(R.id.super_secret)
        sup_Name.text = superheroSecret
    }
}