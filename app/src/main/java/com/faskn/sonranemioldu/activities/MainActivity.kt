package com.faskn.sonranemioldu.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.faskn.sonranemioldu.R
import com.faskn.sonranemioldu.utils.BaseActivity
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val newsRecyclerView = findViewById<RecyclerView>(R.id.recycler_dashboard)
        newsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val url = "http://api.sonraneoldu.com/v2/tags/1/stories"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->

            val news = response
                    .getJSONArray("data")

            newsRecyclerView.adapter = NewsAdapter(news)
        },
                Response.ErrorListener {
                    Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                })

        Volley.newRequestQueue(this.baseContext).add(request)
        Volley.newRequestQueue(this.baseContext).start()

    }

    class NewsAdapter(val news: JSONArray) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_dashboard_recycler, parent, false)
            return NewsViewHolder(view)
        }

        override fun getItemCount(): Int = news.length()

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            holder.bind(news.getJSONObject(position),position)
        }

        class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(newsItem: JSONObject, position: Int) {
//                val baseImgUrl= "https://image-cdn.sonraneoldu.com/images/"
                val title = view.findViewById(R.id.txt_cover) as TextView
                val newsImageArray = newsItem.getJSONArray("images").getJSONObject(0)
                // Kontrol
                Log.v("qqq",newsImageArray.toString())

                val newsImageBase = newsImageArray.getString("baseUrl")

                val newsImageCover = newsImageArray.getString("name")

                // Kontrol * Success!!
                Log.v("qqq",newsImageBase.toString()+newsImageCover.toString())


                val cover = view.findViewById<ImageView>(R.id.img_cover)

                val newsImageCoverURL = newsImageBase.toString()+newsImageCover.toString()

                Glide
                        .with(view.context)
                        .load(newsImageCoverURL)
                        .into(cover)

                title.text =newsItem["summary"].toString()

            }
        }
    }
}
