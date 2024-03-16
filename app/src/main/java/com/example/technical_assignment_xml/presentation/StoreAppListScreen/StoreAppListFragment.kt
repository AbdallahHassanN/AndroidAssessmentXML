package com.example.technical_assignment_xml.presentation.StoreAppListScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.technical_assignment.R
import com.example.technical_assignment_xml.Adapter.ItemAdapter
import com.example.technical_assignment.databinding.FragmentStoreAppListBinding
import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.presentation.viewmodels.StoreAppListViewModel
import com.example.technical_assignment_xml.network.response.Resource
import com.example.technical_assignment_xml.presentation.common.Constants.TAG
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreAppListFragment : Fragment() {
    private lateinit var binding: FragmentStoreAppListBinding
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var listView: ListView
    private var savedPosition: Int = 0
    private val viewModel by viewModels<StoreAppListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreAppListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("savedPosition", listView.firstVisiblePosition)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListView()
        savedInstanceState?.let {
            savedPosition = it.getInt("savedPosition", 0)
        }

        viewModel.allItems.observe(viewLifecycleOwner) { response ->
            showProgressBar()
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        itemAdapter = ItemAdapter(it)
                        listView.adapter = itemAdapter
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
                    Log.d(TAG, "Loading frag")
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpListView() {
        listView = binding.listView
        itemAdapter = ItemAdapter(
            emptyList()
        )
        listView.adapter = itemAdapter
        listView.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position) as StoreItem
            val bundle = Bundle().apply {
                putParcelable("item", item)
            }
            findNavController().navigate(
                R.id.action_storeAppListFragment_to_detailFragment,
                bundle
            )
        }
    }

    override fun onPause() {
        super.onPause()
        savedPosition = listView.firstVisiblePosition
    }
    override fun onResume() {
        super.onResume()
        listView.setSelection(savedPosition)
    }
}