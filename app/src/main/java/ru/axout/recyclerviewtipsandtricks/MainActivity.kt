package ru.axout.recyclerviewtipsandtricks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import ru.axout.recyclerviewtipsandtricks.adapter.FingerprintAdapter
import ru.axout.recyclerviewtipsandtricks.adapter.Item
import ru.axout.recyclerviewtipsandtricks.adapter.animations.AddableItemAnimator
import ru.axout.recyclerviewtipsandtricks.adapter.animations.custom.SimpleCommonAnimator
import ru.axout.recyclerviewtipsandtricks.adapter.animations.custom.SlideInLeftCommonAnimator
import ru.axout.recyclerviewtipsandtricks.adapter.animations.custom.SlideInTopCommonAnimator
import ru.axout.recyclerviewtipsandtricks.adapter.decorations.FeedHorizontalDividerItemDecoration
import ru.axout.recyclerviewtipsandtricks.adapter.decorations.GroupVerticalItemDecoration
import ru.axout.recyclerviewtipsandtricks.adapter.fingerprints.HorizontalItemsFingerprint
import ru.axout.recyclerviewtipsandtricks.adapter.fingerprints.PostFingerprint
import ru.axout.recyclerviewtipsandtricks.adapter.fingerprints.TitleFingerprint
import ru.axout.recyclerviewtipsandtricks.databinding.ActivityMainBinding
import ru.axout.recyclerviewtipsandtricks.model.FeedTitle
import ru.axout.recyclerviewtipsandtricks.model.UserPost
import ru.axout.recyclerviewtipsandtricks.utils.SwipeToDelete
import ru.axout.recyclerviewtipsandtricks.utils.getRandomFeed
import ru.axout.recyclerviewtipsandtricks.utils.getRandomUserPost
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val titlesList: MutableList<Item> by lazy {
        MutableList(1) { FeedTitle("Актуальное за сегодня:") }
    }
    private val postsList: MutableList<Item> by lazy {
        getRandomFeed(this)
    }

    private val titleAdapter = FingerprintAdapter(listOf(TitleFingerprint()))
    private val postAdapter = FingerprintAdapter(
        listOf(
            PostFingerprint(::onSavePost),
            HorizontalItemsFingerprint(
                listOf(PostFingerprint(::onSavePost, 600)),
                70
            )
        )
    )
    private val concatAdapter = ConcatAdapter(
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build(),
        titleAdapter,
        postAdapter
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Hello timber")

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = concatAdapter

            addItemDecoration(FeedHorizontalDividerItemDecoration(70, listOf(R.layout.item_horizontal_list)))
            addItemDecoration(GroupVerticalItemDecoration(R.layout.item_post, 100, 0))
            addItemDecoration(GroupVerticalItemDecoration(R.layout.item_title, 0, 100))
            addItemDecoration(GroupVerticalItemDecoration(R.layout.item_horizontal_list, 0, 150))

            itemAnimator = AddableItemAnimator(SimpleCommonAnimator()).also { animator ->
                animator.addViewTypeAnimation(R.layout.item_post, SlideInLeftCommonAnimator())
                animator.addViewTypeAnimation(R.layout.item_title, SlideInTopCommonAnimator())
                animator.addDuration = 500L
                animator.removeDuration = 500L
            }
        }

        postAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT

        initSwipeToDelete()
        submitInitialListWithDelayForAnimation()
    }

    private fun onSavePost(post: UserPost) {
        val postIndex = postsList.indexOf(post)
        val newItem = post.copy(isSaved = post.isSaved.not())

        postsList.removeAt(postIndex)
        postsList.add(postIndex, newItem)
        postAdapter.submitList(postsList.toList())
    }

    private fun submitInitialListWithDelayForAnimation() {
        binding.recyclerView.postDelayed({
            titleAdapter.submitList(titlesList.toList())
            postAdapter.submitList(postsList.toList())
            postAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        }, 300L)
    }

    private fun initSwipeToDelete() {
        val onItemSwipedToDelete = { positionForRemove: Int ->
            val removedItem = postsList[positionForRemove]
            postsList.removeAt(positionForRemove)
            postAdapter.submitList(postsList.toList())

            showRestoreItemSnackbar(positionForRemove, removedItem)

        }
        val swipeToDeleteCallback = SwipeToDelete(onItemSwipedToDelete)
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.recyclerView)
    }

    private fun showRestoreItemSnackbar(position: Int, item: Item) {
        Snackbar.make(binding.recyclerView, "Item was deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                postsList.add(position, item)
                postAdapter.submitList(postsList.toList())
            }.show()
    }
}