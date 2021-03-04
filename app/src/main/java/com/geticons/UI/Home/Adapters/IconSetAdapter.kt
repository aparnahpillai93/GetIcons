package com.geticons.UI.Home.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geticons.Util.ConstantHelper
import com.geticons.data.ResponseBean.IconsetsItem
import com.geticons.databinding.LayoutIconsetitemBinding

class IconSetAdapter(val type:String, var datalist: List<IconsetsItem?>?, val onClick: (id:Int)-> Unit) : RecyclerView.Adapter<IconSetAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutIconsetitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutIconsetitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding);
    }

    override fun getItemCount() = datalist!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClick.invoke(datalist!![position]!!.iconsetId!!.toInt())
        }
        val iconObj= datalist!![position]

        if (iconObj!!.isPremium!!){
            holder.binding.badge.visibility= View.VISIBLE
        }
        else{
            holder.binding.badge.visibility= View.GONE
        }

        holder.binding.iconsetname.text=iconObj.name
        holder.binding.type.text=iconObj.type
        if (iconObj.prices!=null){
            holder.binding.price.text= "$"+iconObj.prices[0]!!.price.toString()
            if (iconObj.prices[0]!!.license!!!=null){
                holder.binding.licenseName.text= iconObj.prices[0]!!.license!!.name
            }else{
                holder.binding.licenseName.text= ConstantHelper.NotApplicable
            }


        }
        else{
            holder.binding.price.text=ConstantHelper.NotApplicable
            holder.binding.licenseName.text= ConstantHelper.NotApplicable
        }
        if (iconObj.author!=null){
            holder.binding.authorname.text= iconObj.author.name

        }
        else{
            holder.binding.authorname.text=ConstantHelper.NotApplicable
        }

    }
}