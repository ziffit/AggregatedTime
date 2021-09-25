package de.fx.aggregatedtime

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import de.fx.aggregatedtime.room.Project
import de.fx.aggregatedtime.room.ProjectRepository
import de.fx.aggregatedtime.room.ProjectState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList


class DayAdapter(
    private val context: Context,
    private val dataSource: ArrayList<Day>,
    private val projectRepository: ProjectRepository
) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_day, parent, false)

        val dayView = rowView.findViewById(R.id.itemDayDate) as TextView
        val dayAggegatedView = rowView.findViewById(R.id.itemDayAggegated) as TextView
        val dayCalculatedView = rowView.findViewById(R.id.itemDayCalculated) as TextView

        val day = getItem(position) as Day

        dayView.text = "${day.date.dayOfMonth}. ${day.date.dayOfWeek.getDisplayName(TextStyle.FULL, context.resources.configuration.locales[0])}"

        if(LocalDate.now().isEqual(day.date)){
            rowView.setBackgroundColor(Color.RED)
        }

        dayAggegatedView.text = "0"
        dayCalculatedView.text = "-5"

        rowView.setOnClickListener {
            val bundle = bundleOf("day" to day)
            findNavController(parent).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }

        return rowView
    }

}