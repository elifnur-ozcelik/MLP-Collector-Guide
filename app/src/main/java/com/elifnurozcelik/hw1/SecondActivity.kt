package com.elifnurozcelik.hw1
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.elifnurozcelik.hw1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase))
        )
    }
    lateinit var bindingSec: ActivitySecondBinding
    var sel: Selection? = null
    var pos = 0

    private val thirdLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val items = result.data?.getIntExtra("ITEMS", 0)
                val fav = result.data?.getStringExtra("FAV")
                sel = sel?.copy(itemCount = items, favorite = fav)
                val backIntent = Intent().apply {
                    putExtra("SEL", sel)
                }
                setResult(RESULT_OK, backIntent)
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingSec = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(bindingSec.root)

        sel = intent.getParcelableExtra("SEL")

        bindingSec.btnBack.setOnClickListener {
            finish()
        }

        bindingSec.spinG4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pos = position

                val selectedCategory = bindingSec.spinG4.selectedItem?.toString() ?: ""
                sel = sel?.copy(category = selectedCategory)

                if (pos == 0) {
                    bindingSec.imgDatabase.setImageResource(R.drawable.brushables)
                } else if (pos == 1) {
                    bindingSec.imgDatabase.setImageResource(R.drawable.blind_bags)
                } else if (pos == 2) {
                    bindingSec.imgDatabase.setImageResource(R.drawable.guardians_of_harmony)
                } else if (pos == 3) {
                    bindingSec.imgDatabase.setImageResource(R.drawable.plushies)
                } else if (pos == 4) {
                    bindingSec.imgDatabase.setImageResource(R.drawable.equestria_girls)
                } else if (pos == 5) {
                    bindingSec.imgDatabase.setImageResource(R.drawable.comics)
                } else {
                    bindingSec.imgDatabase.setImageResource(R.drawable.trading_cards)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        bindingSec.btnContinue.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java).apply {
                putExtra("Category", sel?.category ?: "")
                putExtra("NAME", sel?.name)
                putExtra("SURNAME", sel?.surname)
                putExtra("YEAR", sel?.year ?: 0)
            }
            thirdLauncher.launch(intent)
        }
    }
}
