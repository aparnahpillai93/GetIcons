package com.geticons.UI.Home.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geticons.databinding.LayoutIconlisteitemBinding
import com.geticons.databinding.LayoutIconsetitemBinding

class IconListAdapter (val onClick: ()-> Unit) : RecyclerView.Adapter<IconListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutIconlisteitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutIconlisteitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding);
    }

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClick.invoke()
        }

    }
}