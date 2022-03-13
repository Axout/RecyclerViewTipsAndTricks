package ru.axout.recyclerviewtipsandtricks.model

import android.os.Parcelable
import ru.axout.recyclerviewtipsandtricks.adapter.Item

data class HorizontalItems(
    val items: List<Item>,
    var state: Parcelable? = null
) : Item