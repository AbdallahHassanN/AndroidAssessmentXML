package com.example.technical_assignment_xml.presentation.detailFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.technical_assignment.R
import com.example.technical_assignment.databinding.FragmentDetailBinding
import com.example.technical_assignment.databinding.FragmentStoreAppListBinding
import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.presentation.common.Constants.TAG

class DetailFragment : Fragment() {

    private lateinit var storeItem: StoreItem
    private lateinit var binding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storeItem = it.getParcelable("item")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.toolbar
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.titleTextView.text = storeItem.title

        Glide.with(this)
            .load(storeItem.image)
            .placeholder(R.drawable.ic_launcher_background)
            .fitCenter()
            .into(binding.imageView)

        binding.priceTextView.text = "Price: " + storeItem.price.toString()
        binding.descTextView.text = storeItem.description
        binding.ratingTextView.text = "Rated: " + storeItem.rating.rate.toString()
        binding.usersTextView.text = "by: " + storeItem.rating.count.toString() + " users"

    }
}