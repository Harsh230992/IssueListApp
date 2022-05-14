package com.issuelistapp

import android.app.Application
import com.issuelistapp.data.db.AppDatabase
import com.issuelistapp.data.network.MyApi
import com.issuelistapp.data.network.NetworkConnectionInterceptor
import com.issuelistapp.data.preferences.PreferenceProvider
import com.issuelistapp.data.repositories.CommentRepository
import com.issuelistapp.data.repositories.IssueRepository
import com.issuelistapp.ui.home.comments.CommentsViewModelFactory
import com.issuelistapp.ui.home.issue.IssueViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { IssueRepository(instance(), instance(), instance()) }
        bind() from provider{ IssueViewModelFactory(instance()) }
        bind() from singleton { CommentRepository(instance(), instance(), instance()) }
        bind() from provider{ CommentsViewModelFactory(instance()) }

    }

}