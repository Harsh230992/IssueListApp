package net.simplifiedcoding.ui.movies

import android.view.View
import com.issuelistapp.data.db.entities.Issue

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, quote: Issue)
}