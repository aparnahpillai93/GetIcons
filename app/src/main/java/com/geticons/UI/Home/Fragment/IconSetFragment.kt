package com.geticons.UI.Home.Fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geticons.R
import com.geticons.UI.DetailPages.IconSetDetail
import com.geticons.UI.Home.Adapters.IconSetAdapter
import com.geticons.Util.ConstantHelper
import com.geticons.data.ResponseBean.GetIconSetBean
import com.geticons.data.ResponseBean.IconsetsItem
import com.geticons.data.ViewMOdal.MainViewModel
import com.geticons.databinding.FragmentIconSetBinding
import com.google.gson.Gson


class IconSetFragment : Fragment() {


    lateinit var binding: FragmentIconSetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var viewModel: MainViewModel
    var count=20
    var totalcount=0
    var firstCall=true
    var lastIconsetid:Int?=null
    var loadingFinished=false
    var isLoading=false
    var progressDialog : ProgressDialog?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_icon_set, container, false)
        viewModel= ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage("Loading")
        progressDialog!!.setCancelable(false)
        binding.setlist.adapter=IconSetAdapter(ConstantHelper.Home,iconsets){
            val intent=Intent(requireContext(),IconSetDetail::class.java)
            intent.putExtra(ConstantHelper.ICONSET_ID,it)
            startActivity(intent)
        }
        getIconSets(true)
        binding.setlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView!!.layoutManager?.itemCount
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    Log.d("MyTAG", "Load new list")

                    if (!loadingFinished){
                        if (!isLoading) {
                            binding.progressBar.visibility=View.VISIBLE
                            count=count+20
                            getIconSets(false)
                            isLoading=true




                        }
                    }


                }
            }
        })

        return binding.root
    }
    val lastcount=100
    val iconsets: ArrayList<IconsetsItem?> = arrayListOf()
    var iconsetid=0
    private fun getIconSets(b: Boolean) {
        Log.e("lasticonid",lastIconsetid.toString())
        if (b){
            progressDialog!!.show()
        }
        viewModel.getIconSets(count,lastIconsetid).observe(requireActivity(),{
            Log.e("Response",it.body().toString())
            if (b){
                progressDialog!!.dismiss()
            }
            if (it.code()==200) {
                val resp = Gson().fromJson(it.body(), GetIconSetBean::class.java)
                binding.progressBar.visibility = View.GONE
//                lastIconsetid = resp.iconsets!![resp.iconsets.size - 1]!!.iconsetId
                iconsets.clear()
                iconsets.addAll(resp.iconsets!!.toMutableList())

                Log.e("size",iconsets.size.toString())
                binding.setlist.adapter!!.notifyDataSetChanged()
                if (count==lastcount){
                    loadingFinished=true
                }
                isLoading=false
            }
            else {
                Toast.makeText(requireContext(), "Some Error Occured", Toast.LENGTH_SHORT).show()
            }
        })
    }


}