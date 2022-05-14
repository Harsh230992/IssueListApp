package com.issuelistapp.data.network.responses

import com.issuelistapp.ui.home.issue.User

data class CommentResponse (
    val user : User,
    val body : String,
    val updated_at : String
)