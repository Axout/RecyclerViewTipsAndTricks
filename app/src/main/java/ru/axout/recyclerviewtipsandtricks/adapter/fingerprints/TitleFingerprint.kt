package ru.axout.recyclerviewtipsandtricks.adapter.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.axout.recyclerviewtipsandtricks.R
import ru.axout.recyclerviewtipsandtricks.adapter.BaseViewHolder
import ru.axout.recyclerviewtipsandtricks.adapter.Item
import ru.axout.recyclerviewtipsandtricks.adapter.ItemFingerprint
import ru.axout.recyclerviewtipsandtricks.databinding.ItemTitleBinding
import ru.axout.recyclerviewtipsandtricks.model.FeedTitle
import timber.log.Timber

class TitleFingerprint : ItemFingerprint<ItemTitleBinding, FeedTitle> {

    override fun isRelativeItem(item: Item) : Boolean {
        Timber.d("tag: isRelativeItem")
        return item is FeedTitle
    }

    override fun getLayoutId() : Int {
        Timber.d("tag: getLayoutId")
        return R.layout.item_title
    }

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemTitleBinding, FeedTitle> {
        val binding = ItemTitleBinding.inflate(layoutInflater, parent, false)
        Timber.d("tag: getViewHolder")
        return TitleViewHolder(binding)
    }

}

class TitleViewHolder(
    binding: ItemTitleBinding
) : BaseViewHolder<ItemTitleBinding, FeedTitle>(binding) {

    override fun onBind(item: FeedTitle) {
        Timber.d("tag: onBind")
        binding.tvFeedTitle.text = item.title
    }

}
