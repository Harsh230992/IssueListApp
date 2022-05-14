package net.simplifiedcoding.ui.movies

import android.view.View
import com.issuelistapp.data.db.entities.Comment

interface RecyclerViewCommentClickListenerComment {
    fun onRecyclerViewItemClick(view: View, comment: Comment)
}