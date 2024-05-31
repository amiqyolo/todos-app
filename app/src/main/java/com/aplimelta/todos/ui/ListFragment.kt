package com.aplimelta.todos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplimelta.todos.R
import com.aplimelta.todos.data.response.ApiResponse
import com.aplimelta.todos.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding
    private val viewModel: TodosViewModel by viewModels()
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            adapter = TodoAdapter()

            observeViewModel()
            viewModel.getTodos()
            showRecyclerView()
        }
    }

    private fun showRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding?.rvList?.layoutManager = layoutManager
        binding?.rvList?.adapter = adapter

        adapter.setOnItemClickCallback(object : TodoAdapter.OnItemClickCallback {
            override fun onItemClicked(id: String) {
                val mBundle = Bundle()
                mBundle.putString("EXTRA_ID", id)
                view?.findNavController()
                    ?.navigate(R.id.action_listFragment_to_detailFragment, mBundle)
            }
        })
    }

    private fun observeViewModel() {
        viewModel.todos.observe(viewLifecycleOwner) { results ->
            when (results) {
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), results.message, Toast.LENGTH_LONG).show()
                }

                is ApiResponse.Success -> {
                    val data = results.data
                    if (data.isNotEmpty()) {
                        adapter.submitList(data)
                    } else {
                        Toast.makeText(requireContext(), "Data tidak ada", Toast.LENGTH_LONG).show()
                    }
                }

                is ApiResponse.Loading -> Toast.makeText(
                    requireContext(),
                    "Loading....",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        binding?.rvList?.adapter = null
    }

}