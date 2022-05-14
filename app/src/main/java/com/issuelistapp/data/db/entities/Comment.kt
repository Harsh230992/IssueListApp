package com.issuelistapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
    val avatar_url: String,
    val login: String,
    val updated_at: String?,
    val body: String?=null,
    val flag: String?=null
){
    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0
}