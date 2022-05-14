package com.issuelistapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.issuelistapp.data.db.entities.Comment
import com.issuelistapp.data.db.entities.Issue

@Database(
    entities = [Issue::class, Comment::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getIssueDao(): IssueDao

    abstract fun getCommentDao(): CommentDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()
    }
}