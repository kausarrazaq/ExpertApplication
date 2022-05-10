package com.example.expertapplication.Activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.expertapplication.Fragments.*
import com.example.expertapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileForCreateGigActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var vpPager: ViewPager
    private lateinit var profileImageView: ImageView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilefor_create_gig)
        initViews()
        viewPagerSettingFun()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar_actionbar11)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        profileImageView = findViewById(R.id.profileimage)
        vpPager = findViewById(R.id.viewpager)
        bottomNavigation = findViewById(R.id.topbar)

    }

    private fun viewPagerSettingFun() {
        val adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->

                when (item.itemId) {
                    R.id.ic_about -> {


                        vpPager.currentItem = 0
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.ic_gigs -> {

                        vpPager.currentItem = 1
                        return@OnNavigationItemSelectedListener true
                    }
                    else -> vpPager.currentItem = 1
                }
                false
            }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.ic_about
        vpPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            // Set Toolbar title and menu items
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigation.menu.getItem(0)
                    }
                    1 -> {
                        bottomNavigation.menu.getItem(1)


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
                0 -> AboutFragment()
                1 -> GigsFragment()

                else -> {
                    AboutFragment()
                }
            }
        }

        // Returns the page title for the top indicator

        companion object {
            private const val NUM_ITEMS = 2
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}





