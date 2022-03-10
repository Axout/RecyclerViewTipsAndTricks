package ru.axout.recyclerviewtipsandtricks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import timber.log.Timber

class FingerprintAdapter(
    private val fingerprints: List<ItemFingerprint<*, *>>,
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding, Item>>() {

    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, Item> {
        val inflater = LayoutInflater.from(parent.context)
        Timber.d("tag: onCreateViewHolder")
        return fingerprints.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseViewHolder<ViewBinding, Item> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, Item>, position: Int) {
        Timber.d("tag: onBindViewHolder")
        holder.onBind(items[position])
    }

    override fun getItemCount() : Int {
        val size = items.size
        Timber.d("tag: getItemCount $size")
        return size
    }

    // метод возвращает ID лейаута элемента, например: R.layout.item_title или R.layout.item_post
    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        Timber.d("tag: getItemViewType")
        return fingerprints.find { it.isRelativeItem(item) }
            ?.getLayoutId()
            ?: throw IllegalArgumentException("View type not found: $item")
    }

    fun setItems(newItems: List<Item>) {
        Timber.d("tag: setItems")
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}
