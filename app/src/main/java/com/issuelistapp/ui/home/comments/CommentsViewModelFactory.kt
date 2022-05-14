package com.issuelistapp.ui.home.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.issuelistapp.data.repositories.CommentRepository

@Suppress("UNCHECKED_CAST")
class CommentsViewModelFactory(
    private val repository: CommentRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(repository) as T
    }
}