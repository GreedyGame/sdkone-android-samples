package com.pubscale.sdkone.example.recyclerviewadadapterexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pubscale.sdkone.example.recyclerviewadadapterexample.databinding.ItemRecyclerViewBinding

class RVAdapter: RecyclerView.Adapter<RVAdapter.RVViewHolder>() {

    private var list = ArrayList<String>()

    fun submitList(list: ArrayList<String>) { this.list = list }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val binding = ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int { return list.size }

    class RVViewHolder (private val binding: ItemRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(string: String) { binding.tv.text = string }
    }
}