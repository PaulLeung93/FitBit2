package com.example.fitbit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        findViewById<Button>(R.id.btnSubmit).setOnClickListener{
            val food = findViewById<EditText>(R.id.inputFood).text.toString()
            val calories = findViewById<EditText>(R.id.inputCalories).text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                (application as MyApplication).db.nutritionDao().insert(
                    Nutrition(food, calories)
                )
            }

            val i = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(i)
        }


    }
}