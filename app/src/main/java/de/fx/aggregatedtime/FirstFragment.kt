package de.fx.aggregatedtime

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import de.fx.aggregatedtime.databinding.FragmentFirstBinding
import de.fx.aggregatedtime.room.*
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    @Inject
    lateinit var projectRepository: ProjectRepository


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    private var actualMonth = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh(view.context)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
        binding.btnNextMonth.setOnClickListener {
            actualMonth = actualMonth.plusMonths(1)
            refresh(view.context)
        }
        binding.btnPrevMonth.setOnClickListener {
            actualMonth = actualMonth.minusMonths(1)
            refresh(view.context)
        }
        binding.resetMonth.setOnClickListener {
            actualMonth = LocalDate.now()
            refresh(view.context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDaysOfMonth(localDate: LocalDate): ArrayList<Day> {
            val start: Calendar = Calendar.getInstance()
        start.set(localDate.year,localDate.monthValue,localDate.dayOfMonth)
        val days: ArrayList<Day> = arrayListOf()
        for(d in 1 until start.getActualMaximum(Calendar.DAY_OF_MONTH)){
            try {
                val date = LocalDate.of(localDate.year, localDate.month, d)
                val timeAggregates = projectRepository.projectDao().getTimeAggregates(date)
                val day = Day(date,timeAggregates.stream().mapToInt { it.minutes }.sum(),-10)
                days.add(day)
            } catch ( e: DateTimeException){
                println("#### ERROR in DateParsing: $e")
            }
        }
        return days
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun refresh(context: Context) {
        binding.actualMonth.text = actualMonth.format(DateTimeFormatter.ofPattern("MMM yyyy"))

        val listView = binding.monthView

        var days:ArrayList<Day> = getDaysOfMonth(actualMonth)

        val adapter = DayAdapter(context, days, projectRepository)
        listView.adapter = adapter

        if (actualMonth.year < LocalDate.now().year ||
            actualMonth.year == LocalDate.now().year && actualMonth.month < LocalDate.now().month
        ) {
            binding.btnNextMonth.visibility = View.VISIBLE
        } else {
            binding.btnNextMonth.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}