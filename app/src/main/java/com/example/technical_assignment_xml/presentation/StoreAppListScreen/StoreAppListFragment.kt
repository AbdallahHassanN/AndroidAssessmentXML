package com.example.technical_assignment_xml.presentation.StoreAppListScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technical_assignment.R
import com.example.technical_assignment_xml.Adapter.ItemAdapter
import com.example.technical_assignment.databinding.FragmentStoreAppListBinding
import com.example.technical_assignment_xml.presentation.viewmodels.StoreAppListViewModel
import com.example.technical_assignment_xml.network.response.Resource
import com.example.technical_assignment_xml.presentation.common.Constants.TAG
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreAppListFragment : Fragment() {
    private lateinit var binding: FragmentStoreAppListBinding
    lateinit var viewModel: StoreAppListViewModel
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[StoreAppListViewModel::class.java]
        binding = FragmentStoreAppListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        viewModel.allItems.observe(viewLifecycleOwner) { response ->
            showProgressBar()
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        itemAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Snackbar.make(
                            requireView(),
                            message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    hideProgressBar()
                }

                is Resource.Loading -> {
                    Log.d(TAG,"Loading frag")
                    showProgressBar()
                }
            }
        }
        itemAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("item", it)
            }
            findNavController().navigate(
                R.id.action_storeAppListFragment_to_detailFragment,
                bundle
            )
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        recyclerView = binding.rvItems
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return 1
                }
            }
        }
        itemAdapter = ItemAdapter()
        binding.rvItems.apply {
            adapter = itemAdapter
        }
    }
}