package de.fx.aggregatedtime

import android.os.Build
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import de.fx.aggregatedtime.databinding.FragmentDayBinding
import de.fx.aggregatedtime.room.Project
import de.fx.aggregatedtime.room.ProjectRepository
import de.fx.aggregatedtime.room.TimeAggregate
import java.lang.RuntimeException
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class DayFragment : Fragment() {

    private var _binding: FragmentDayBinding? = null

    @Inject
    lateinit var projectRepository: ProjectRepository

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDayBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projects = projectRepository.projectDao().getActiveProjects()

        val spinner = binding.fragmentDayProjectSpinner
        var entries = arrayOf("").toList()
        entries = entries.plus(projects.map { it.name })

        spinner.adapter = ArrayAdapter<CharSequence>(
            view.context,
            R.layout.support_simple_spinner_dropdown_item,
            entries
        )


        val info = binding.fragmentDayInfo
        val day = arguments?.get("day") as Day
        info.text = day.date.format(DateTimeFormatter.ISO_LOCAL_DATE)

        /*       binding.buttonSecond.setOnClickListener {
                   findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
               }*/
        binding.layerTimeClose.setOnClickListener {
            binding.layerGroup.visibility = View.INVISIBLE
        }

        binding.fragmentDayProjectSpinner.onItemSelectedListener =
            SelectListener(binding,projects,projectRepository,day)

        binding.fragmendDayLayerTimePlus15.setOnClickListener {
            val projectIdText = binding.hiddenActiveProjectId.text as String

            val projectId = UUID.fromString(projectIdText)
            val newTime:Int
            if(projectRepository.projectDao().hasTimeAggregate(projectId,day.date)){
                val timeAggregate:TimeAggregate = projectRepository.projectDao().getTimeAggregate(projectId,day.date)
                newTime= timeAggregate.minutes + 15
                timeAggregate.minutes = newTime
                projectRepository.projectDao().update(timeAggregate)
            }else{
                projectRepository.projectDao().insert(TimeAggregate(projectId,day.date,15))
                newTime = 15
            }
            binding.fraygmendDayLayerTime.text = readableTime(newTime)
        }
    }

    companion object {
        fun readableTime(minutes:Int):String{
            return "${minutes/60}:${minutes%60}"
        }
    }



    private class SelectListener(
        private val binding: FragmentDayBinding,
        private val projects: List<Project>,
        private val projectRepository: ProjectRepository,
        private val day:Day
    ) : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if (p2 > 0) {
                this.binding.layerGroup.visibility = View.VISIBLE
                binding.textView3.text = projects.get(p2 - 1).name
                val projectId=projects.get(p2 - 1).uid
                binding.hiddenActiveProjectId.text = projectId.toString()
                if(projectRepository.projectDao().hasTimeAggregate(projectId,day.date)){
                    val time = projectRepository.projectDao().getTimeAggregate(projectId,day.date).minutes
                    binding.fraygmendDayLayerTime.text = readableTime(time)
                }else {
                    binding.fraygmendDayLayerTime.text = "0"
                }
                println("p2 $p2   p3 $p3")
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}