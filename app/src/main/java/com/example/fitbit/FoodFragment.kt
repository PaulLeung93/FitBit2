package com.example.fitbit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitbit.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FoodFragment : Fragment() {

    private val nutritionList = mutableListOf<Nutrition>()
    private lateinit var nutritionRV: RecyclerView
    private lateinit var nutritionAdapter : NutritionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food, container, false)

        //Define RecyclerView and Adapter
        nutritionRV = view.findViewById<RecyclerView>(R.id.recyclerView)
        nutritionAdapter = NutritionAdapter(requireContext(),nutritionList)
        nutritionRV.adapter = nutritionAdapter

        nutritionRV.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            nutritionRV.addItemDecoration(dividerItemDecoration)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch{

            (requireActivity().application as MyApplication).db.nutritionDao().getAll().collect { databaseList ->
                nutritionList.clear()
                databaseList.map { mappedList ->
                    nutritionList.addAll(listOf(mappedList))
                    nutritionAdapter.notifyDataSetChanged()
                }
            }
        }

        nutritionRV.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            nutritionRV.addItemDecoration(dividerItemDecoration)
        }


        //Calling an intent on Button Click
        val add = view.findViewById<Button>(R.id.button)
        add.setOnClickListener {
            val i = Intent(requireActivity(), DetailActivity::class.java)
            startActivity(i)
        }

        //Button to delete all Entries
        val delete = view.findViewById<Button>(R.id.btnDelete)
        delete.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (requireActivity().application as MyApplication).db.nutritionDao().deleteAll()
            }

            nutritionList.clear()
            nutritionAdapter.notifyDataSetChanged()
        }


        //Remove Item
        nutritionAdapter.setOnItemClickListener(object: NutritionAdapter.onItemClickListener{

            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(), "Item removed at position $position", Toast.LENGTH_LONG).show()
                val itemToDelete = nutritionList[position]

                lifecycleScope.launch(Dispatchers.IO) {
                    (requireActivity().application as MyApplication).db.nutritionDao().deleteItem(itemToDelete)
                }
                nutritionList.removeAt(position)
                nutritionAdapter.notifyItemRemoved(position)
            }
        })
    }

}