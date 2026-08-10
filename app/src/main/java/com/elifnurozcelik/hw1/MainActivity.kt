package com.elifnurozcelik.hw1
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elifnurozcelik.hw1.databinding.ActivityMainBinding
import com.elifnurozcelik.hw1.databinding.ResultBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase))
        )
    }
    private lateinit var bindingMain: ActivityMainBinding
    private val secLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val sel = result.data?.getParcelableExtra<Selection>("SEL")
                val unknown = getString(R.string.unknown)

                val name      = sel?.name ?: unknown
                val surname   = sel?.surname ?: unknown
                val year      = sel?.year ?: 0
                val category  = sel?.category ?: unknown
                val itemCount = sel?.itemCount ?: 0
                val favorite  = sel?.favorite ?: unknown

                val summaryText = getString(
                    R.string.summary_fmt,
                    name,
                    surname,
                    year,
                    getString(R.string.label_category),
                    category,
                    getString(R.string.label_item_count),
                    itemCount,
                    getString(R.string.label_favorite),
                    favorite
                )
                val dialogBinding = ResultBinding.inflate(layoutInflater)
                dialogBinding.txtSummaryContent.text = summaryText

                AlertDialog.Builder(this)
                    .setView(dialogBinding.root)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    private fun changeLanguage() {
        val currentLang = LocaleHelper.getLanguage(this)
        val newLang = if (currentLang == "tr") "en" else "tr"

        LocaleHelper.setLocale(this, newLang)

        Toast.makeText(
            this,
            getString(R.string.toast_language_changed),
            Toast.LENGTH_SHORT
        ).show()

        recreate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "Activity Loaded", Toast.LENGTH_SHORT).show()

        enableEdgeToEdge()
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        bindingMain.txtApp.startAnimation(blinkAnimation)

        bindingMain.btnLanguage.setOnClickListener {
            changeLanguage()
        }
        bindingMain.btnMainContinue.setOnClickListener {
            val name = bindingMain.txtName.text.toString().trim()
            val surname = bindingMain.txtSurname.text.toString().trim()
            val yearInput = bindingMain.txtYear.text.toString().trim()

            if (name.isEmpty() || surname.isEmpty() || yearInput.isEmpty()) {
                Snackbar.make(
                    bindingMain.root,
                    getString(R.string.err_fill_name_surname_year),
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (!name.all { it.isLetter() }) {
                bindingMain.txtName.error = getString(R.string.err_only_letters)
                return@setOnClickListener
            }

            if (!surname.all { it.isLetter() }) {
                bindingMain.txtSurname.error = getString(R.string.err_only_letters)
                return@setOnClickListener
            }

            val year = yearInput.toIntOrNull()
            if (year == null) {
                bindingMain.txtYear.error = getString(R.string.err_year_number)
                return@setOnClickListener
            }

            val sel = Selection(name = name, surname = surname, year = year)

            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("SEL", sel)
            }
            secLauncher.launch(intent)
        }

        bindingMain.btnlist.setOnClickListener {
            val intent = Intent(this, CollectorListActivity::class.java)
            startActivity(intent)
        }

    }
}
