package com.issuelistapp.ui.home.issue

import androidx.lifecycle.ViewModel;
import com.issuelistapp.data.repositories.IssueRepository
import com.issuelistapp.util.lazyDeferred

class IssueViewModel(
    repository: IssueRepository
) : ViewModel() {

    val issues by lazyDeferred {
        repository.getIssues()
    }
}
