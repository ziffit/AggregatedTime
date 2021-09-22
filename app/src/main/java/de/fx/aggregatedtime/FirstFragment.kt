package de.fx.aggregatedtime

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import de.fx.aggregatedtime.databinding.FragmentFirstBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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

        refreshMonth()

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.btnNextMonth.setOnClickListener {
            actualMonth = actualMonth.plusMonths(1)
            refreshMonth()
        }
        binding.btnPrevMonth.setOnClickListener {
            actualMonth = actualMonth.minusMonths(1)
            refreshMonth()
        }
        binding.actualMonth.setOnClickListener {
            actualMonth = LocalDate.now()
            refreshMonth()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun refreshMonth() {
        val locale = resources.configuration.locales.get(0)
        binding.actualMonth.text = actualMonth.format(DateTimeFormatter.ofPattern("MMM yyyy"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}