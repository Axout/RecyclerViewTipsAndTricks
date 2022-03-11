package ru.axout.recyclerviewtipsandtricks.model

import android.graphics.drawable.Drawable
import ru.axout.recyclerviewtipsandtricks.adapter.Item

data class UserPost(
    val postId: Long,
    val mainComment: CharSequence,
    val likesCount: String,
    val commentsCount: String,
    val image: Drawable,
    val isSaved: Boolean
) : Item
