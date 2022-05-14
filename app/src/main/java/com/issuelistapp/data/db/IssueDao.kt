package com.issuelistapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.issuelistapp.data.db.entities.Issue

@Dao
interface IssueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllIssues(issue : List<Issue>)

    @Query("SELECT * FROM Issue")
    fun getIssues() : LiveData<List<Issue>>

}