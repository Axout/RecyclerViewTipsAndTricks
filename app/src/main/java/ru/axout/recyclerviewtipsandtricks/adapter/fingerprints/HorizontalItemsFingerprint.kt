package ru.axout.recyclerviewtipsandtricks.adapter.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.axout.recyclerviewtipsandtricks.R
import ru.axout.recyclerviewtipsandtricks.adapter.BaseViewHolder
import ru.axout.recyclerviewtipsandtricks.adapter.FingerprintAdapter
import ru.axout.recyclerviewtipsandtricks.adapter.Item
import ru.axout.recyclerviewtipsandtricks.adapter.ItemFingerprint
import ru.axout.recyclerviewtipsandtricks.adapter.decorations.HorizontalDividerDecoration
import ru.axout.recyclerviewtipsandtricks.databinding.ItemHorizontalListBinding
import ru.axout.recyclerviewtipsandtricks.model.HorizontalItems

class HorizontalItemsFingerprint(
    private val fingerprintsList: List<ItemFingerprint<*, *>>,
    private val outerDivider: Int,
) : ItemFingerprint<ItemHorizontalListBinding, HorizontalItems> {

    override fun isRelativeItem(item: Item) = item is HorizontalItems

    override fun getLayoutId() = R.layout.item_horizontal_list

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemHorizontalListBinding, HorizontalItems> {
        val binding = ItemHorizontalListBinding.inflate(layoutInflater)
        return HorizontalItemsHolder(binding, fingerprintsList, outerDivider)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<HorizontalItems>() {

        override fun areItemsTheSame(oldItem: HorizontalItems, newItem: HorizontalItems) = oldItem == newItem

        override fun areContentsTheSame(oldItem: HorizontalItems, newItem: HorizontalItems) = oldItem == newItem

    }

}

class HorizontalItemsHolder(
    binding: ItemHorizontalListBinding,
    fingerprints: List<ItemFingerprint<*, *>>,
    outerDivider: Int,
) : BaseViewHolder<ItemHorizontalListBinding, HorizontalItems>(binding) {

    private val fingerprintAdapter = FingerprintAdapter(fingerprints)

    init {
        with(binding.rvHorizontalItems) {
            adapter = fingerprintAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(HorizontalDividerDecoration(50, outerDivider))
        }
    }

    override fun onBind(item: HorizontalItems) {
        super.onBind(item)
        fingerprintAdapter.submitList(item.items)
    }

}