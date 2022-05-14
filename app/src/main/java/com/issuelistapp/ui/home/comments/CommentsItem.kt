package com.issuelistapp.ui.home.comments

import com.bumptech.glide.Glide
import com.issuelistapp.R
import com.issuelistapp.data.db.entities.Comment
import com.issuelistapp.databinding.ItemCommentListBinding
import com.issuelistapp.databinding.ItemIssueListBinding
import com.xwray.groupie.databinding.BindableItem
import net.simplifiedcoding.ui.movies.RecyclerViewClickListener
import net.simplifiedcoding.ui.movies.RecyclerViewCommentClickListenerComment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CommentsItem(
    private val comments: Comment,
    private val listener: RecyclerViewCommentClickListenerComment
) : BindableItem<ItemCommentListBinding>(){

    override fun getLayout() = R.layout.item_comment_list

    override fun bind(viewBinding: ItemCommentListBinding, position: Int) {

        viewBinding.setComment(comments)

        viewBinding.layoutIssue.setOnClickListener {
            //listener.onRecyclerViewItemClick(viewBinding.title,comments)
        }

        val url = comments.avatar_url
        val date = comments.updated_at!!.substring(0, 10)
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