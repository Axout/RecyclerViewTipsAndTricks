package ru.axout.recyclerviewtipsandtricks.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

// out - объект только возвращается, in - объект только принимается.
// List<out Any> соотвествует List<? extends Object>
// List<in Any> соотвествует List<? super Object>
abstract class BaseViewHolder<out V : ViewBinding, in I : Item>(
    val binding: V
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item: I)
}
