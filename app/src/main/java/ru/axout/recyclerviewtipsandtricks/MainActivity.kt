package ru.axout.recyclerviewtipsandtricks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.axout.recyclerviewtipsandtricks.adapter.FingerprintAdapter
import ru.axout.recyclerviewtipsandtricks.adapter.Item
import ru.axout.recyclerviewtipsandtricks.adapter.decorations.FeedHorizontalDividerItemDecoration
import ru.axout.recyclerviewtipsandtricks.adapter.decorations.GroupVerticalItemDecoration
import ru.axout.recyclerviewtipsandtricks.adapter.fingerprints.PostFingerprint
import ru.axout.recyclerviewtipsandtricks.adapter.fingerprints.TitleFingerprint
import ru.axout.recyclerviewtipsandtricks.databinding.ActivityMainBinding
import ru.axout.recyclerviewtipsandtricks.model.UserPost
import ru.axout.recyclerviewtipsandtricks.utils.getRandomFeed
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var adapter: FingerprintAdapter
    private val feed: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) { getRandomFeed(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Hello timber")

        adapter = FingerprintAdapter(getFingerprints())

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter

            addItemDecoration(FeedHorizontalDividerItemDecoration(70))
            addItemDecoration(GroupVerticalItemDecoration(R.layout.item_post, 100, 0))
            addItemDecoration(GroupVerticalItemDecoration(R.layout.item_title, 0, 100))
        }

        adapter.submitList(feed.toList())
    }

    private fun getFingerprints() = listOf(
        TitleFingerprint(),
        PostFingerprint(::onSavePost)
    )

    private fun onSavePost(post: UserPost) {
        val postIndex = feed.indexOf(post)
        val newItem = post.copy(isSaved = post.isSaved.not())

        feed.removeAt(postIndex)
        feed.add(postIndex, newItem)
        adapter.submitList(feed.toList())
    }
}