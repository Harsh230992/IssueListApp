package com.issuelistapp.ui.home.comments

import androidx.lifecycle.ViewModel;
import com.issuelistapp.data.repositories.CommentRepository
import com.issuelistapp.util.lazyDeferred

class CommentsViewModel(
    repository: CommentRepository
) : ViewModel() {

    val issues by lazyDeferred {
        repository.getComment()
    }
}
