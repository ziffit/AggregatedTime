package de.fx.aggregatedtime

import android.os.Build
import android.os.Bundle
import android.transition.Visibility
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import de.fx.aggregatedtime.databinding.FragmentFirstBinding
import de.fx.aggregatedtime.room.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
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

        val project = Project(UUID.randomUUID(), "test", "desc", ProjectState.ACTIVE)
        projectRepository.projectDao().insert(project)

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh()

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.btnNextMonth.setOnClickListener {
            actualMonth = actualMonth.plusMonths(1)
            refresh()
        }
        binding.btnPrevMonth.setOnClickListener {
            actualMonth = actualMonth.minusMonths(1)
            refresh()
        }
        binding.resetMonth.setOnClickListener {
            actualMonth = LocalDate.now()
            refresh()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun refresh() {
        binding.actualMonth.text = actualMonth.format(DateTimeFormatter.ofPattern("MMM yyyy"))
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