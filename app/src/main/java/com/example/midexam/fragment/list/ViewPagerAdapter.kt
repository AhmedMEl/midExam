package com.example.midexam.fragment.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val lstFragment=ArrayList<Fragment>()
    private val lstTitle=ArrayList<String>()

    override fun getCount(): Int {
        return lstTitle.size
    }

    override fun getItem(position: Int): Fragment {
        return lstFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return lstTitle.get(position)
    }

    fun addFragment(fragment: Fragment,title:String){
        lstFragment.add(fragment)
        lstTitle.add(title)
    }
}