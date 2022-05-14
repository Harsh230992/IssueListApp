package com.issuelistapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Issue(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val body: String?=null,
    val avatar_url: String,
    val login: String,
    val updated_at: String?,
    val comments_url:String?
)