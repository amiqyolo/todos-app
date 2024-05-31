package com.aplimelta.todos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aplimelta.todos.data.response.ApiResponse
import com.aplimelta.todos.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding
    private val viewModel: TodosViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val dataId = arguments?.getString("EXTRA_ID")

            if (dataId != null) {
                viewModel.todo.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ApiResponse.Error -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                        }
                        is ApiResponse.Success -> {
                            val data = result.data
                            Toast.makeText(requireContext(), "data:${data}", Toast.LENGTH_LONG).show()
                            binding?.apply {
                                itemDetailTitle.text = data.title
                                itemDetailId.text = data.id.toString()
                                itemDetailUserid.text = data.userId.toString()
                                itemDetailCompleted.text = data.completed.toString()
                            }
                        }
                        else -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_LONG).show()
                    }
                }
                viewModel.getTodoItem(dataId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}