package de.fx.aggregatedtime

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import de.fx.aggregatedtime.room.Project
import de.fx.aggregatedtime.room.ProjectRepository
import de.fx.aggregatedtime.room.ProjectState


class ProjectAdapter(
    private val context: Context,
    private val dataSource: ArrayList<Project>,
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
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_project, parent, false)

        val titleTextView = rowView.findViewById(R.id.itemProjectName) as TextView
        val switch = rowView.findViewById(R.id.itemProjectActive) as Switch
        val button = rowView.findViewById(R.id.itemProjectDelete) as Button

        val project = getItem(position) as Project

        titleTextView.text = project.name

        button.setOnClickListener {
            println("Delte ${project.uid}")
            projectRepository.projectDao().delete(project)
            dataSource.removeAt(position)
            Snackbar.make(parent, "Project Deleted", Snackbar.LENGTH_LONG).show()
            notifyDataSetChanged()
        }

        switch.isChecked = project.state == ProjectState.ACTIVE

        return rowView
    }

}