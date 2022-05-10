package com.example.expertapplication.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.expertapplication.Fragments.HomeFragment
import com.example.expertapplication.Fragments.MessagesFragment
import com.example.expertapplication.Fragments.NotificationFragment
import com.example.expertapplication.Fragments.ProfileFragment
import com.example.expertapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var vpPager: ViewPager
    private lateinit var homeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        idViews()
        viewPagerSettingFun()
    }

    private fun idViews() {
        vpPager = findViewById(R.id.viewpager)
        bottomNavigation = findViewById(R.id.topbar)
        homeTextView = findViewById(R.id.hometextview)
    }

    @SuppressLint("SetTextI18n")
    private fun viewPagerSettingFun() {
        val adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->

                when (item.itemId) {
                    R.id.ic_home -> {
                        vpPager.currentItem = 0
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.ic_messages -> {
                        vpPager.currentItem = 1
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.ic_notification -> {
                        vpPager.currentItem = 2
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.ic_profile -> {
                        vpPager.currentItem = 3
                        return@OnNavigationItemSelectedListener true
                    }
                    else ->
                        vpPager.currentItem = 1
                }
                false
            }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.ic_home
        vpPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            // Set Toolbar title and menu items
            @SuppressLint("ResourceAsColor")
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigation.menu.getItem(0)
                        homeTextView.text = "Home"
                    }
                    1 -> {
                        bottomNavigation.menu.getItem(1)
                        homeTextView.text = "Messages"

                    }
                    2 -> {
                        bottomNavigation.menu.getItem(2)
                        homeTextView.text = "Notifications"
                    }
                    3 -> {
                        bottomNavigation.menu.getItem(3)
                        homeTextView.text = "Profile"
                    }
                }
            }

            // While view pager settling on new item/page, check the correct bottom navigation view item
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    val pos = vpPager.currentItem
                    bottomNavigation.menu.getItem(pos).isChecked = true
                }
            }

            // Not needed, must be implemented
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })
    }


    class MyPagerAdapter(fragmentManager: FragmentManager?) :
        FragmentPagerAdapter(fragmentManager!!) {
        // Returns total number of pages
        override fun getCount(): Int {
            return NUM_ITEMS
        }

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> MessagesFragment()
                2 -> NotificationFragment()
                3 -> ProfileFragment()

                else -> {
                    HomeFragment()
                }
            }
        }

        // Returns the page title for the top indicator

        companion object {
            private const val NUM_ITEMS = 4
        }

    }
}