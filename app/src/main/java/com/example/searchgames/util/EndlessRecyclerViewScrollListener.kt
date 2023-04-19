package com.example.searchgames.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener

abstract class EndlessRecyclerViewScrollListener(private val layoutManager: GridLayoutManager) :
    OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 4
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            loading = true
            onLoadMore()

        }
    }

     abstract fun onLoadMore()
}