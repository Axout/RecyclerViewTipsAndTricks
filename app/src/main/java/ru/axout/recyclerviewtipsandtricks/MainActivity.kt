package ru.axout.recyclerviewtipsandtricks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.axout.recyclerviewtipsandtricks.adapter.FingerprintAdapter
import ru.axout.recyclerviewtipsandtricks.adapter.fingerprints.PostFingerprint
import ru.axout.recyclerviewtipsandtricks.adapter.fingerprints.TitleFingerprint
import ru.axout.recyclerviewtipsandtricks.databinding.ActivityMainBinding
import ru.axout.recyclerviewtipsandtricks.utils.getRandomFeed
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var adapter: FingerprintAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Hello timber")

        adapter = FingerprintAdapter(getFingerprints())

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        adapter.setItems(getRandomFeed(this))
    }

    private fun getFingerprints() = listOf(
        TitleFingerprint(),
        PostFingerprint()
    )
}