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

class MainRecyclerAdapter(val news: JSONArray, private val itemClickListener: (View, Int, Int) -> Unit) : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_dashboard_recycler, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.length()
    }

    override fun onBindViewHolder(holder: MainRecyclerAdapter.ViewHolder, position: Int) {

        holder.bind(news.getJSONObject(position), itemClickListener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(newsItem: JSONObject, itemClickListener: (View, Int, Int) -> Unit) {
            val txtDescription = view.findViewById(R.id.txt_news_description) as TextView
            val txtTitle = view.findViewById(R.id.txt_news_title) as TextView
            val txtCategory = view.findViewById(R.id.txt_news_category) as TextView
            val cover = view.findViewById(R.id.img_cover) as ImageView

            val newsCategory = newsItem.getJSONObject("primaryTag").getString("name")
            val newsImageArray = newsItem.getJSONArray("images").getJSONObject(0)
            val newsImageBase = newsImageArray.getString("baseUrl")
            val newsImageCover = newsImageArray.getString("name")
            val newsImageCoverURL = newsImageBase.toString() + newsImageCover.toString()

            Glide
                    .with(view.context)
                    .load(newsImageCoverURL)
                    .apply(RequestOptions().centerCrop())
                    .into(cover)

            txtDescription.text = newsItem["summary"].toString()
            txtTitle.text = newsItem["title"].toString()
            txtCategory.text = newsCategory

            itemView.setOnClickListener { itemClickListener(view, adapterPosition, itemViewType) }

        }
    }
}