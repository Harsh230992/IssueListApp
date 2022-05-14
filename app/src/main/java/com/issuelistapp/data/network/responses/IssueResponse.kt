package com.issuelistapp.data.network.responses

import com.issuelistapp.ui.home.issue.User

data class IssueResponse (
    val title: String,
    val user : User,
    val body : String,
    val updated_at : String,
    val comments_url : String
)