package com.issuelistapp.data.repositories

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.issuelistapp.data.db.AppDatabase
import com.issuelistapp.data.db.entities.Comment
import com.issuelistapp.data.network.MyApi
import com.issuelistapp.data.network.SafeApiRequest
import com.issuelistapp.data.preferences.PreferenceProvider
import com.issuelistapp.util.Coroutines
import com.issuelistapp.util.removeWhitespaces
import java.lang.Exception
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6

class CommentRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val comment = MutableLiveData<List<Comment>>()

    /*init {
        comment.observeForever {
            saveComment(it)
        }
    }*/

    suspend fun getComment(): LiveData<List<Comment>> {
        return withContext(Dispatchers.IO) {
            var comment_url = prefs.Read("comment_url")
            fetchComment(comment_url!!)
            db.getCommentDao().getComments(comment_url!!)
        }
    }

    private suspend fun fetchComment(commentUrl: String):  LiveData<List<Comment>> {
        val lastSavedAt = prefs.Read("key_saved_at_comment")

        var list: ArrayList<Comment> = ArrayList()
       //   if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
               val response = apiRequest { api.getComments("issues/"+commentUrl) }

                for(res in response){

                    var body : String

                    if(res.body!=null){
                        body = res.body.removeWhitespaces()
                    }else{
                        body = res.body
                    }

                    var CommentModel = Comment(res.user.avatar_url,res.user.login,res.updated_at,body,commentUrl)
                    list.add(CommentModel)
                }
                comment.postValue(list)


                saveComment(list)

            } catch (e: Exception) {
                e.printStackTrace()
            }
      //  }
       return comment
    }


    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }



    private fun saveComment(comments: List<Comment>) {
        Coroutines.io {
            db.getCommentDao().deleteComments()
            prefs.Write("key_saved_at_comment",LocalDateTime.now().toString())
            db.getCommentDao().saveAllComment(comments)
        }
    }

}