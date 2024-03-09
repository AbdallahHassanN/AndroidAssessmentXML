package com.example.technical_assignment_xml.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technical_assignment.R
import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.presentation.common.Constants.TAG

class ItemAdapter(private val items: List<StoreItem>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.store_item, parent, false)

        val currentItem = items[position]

        view.findViewById<TextView>(R.id.itemTitle).text = currentItem.title
        view.findViewById<TextView>(R.id.itemPrice).text = "Price: ${currentItem.price}$"

        Glide.with(view)
            .load(currentItem.image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.findViewById(R.id.itemImg))
        return view
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}
