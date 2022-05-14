package com.issuelistapp.ui.home.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.issuelistapp.data.repositories.IssueRepository

@Suppress("UNCHECKED_CAST")
class IssueViewModelFactory(
    private val repository: IssueRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return IssueViewModel(repository) as T
    }
}