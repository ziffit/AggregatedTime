package de.fx.aggregatedtime

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.fx.aggregatedtime.databinding.FragmentDayBinding
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class DayFragment : Fragment() {

    private var _binding: FragmentDayBinding? = null

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

        val info = binding.fragmentDayInfo
        val day = arguments?.get("day") as Day
        info.text = day.date.format(DateTimeFormatter.ISO_LOCAL_DATE)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}