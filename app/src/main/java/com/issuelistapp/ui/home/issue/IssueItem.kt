package com.issuelistapp.ui.home.issue

import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.issuelistapp.R
import com.issuelistapp.data.db.entities.Issue
import com.issuelistapp.data.preferences.PreferenceProvider
import com.issuelistapp.databinding.ItemIssueListBinding
import com.xwray.groupie.databinding.BindableItem
import net.simplifiedcoding.ui.movies.RecyclerViewClickListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class IssueItem(
    private val issues: Issue,
    private val listener: RecyclerViewClickListener,
    private val prefs: PreferenceProvider
) : BindableItem<ItemIssueListBinding>(){

    override fun getLayout() = R.layout.item_issue_list

    override fun bind(viewBinding: ItemIssueListBinding, position: Int) {
        viewBinding.setIssue(issues)

        viewBinding.layoutIssue.setOnClickListener {

            var key = issues.comments_url
            key = key?.substring(key?.substring(0, key?.lastIndexOf("/"))?.lastIndexOf("/") + 1)

            key?.let { it1 -> prefs.Write("comment_url", it1) }
            prefs.Write("title",issues.title)

          //  listener.onRecyclerViewItemClick(viewBinding.title,issues)

            Navigation.findNavController(viewBinding.root).navigate(R.id.commentFragment)
        }

        val url = issues.avatar_url
        val date = issues.updated_at!!.substring(0, 10)
        val dateNew = date?.let { changeFormat(it) }

        viewBinding.updatedAt.setText(dateNew)

        if (url !== null) {
            Glide.with(viewBinding.imageview.context)
                .load(url)
                .into(viewBinding.imageview)
        } else {
            viewBinding.imageview.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    fun changeFormat(currentFormat: String) : String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = dateFormat.parse(currentFormat)
        val formatter: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        return formatter.format(date)
    }
}