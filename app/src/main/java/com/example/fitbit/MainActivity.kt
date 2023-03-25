package com.example.fitbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitbit.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val nutritionList = mutableListOf<Nutrition>()
    private lateinit var nutritionRV: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //RecyclerView
        nutritionRV = findViewById(R.id.recyclerView)
        val nutritionAdapter = NutritionAdapter(this,nutritionList)
        nutritionRV.adapter = nutritionAdapter

        lifecycleScope.launch{

            (application as MyApplication).db.nutritionDao().getAll().collect { databaseList ->
                databaseList.map { mappedList ->
                    // waters.clear()
                    nutritionList.addAll(listOf(mappedList))
                    nutritionAdapter.notifyDataSetChanged()
                }
            }
        }

        nutritionRV.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            nutritionRV.addItemDecoration(dividerItemDecoration)
        }


        //Calling an intent on Button Click
        val add = findViewById<Button>(R.id.button)
        add.setOnClickListener {
            val i = Intent(this@MainActivity, DetailActivity::class.java)
            startActivity(i)
        }

        //Button to delete all Entries
        val delete = findViewById<Button>(R.id.btnDelete)
        delete.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
            (application as MyApplication).db.nutritionDao().deleteAll()
            }

            nutritionList.clear()
            finish();
            startActivity(getIntent());
        }


        //Remove Item
        nutritionAdapter.setOnItemClickListener(object: NutritionAdapter.onItemClickListener{

            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity, "Item removed at position $position", Toast.LENGTH_LONG).show()
                nutritionList.removeAt(position)
                nutritionAdapter.notifyItemRemoved(position)
            }
        })

    }
}