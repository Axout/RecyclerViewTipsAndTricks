package ru.axout.recyclerviewtipsandtricks.adapter.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.axout.recyclerviewtipsandtricks.R
import ru.axout.recyclerviewtipsandtricks.adapter.BaseViewHolder
import ru.axout.recyclerviewtipsandtricks.adapter.Item
import ru.axout.recyclerviewtipsandtricks.adapter.ItemFingerprint
import ru.axout.recyclerviewtipsandtricks.databinding.ItemPostBinding
import ru.axout.recyclerviewtipsandtricks.model.UserPost

class PostFingerprint(
    private val onSavePost: (UserPost) -> Unit
) : ItemFingerprint<ItemPostBinding, UserPost> {

    override fun isRelativeItem(item: Item) = item is UserPost

    override fun getLayoutId() = R.layout.item_post

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemPostBinding, UserPost> {
        val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding, onSavePost)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UserPost>() {

        override fun areItemsTheSame(oldItem: UserPost, newItem: UserPost) = oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: UserPost, newItem: UserPost) = oldItem == newItem

        override fun getChangePayload(oldItem: UserPost, newItem: UserPost): Any? {
            if (oldItem.isSaved != newItem.isSaved) return newItem.isSaved
            return super.getChangePayload(oldItem, newItem)
        }
    }

}

class PostViewHolder(
    binding: ItemPostBinding,
    val onSavePost: (UserPost) -> Unit
) : BaseViewHolder<ItemPostBinding, UserPost>(binding) {

    init {
        binding.tbLike.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            onSavePost(item)
        }
    }

    override fun onBind(item: UserPost) {
        super.onBind(item)
        with(binding) {
            tvCommentCount.text = item.commentsCount
            tvLikesCount.text = item.likesCount
            tvTitle.text = item.mainComment
            ivPostImage.setImageDrawable(item.image)
            tbLike.setChecked(item.isSaved)
        }
    }

    // Облегчаем метод onBind(). Здесь мы обновляем только то, что изменилось.
    // В данном случае, меняется значок закладки (tbLike).
    override fun onBind(item: UserPost, payloads: List<Any>) {
        super.onBind(item, payloads)
        val isSaved = payloads.last() as Boolean
        binding.tbLike.setChecked(isSaved)
    }

    private fun ImageView.setChecked(isChecked: Boolean) {
        val icon = when (isChecked) {
            true -> R.drawable.ic_bookmark_fill_24
            false -> R.drawable.ic_bookmark_border_24
        }
        setImageResource(icon)
    }

}