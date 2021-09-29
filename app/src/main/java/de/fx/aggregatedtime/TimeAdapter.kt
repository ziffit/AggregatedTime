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
import androidx.core.view.get
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import de.fx.aggregatedtime.room.Project
import de.fx.aggregatedtime.room.ProjectRepository
import de.fx.aggregatedtime.room.ProjectState
import de.fx.aggregatedtime.room.ProjectWithTimeAggregates
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList


class TimeAdapter(
    private val context: Context,
    private val dataSource: ArrayList<ProjectWithTimeAggregates>,
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
        val rowView = inflater.inflate(R.layout.list_item_time, parent, false)

        val projectView = rowView.findViewById(R.id.listItemTimeProject) as TextView
        val aggregateView = rowView.findViewById(R.id.listItemTimeAggregate) as TextView

        val projectWithTimeAggregates = getItem(position) as ProjectWithTimeAggregates

        projectView.text = projectWithTimeAggregates.project.name
        aggregateView.text =
            projectWithTimeAggregates.timeAggregates.stream().mapToInt(){ it.minutes }.sum().toString()

        rowView.setOnClickListener {
            parent.get(R.id.layerGroup).visibility = View.VISIBLE
        }

        return rowView
    }

}