package com.faskn.sonranemioldu.fragments


import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faskn.sonranemioldu.R
import kotlinx.android.synthetic.main.fragment_news.*
import org.json.JSONObject


class NewsFragment : Fragment() {


    private val contentPosition by lazy { arguments?.getInt("contentPosition") as Int }
    private val contentID by lazy { arguments?.getString("contentID") as String }

    companion object {
        fun newInstance(contentPosition: Int, contentID: String): Fragment {
            val newsFragment = NewsFragment()
            val bundle = Bundle()
            bundle.putInt("contentPosition", contentPosition)
            bundle.putString("contentID", contentID)
            newsFragment.arguments = bundle
            return newsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = "http://api.sonraneoldu.com/v2/tags/$contentID/stories"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->

            val news = response
                    .getJSONArray("data")

            val contentText = news
                    .getJSONObject(contentPosition)
                    .getString("content")
            val contentCover = news
                    .getJSONObject(contentPosition)
                    .getJSONArray("images")
                    .getJSONObject(0)
                    .getString("name")
            val contentTitle = news
                    .getJSONObject(contentPosition)
                    .getString("title")

            val baseImgUrl = "https://image-cdn.sonraneoldu.com/images/$contentCover"
            txt_context.text = Html.fromHtml(contentText)
            txt_context_title.text = contentTitle

            Glide.with(this)
                    .load(baseImgUrl)
                    .apply(RequestOptions().centerCrop())
                    .into(img_context_cover)

        },

                Response.ErrorListener {
                    Toast.makeText(this.context, "That didn't work!", Toast.LENGTH_SHORT).show()
                })

        Volley.newRequestQueue(this.context).add(request)
        Volley.newRequestQueue(this.context).start()

    }

}
