package com.issuelistapp.ui.home.comments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.issuelistapp.R
import com.issuelistapp.data.db.entities.Comment
import com.issuelistapp.data.preferences.PreferenceProvider
import com.issuelistapp.util.Coroutines
import com.issuelistapp.util.hide
import com.issuelistapp.util.show
import kotlinx.android.synthetic.main.comment_fragment.*
import kotlinx.android.synthetic.main.issue_fragment.progress_bar
import kotlinx.android.synthetic.main.issue_fragment.recyclerview
import net.simplifiedcoding.ui.movies.RecyclerViewCommentClickListenerComment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CommentsFragment : Fragment(), KodeinAware, RecyclerViewCommentClickListenerComment {

    override val kodein by kodein()

    private val factory: CommentsViewModelFactory by instance()

    private val prefs: PreferenceProvider by instance()

    private lateinit var viewModel: CommentsViewModel

   lateinit var listner : RecyclerViewCommentClickListenerComment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.comment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(CommentsViewModel::class.java)
        listner=this
        bindUI()
    }


    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        val title1 = prefs.Read("title")
        title.setText(title1)
        viewModel.issues.await().observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            it?.let { it1 -> initRecyclerView(it1?.toCommentItem()) }
        })
    }

    private fun initRecyclerView(issueItem: List<CommentsItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(issueItem)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter

        }

    }

    private fun List<Comment>.toCommentItem() : List<CommentsItem>{
        return this.map {
            CommentsItem(it,listner)
        }
    }

    override fun onRecyclerViewItemClick(view: View, comment: Comment) {
        when (view.id) {
            R.id.title -> {
                Toast.makeText(
                    requireContext(),
                    "Quote Button Clicked  ${comment.avatar_url}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}
