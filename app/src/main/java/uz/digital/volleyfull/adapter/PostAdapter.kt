package uz.digital.volleyfull.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.digital.volleyfull.databinding.UserLayoutBinding
import uz.digital.volleyfull.model.Post


class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallBack()) {
    lateinit var onClick: (id: Int) -> Unit
    lateinit var onLongClick: (Int) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    inner class PostViewHolder(private val binding: UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            with(binding) {
                title.text = post.title
                body.text = post.body
            }
            itemView.setOnLongClickListener {
                onLongClick(post.id)
                true
            }
            itemView.setOnClickListener {
                onClick(post.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            UserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}