package com.manuellugodev.to_do.ui.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.manuellugodev.to_do.R
import com.manuellugodev.to_do.data.main.TaskRepositoryLocal
import com.manuellugodev.to_do.data.repositories.TasksRepository
import com.manuellugodev.to_do.data.sources.LocalTaskDataSource
import com.manuellugodev.to_do.domain.DataResult
import com.manuellugodev.to_do.room.TaskDatabase
import com.manuellugodev.to_do.sources.TaskRoomDataSource
import com.manuellugodev.to_do.ui.adapters.AdapterListTasks
import kotlinx.android.synthetic.main.fragment_list_task.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListTaskFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    private lateinit var db:TaskDatabase
    private lateinit var  source:LocalTaskDataSource
    private lateinit var repository:TasksRepository
    private val viewModel:MainViewModel by activityViewModels<MainViewModel> { MainViewModelProvider(repository) }


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

        db=TaskDatabase.getDatabase(requireContext())
        source= TaskRoomDataSource(db)
        repository=TaskRepositoryLocal(source)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_task, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvListTasks.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        bAddTaskFragment.setOnClickListener { findNavController().navigate(R.id.action_listTaskFragment_to_newTaskFragment) }

        viewModel.fetchListTask().observe(viewLifecycleOwner, Observer {
            when(it){

                is DataResult.Loading ->{
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_SHORT).show()
                }
                is DataResult.Success -> {

                    rvListTasks.adapter=AdapterListTasks(it.data)
                }

                is DataResult.Failure ->{
                    Log.e("Error",it.exception.message.toString())
                }
            }
        })





    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListTaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}