package com.example.midexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.midexam.fragment.list.FragmentDone
import com.example.midexam.fragment.list.FragmentInprogress
import com.example.midexam.fragment.list.FragmentTodo
import com.example.midexam.fragment.list.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout=findViewById(R.id.tabLayout_id)
        viewPager=findViewById(R.id.viewPager_id)
        adapter= ViewPagerAdapter(supportFragmentManager)

        //add Fragment
        adapter.addFragment(FragmentTodo(),"ToDo")
        adapter.addFragment(FragmentInprogress(),"in_progress")
        adapter.addFragment(FragmentDone(),"Done")

        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}