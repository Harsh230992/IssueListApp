package com.issuelistapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.issuelistapp.data.db.entities.Comment


@Dao
interface CommentDao {

    @Insert
    fun saveAllComment(comment : List<Comment>)

    @Query("SELECT * FROM Comment where flag=:flag")
    fun getComments(flag : String) : LiveData<List<Comment>>

    @Query("DELETE FROM Comment")
    fun deleteComments()

}