package com.geticons.UI.Home.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geticons.Util.ConstantHelper
import com.geticons.data.ResponseBean.IconsItem
import com.geticons.databinding.LayoutIconlisteitemBinding

class IconListAdapter(var data: List<IconsItem?>?, val onClick: (id:Int,isPremium:Boolean) -> Unit) : RecyclerView.Adapter<IconListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutIconlisteitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutIconlisteitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding);
    }

    override fun getItemCount() = data!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClick.invoke(data!![position]!!.iconId!!,data!![position]!!.isPremium!!)
        }
        val ctx=holder.itemView.context
        try {
            val pos=data!![position]!!.rasterSizes!!.size
            Glide.with(ctx)
                .load(data!![position]!!.rasterSizes!![pos-1]!!.formats!![0]!!.preview_url)
                .into(holder.binding.imIcon)

            if (data!![position]!!.isPremium!!){
                holder.binding.imageView2.visibility=View.VISIBLE
            }
            else{
                holder.binding.imageView2.visibility=View.GONE
            }

            if (data!![position]!!.prices!=null){
                holder.binding.price.text="$"+ data!![position]?.prices!![0]!!.price
            }
            else{
                holder.binding.price.text=ConstantHelper.NotApplicable
            }

            if (data!![position]?.type!=null){
                holder.binding.type.text=  data!![position]?.type
            }
            else{
                holder.binding.type.text= ConstantHelper.NotApplicable
            }

            holder.binding.authorname.text=ConstantHelper.NotApplicable
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}