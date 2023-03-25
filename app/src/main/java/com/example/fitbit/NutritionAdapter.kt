package com.example.fitbit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NutritionAdapter(private val context: Context, private val nutritionList: List<Nutrition>) :
    RecyclerView.Adapter<NutritionAdapter.ViewHolder>() {

    //Creating Listener Interface******************************************************

    //Listener member variable
    private lateinit var mListener : onItemClickListener

    //Define Listener Interace
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    //Define method to allow parent activity/fragment to define the listener
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //******************************************************************************************


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false)
        return ViewHolder(view, mListener)
    }



    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        private val food = itemView.findViewById<TextView>(R.id.tvFood)
        private val calories = itemView.findViewById<TextView>(R.id.tvCalories)

        init {
            itemView.setOnLongClickListener {
                listener.onItemClick(adapterPosition)
                true
            }

        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        fun bind(nutrition: Nutrition) {
            food.text = nutrition.food
            calories.text = nutrition.calories
        }

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = nutritionList[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int {
        return nutritionList.size
    }


}













