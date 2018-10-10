package com.faskn.sonranemioldu.activities

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.faskn.sonranemioldu.R
import com.faskn.sonranemioldu.adapters.ViewPagerAdapter
import com.faskn.sonranemioldu.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val url = "http://api.sonraneoldu.com/v2/tags/"
        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->

            val tabList = response
                    .getJSONArray("data")

            val fragmentAdapter = ViewPagerAdapter(supportFragmentManager, tabList)
            viewpager_main.adapter = fragmentAdapter
        },
                Response.ErrorListener {
                    Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                })

        Volley.newRequestQueue(this.baseContext).add(request)
        Volley.newRequestQueue(this.baseContext).start()

        setTabs()

    }


    private fun setTabs() {
        doAsync {

            progress_main.visibility = View.VISIBLE
            tabs_main.setupWithViewPager(viewpager_main)

            // Add Vertical Gray lines between tab items

            val linearLayout = tabs_main.getChildAt(0) as LinearLayout
            linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(Color.GRAY)
            drawable.setSize(1, 1)
            linearLayout.dividerPadding = 25
            linearLayout.dividerDrawable = drawable
            // Ends here.
        }

        doAsyncResult {
            progress_main.visibility = View.GONE
        }
    }

}
