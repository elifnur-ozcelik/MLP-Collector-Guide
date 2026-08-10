package com.elifnurozcelik.hw1
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.elifnurozcelik.hw1.databinding.ActivityThirdBinding
import androidx.lifecycle.lifecycleScope
import com.elifnurozcelik.hw1.data.AppDatabase
import com.elifnurozcelik.hw1.data.CollectorEntity
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {
    private lateinit var bindingThird: ActivityThirdBinding
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase))
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingThird = DataBindingUtil.setContentView(this, R.layout.activity_third)

        val name = intent.getStringExtra("NAME") ?: ""
        val surname = intent.getStringExtra("SURNAME") ?: ""
        val year = intent.getIntExtra("YEAR", 0)
        val category = intent.getStringExtra("Category") ?: ""
        bindingThird.category = category
        bindingThird.progress = 0

        bindingThird.btnLastBack.setOnClickListener {
            finish()
        }
        bindingThird.btnLastResult.setOnClickListener {
            val itemsText = bindingThird.txtPieceAnswer.text.toString().trim()

            val items = itemsText.toIntOrNull()
            if (items == null) {
                bindingThird.txtPieceAnswer.error = getString(R.string.err_year_number)
                return@setOnClickListener
            }


            val favorite = bindingThird.txtCharName.text?.toString().orEmpty()

            if (favorite.isBlank() || items <= 0) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.incomplete_selection_title))
                    .setMessage(getString(R.string.incomplete_selection_message))
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return@setOnClickListener
            }

            val db = AppDatabase.getDatabase(this)
            val dao = db.collectorDao()

            val collector = CollectorEntity(
                name = name,
                surname = surname,
                year = year,
                category = category,
                itemCount = items,
                favorite = favorite
            )

            lifecycleScope.launch {
                dao.insertCollector(collector)
            }


            val data = Intent().apply {
                putExtra("ITEMS", items)
                putExtra("FAV", favorite)
            }
            setResult(RESULT_OK, data)
            finish()
        }
        bindingThird.seekChar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                bindingThird.progress = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
