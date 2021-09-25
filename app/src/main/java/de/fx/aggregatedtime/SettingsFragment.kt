package de.fx.aggregatedtime

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.fx.aggregatedtime.databinding.FragmentSettingsBinding
import de.fx.aggregatedtime.room.Project
import de.fx.aggregatedtime.room.ProjectRepository
import de.fx.aggregatedtime.room.ProjectState
import java.util.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var projectRepository: ProjectRepository

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProjectList(view.context)
        binding.btnProjectSave.setOnClickListener {
            val project = Project(
                UUID.randomUUID(),
                binding.projectName.text.toString(),
                binding.projectDescription.text.toString(),
                ProjectState.ACTIVE
            )
            projectRepository.projectDao().insert(project)
            Snackbar.make(view, "Project Saved", Snackbar.LENGTH_LONG).show()
            loadProjectList(view.context)
        }

    }

    private fun loadProjectList(context: Context) {
        val listView = binding.projectList
        val projects = projectRepository.projectDao().getAll()
        val adapter = ProjectAdapter(context, projects as ArrayList<Project>, projectRepository)
        listView.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}