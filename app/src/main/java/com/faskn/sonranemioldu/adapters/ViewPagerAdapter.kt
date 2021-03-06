package com.faskn.sonranemioldu.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.faskn.sonranemioldu.fragments.MainFragment
import org.json.JSONArray
import org.json.JSONObject


class ViewPagerAdapter(fragmentManager: FragmentManager, private val tabsList: JSONArray) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {

        val id = getIDFromJson(tabsList.getJSONObject(position))
        return MainFragment.newInstance(id)
    }

    override fun getCount(): Int {

        return tabsList.length()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getTitleFromJSON(tabsList.getJSONObject(position))
    }

    private fun getTitleFromJSON(pageList: JSONObject): String {

        // Get title from api
        return pageList["name"].toString()
    }

    private fun getIDFromJson(pageList: JSONObject): String {

        // Get url id from api
        return pageList["id"].toString()
    }
}

