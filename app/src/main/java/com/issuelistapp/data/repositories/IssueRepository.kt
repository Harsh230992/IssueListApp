package com.issuelistapp.data.repositories

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.issuelistapp.data.db.AppDatabase
import com.issuelistapp.data.db.entities.Issue
import com.issuelistapp.data.network.MyApi
import com.issuelistapp.data.network.SafeApiRequest
import com.issuelistapp.data.preferences.PreferenceProvider
import com.issuelistapp.util.Coroutines
import com.issuelistapp.util.removeWhitespaces
import java.lang.Exception
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6

class IssueRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val issues = MutableLiveData<List<Issue>>()

    init {
        issues.observeForever {
            saveIssues(it)
        }
    }

    suspend fun getIssues(): LiveData<List<Issue>> {
        return withContext(Dispatchers.IO) {
            fetchIssues()
            db.getIssueDao().getIssues()
        }
    }

    private suspend fun fetchIssues() :  LiveData<List<Issue>> {
        val lastSavedAt = prefs.Read("key_saved_at")
        var list: ArrayList<Issue> = ArrayList()
          if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
               val response = apiRequest { api.getIssues() }

                for(res in response){
                    var body : String

                    if(res.body!=null){
                        body = res.body.removeWhitespaces()
                    }else{
                        body = res.body
                    }

                    var IssueModel = Issue(res.title,body,res.user.avatar_url,res.user.login,res.updated_at,res.comments_url)
                    list.add(IssueModel)
                }
                issues.postValue(list)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
       return issues
    }


    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }



    private fun saveIssues(issues: List<Issue>) {
        Coroutines.io {
            prefs.Write("key_saved_at",LocalDateTime.now().toString())
            db.getIssueDao().saveAllIssues(issues)
        }
    }

}