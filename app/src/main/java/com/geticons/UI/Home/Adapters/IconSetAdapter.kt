package com.geticons.UI.Home.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geticons.databinding.LayoutIconsetitemBinding

class IconSetAdapter (val type:String,val onClick: ()-> Unit) : RecyclerView.Adapter<IconSetAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutIconsetitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutIconsetitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding);
    }

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClick.invoke()
        }

    }
}