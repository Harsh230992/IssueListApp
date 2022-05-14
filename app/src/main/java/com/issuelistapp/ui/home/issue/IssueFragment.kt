package com.issuelistapp.ui.home.issue


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
import kotlinx.android.synthetic.main.issue_fragment.*
import com.issuelistapp.R
import com.issuelistapp.data.db.entities.Issue
import com.issuelistapp.data.preferences.PreferenceProvider
import com.issuelistapp.util.Coroutines
import com.issuelistapp.util.hide
import com.issuelistapp.util.show
import net.simplifiedcoding.ui.movies.RecyclerViewClickListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class IssueFragment : Fragment(), KodeinAware, RecyclerViewClickListener {

    override val kodein by kodein()

    private val factory: IssueViewModelFactory by instance()

    private val prefs: PreferenceProvider by instance()

    private lateinit var viewModel: IssueViewModel

   lateinit var listner : RecyclerViewClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.issue_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(IssueViewModel::class.java)
        listner=this
        bindUI()
    }


    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        viewModel.issues.await().observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            it?.let { it1 -> initRecyclerView(it1?.toIssueItem()) }
        })
    }

    private fun initRecyclerView(issueItem: List<IssueItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(issueItem)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter

        }

    }

    private fun List<Issue>.toIssueItem() : List<IssueItem>{
        return this.map {
            IssueItem(it,listner,prefs)
        }
    }

    override fun onRecyclerViewItemClick(view: View, issue: Issue) {
        when (view.id) {
            R.id.title -> {


// Navigation.findNavController(viewBinding.root)
                //  .navigate(R.id.commentFragment)
                Toast.makeText(
                    requireContext(),
                    "${issue.comments_url}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}
