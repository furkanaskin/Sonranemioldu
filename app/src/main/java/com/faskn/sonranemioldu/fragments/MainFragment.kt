package com.faskn.sonranemioldu.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.faskn.sonranemioldu.R
import com.faskn.sonranemioldu.adapters.MainRecyclerAdapter
import com.faskn.sonranemioldu.utils.BaseFragment
import org.json.JSONObject


class MainFragment : BaseFragment() {

    private val JsonContentID by lazy { arguments?.getString("JsonContentID") as String }

    companion object {
        fun newInstance(JsonContentID: String): Fragment {
            val mainFragment = MainFragment()
            val bundle = Bundle()
            bundle.putString("JsonContentID", JsonContentID)
            mainFragment.arguments = bundle
            return mainFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_main)
        newsRecyclerView.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)


        val url = "http://api.sonraneoldu.com/v2/tags/$JsonContentID/stories"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->

            val news = response
                    .getJSONArray("data")

            val itemOnClick: (View, Int, Int) -> Unit = { _, position, _ ->
                newsRecyclerView.adapter!!.notifyDataSetChanged()
                val newsFragment = NewsFragment.newInstance(position, JsonContentID)
                val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
                transaction.add(R.id.frame_news, newsFragment, "newsFragment").commit()
            }
            newsRecyclerView.adapter = MainRecyclerAdapter(news, itemClickListener = itemOnClick)
        },
                Response.ErrorListener {
                    Toast.makeText(this.context, "That didn't work!", Toast.LENGTH_SHORT).show()
                })

        Volley.newRequestQueue(this.context).add(request)
        Volley.newRequestQueue(this.context).start()


    }
}
