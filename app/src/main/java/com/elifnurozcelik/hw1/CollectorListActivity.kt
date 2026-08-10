package com.elifnurozcelik.hw1

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.elifnurozcelik.hw1.data.AppDatabase
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.RecyclerView
import com.elifnurozcelik.hw1.data.CollectorEntity
import com.elifnurozcelik.hw1.databinding.UpdateCollectorBinding

class CollectorListActivity : AppCompatActivity(), CollectorClickListener {

    private lateinit var adapter: CollectorAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collector_list)

        val recycler = findViewById<RecyclerView>(R.id.recyclerCollectors)
        recycler.layoutManager = LinearLayoutManager(this)

        db = AppDatabase.getDatabase(this)

        loadCollectors()
    }

    private fun loadCollectors() {
        lifecycleScope.launch {
            val collectors = db.collectorDao().getAllCollectors()
            adapter = CollectorAdapter(collectors, this@CollectorListActivity)
            findViewById<RecyclerView>(R.id.recyclerCollectors).adapter = adapter
        }
    }


    override fun onCollectorClick(collector: CollectorEntity) {
        val dialogBinding = UpdateCollectorBinding.inflate(layoutInflater)

        dialogBinding.etName.setText(collector.name)
        dialogBinding.etSurname.setText(collector.surname)
        dialogBinding.etYear.setText(collector.year.toString())
        dialogBinding.etCategory.setText(collector.category)
        dialogBinding.etItemCount.setText(collector.itemCount.toString())
        dialogBinding.etFavorite.setText(collector.favorite)

        AlertDialog.Builder(this)
            .setTitle("Update Collector")
            .setView(dialogBinding.root)
            .setPositiveButton("Update") { _, _ ->
                val updatedCollector = collector.copy(
                    name = dialogBinding.etName.text.toString(),
                    surname = dialogBinding.etSurname.text.toString(),
                    year = dialogBinding.etYear.text.toString().toIntOrNull() ?: collector.year,
                    category = dialogBinding.etCategory.text.toString(),
                    itemCount = dialogBinding.etItemCount.text.toString().toIntOrNull() ?: collector.itemCount,
                    favorite = dialogBinding.etFavorite.text.toString()
                )

                lifecycleScope.launch {
                    db.collectorDao().updateCollector(updatedCollector)
                    loadCollectors()
                }
            }
            .setNegativeButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    db.collectorDao().deleteCollector(collector)
                    loadCollectors()
                }
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

}