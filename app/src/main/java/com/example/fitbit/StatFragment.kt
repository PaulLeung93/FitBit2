package com.example.fitbit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Byte


class StatFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stat, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val max = activity?.findViewById(R.id.MaxCal) as TextView
        val min = activity?.findViewById(R.id.minCal) as TextView
        val avg = activity?.findViewById(R.id.AVGCal) as TextView

        var smallestInt: Int = Byte.MAX_VALUE.toInt()
        var max_val: Int = Byte.MIN_VALUE.toInt()
        var numOfCal: Int = 0
        var sumOfCal: Int = 0


        lifecycleScope.launch(Dispatchers.IO) {
            for (item in (activity?.application as MyApplication).db.nutritionDao().getCal()){
                numOfCal = numOfCal + 1
                sumOfCal = sumOfCal + Integer.parseInt(item)

                avg.text = (sumOfCal/numOfCal).toString()

                if(Integer.parseInt(item)> max_val){
                    max_val = Integer.parseInt(item)
                }
                if(Integer.parseInt(item)< smallestInt){
                    smallestInt = Integer.parseInt(item)
                }
                max.text = max_val.toString()
                min.text = smallestInt.toString()
            }


        }



    }



}