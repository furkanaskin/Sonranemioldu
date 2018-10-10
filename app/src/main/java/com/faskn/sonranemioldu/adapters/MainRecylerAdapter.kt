package com.faskn.sonranemioldu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faskn.sonranemioldu.R
import org.json.JSONArray
import org.json.JSONObject

class MainRecylerAdapter(val news: JSONArray) : RecyclerView.Adapter<MainRecylerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecylerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_dashboard_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.length()
    }

    override fun onBindViewHolder(holder: MainRecylerAdapter.ViewHolder, position: Int) {
        holder.bind(news.getJSONObject(position), position)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(newsItem: JSONObject, position: Int) {
            val title = view.findViewById(R.id.txt_cover) as TextView
            val newsImageArray = newsItem.getJSONArray("images").getJSONObject(0)
            val newsImageBase = newsImageArray.getString("baseUrl")
            val newsImageCover = newsImageArray.getString("name")
            val cover = view.findViewById<ImageView>(R.id.img_cover)
            val newsImageCoverURL = newsImageBase.toString() + newsImageCover.toString()

            Glide
                    .with(view.context)
                    .load(newsImageCoverURL)
                    .apply(RequestOptions().centerCrop())
                    .into(cover)

            title.text = newsItem["summary"].toString()

        }
    }
}