package com.example.technical_assignment_xml.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technical_assignment.R
import com.example.technical_assignment_xml.domain.models.StoreItem


class ItemAdapter:RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<StoreItem>(){
        override fun areItemsTheSame(oldItem: StoreItem, newItem: StoreItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StoreItem, newItem: StoreItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.store_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.itemView.apply {
            holder.itemView.findViewById<TextView>(R.id.itemTitle).text = currentItem.title
            holder.itemView.findViewById<TextView>(R.id.itemPrice).text =
                "Price: " + currentItem.price.toString() + "$"

            Glide.with(this)
                .load(currentItem.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.itemView.findViewById(R.id.itemImg))
            setOnClickListener {
                onItemClickListener?.let { it(currentItem) }
            }
        }
    }

    private var onItemClickListener: ((StoreItem) -> Unit)? = null
    fun setOnItemClickListener(listener: (StoreItem) -> Unit) {
        onItemClickListener = listener
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}
}