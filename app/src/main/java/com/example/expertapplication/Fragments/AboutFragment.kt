package com.example.expertapplication.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.expertapplication.Activities.MessageActivity
import com.example.expertapplication.Activities.RatesActivity
import com.example.expertapplication.Adapter.ReviewsAdapter
import com.example.expertapplication.Models.ReviewsModel
import com.example.expertapplication.R


class AboutFragment : Fragment() {
    private lateinit var ratesMeBtn: View
    private lateinit var messageBtn: View
    private lateinit var reviewsAdapter: ReviewsAdapter
    private val reviewsModelClassdataList = ArrayList<ReviewsModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        initialisation(view)
        reviewsAdapterFun()
        return view

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialisation(view: View) {
        ratesMeBtn = view.findViewById(R.id.ratemetv)
        messageBtn = view.findViewById(R.id.messagebtn)
        viewPager = view.findViewById(R.id.viewpager)
        recyclerView = view.findViewById(R.id.reviewsrecyclerview)
        messageBtn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, MessageActivity::class.java)
                it.startActivity(intent)
            }
        }
        ratesMeBtn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, RatesActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun reviewsAdapterFun() {
        reviewsAdapter = ReviewsAdapter(
            requireActivity().applicationContext, reviewsModelClassdataList
        )


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = reviewsAdapter
        recyclerView.addItemDecoration(CirclePagerIndicatorDecoration())
        this.reviewsModelClassdataList.clear()

        var movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)
        movie = ReviewsModel(
            "256 Reviews",
            "Posted by",
            "Kai",
            "April 2,2018",
            "Great for startup work!",
            "5.0",
            "I've worked with Teresa on several startup deals - both where I was the investor and when I was on the inside. In each case she clearly explained to me the ramifications of the documents I was executing. Additionally, she was exceptionally communicative " +
                    "and responsive - she was waiting on me, instead of the other way around. Recommended!",
            R.drawable.star
        )

        reviewsModelClassdataList.add(movie)

        reviewsAdapter.notifyDataSetChanged()

    }

    class CirclePagerIndicatorDecoration : RecyclerView.ItemDecoration() {

        private val colorActive = Color.parseColor("#FF000000")
        private val colorInactive = Color.parseColor("#C4C4C4")

        /**
         * Height of the space the indicator takes up at the bottom of the view.
         */
        private val mIndicatorHeight = (DP * 44).toInt()

        /**
         * Indicator stroke width.
         */
        private val mIndicatorStrokeWidth = DP * 7

        /**
         * Indicator width.
         */
        private val mIndicatorItemLength = DP * 0

        /**
         * Padding between indicators.
         */
        private val mIndicatorItemPadding = DP * 8

        /**
         * Some more natural animation interpolation
         */
        private val mInterpolator = AccelerateDecelerateInterpolator()

        private val mPaint = Paint()

        init {
            mPaint.strokeCap = Paint.Cap.ROUND
            mPaint.strokeWidth = mIndicatorStrokeWidth
            mPaint.style = Paint.Style.FILL
            mPaint.isAntiAlias = true
        }

        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)

            val itemCount = parent.adapter!!.itemCount

            // center horizontally, calculate width and subtract half from center
            val totalLength = mIndicatorItemLength * itemCount
            val paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding
            val indicatorTotalWidth = totalLength + paddingBetweenItems
            val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

            // center vertically in the allotted space
            val indicatorPosY = parent.height - mIndicatorHeight / 2f

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)


            // find active page (which should be highlighted)
            val layoutManager = parent.layoutManager as LinearLayoutManager?
            val activePosition = layoutManager!!.findFirstVisibleItemPosition()
            if (activePosition == RecyclerView.NO_POSITION) {
                return
            }

            // find offset of active page (if the user is scrolling)
            val activeChild = layoutManager.findViewByPosition(activePosition)
            val left = activeChild!!.left
            val width = activeChild.width

            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            val progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())

            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
        }

        private fun drawInactiveIndicators(
            c: Canvas,
            indicatorStartX: Float,
            indicatorPosY: Float,
            itemCount: Int
        ) {
            mPaint.color = colorInactive

            // width of item indicator including padding
            val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

            var start = indicatorStartX
            for (i in 0 until itemCount) {
                // draw the line for every item
                c.drawLine(
                    start,
                    indicatorPosY,
                    start + mIndicatorItemLength,
                    indicatorPosY,
                    mPaint
                )
                start += itemWidth
            }
        }

        private fun drawHighlights(
            c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
            highlightPosition: Int, progress: Float, itemCount: Int
        ) {
            mPaint.color = colorActive

            // width of item indicator including padding
            val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

            if (progress == 0f) {
                // no swipe, draw a normal indicator
                val highlightStart = indicatorStartX + itemWidth * highlightPosition

                c.drawLine(
                    highlightStart, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint
                )

            } else {
                var highlightStart = indicatorStartX + itemWidth * highlightPosition
                // calculate partial highlight
                val partialLength = mIndicatorItemLength * progress

                // draw the cut off highlight

                c.drawLine(
                    highlightStart + partialLength, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint
                )

                // draw the highlight overlapping to the next item as well
                if (highlightPosition < itemCount - 1) {
                    highlightStart += itemWidth

                    c.drawLine(
                        highlightStart, indicatorPosY,
                        highlightStart + partialLength, indicatorPosY, mPaint
                    )
                }
            }
        }

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = mIndicatorHeight
        }

        companion object {
            private val DP = Resources.getSystem().displayMetrics.density
        }
    }

}