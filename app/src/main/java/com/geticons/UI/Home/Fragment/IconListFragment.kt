package com.geticons.UI.Home.Fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geticons.R
import com.geticons.UI.DetailPages.iconDetailActivty
import com.geticons.UI.Home.Adapters.IconListAdapter
import com.geticons.Util.ConstantHelper
import com.geticons.Util.ProgessShow
import com.geticons.data.ResponseBean.IconsItem
import com.geticons.data.ResponseBean.SearchBean
import com.geticons.data.ViewMOdal.MainViewModel
import com.geticons.databinding.FragmentIconListBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_icon_list.*


class IconListFragment : Fragment() {

    lateinit var binding: FragmentIconListBinding

    lateinit var viewModel: MainViewModel

    var iconsList: ArrayList<IconsItem?>? = arrayListOf()
    var progressDialog : ProgressDialog?=null
    var count=20
    var totalcount=0
    val lastcount=100
    var firstCall=true
    var lastIconsetid:Int?=null
    var loadingFinished=false
    var isLoading=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_icon_list, container, false)
        viewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage("Loading")
        progressDialog!!.setCancelable(false)
        binding.iconlist.adapter=IconListAdapter(iconsList) { i: Int, b: Boolean ->
            val intent = Intent(requireContext(), iconDetailActivty::class.java)
            intent.putExtra(ConstantHelper.ICON_ID, i)
            intent.putExtra(ConstantHelper.IsPremium, b)
            startActivity(intent)

        }
        binding.editSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                hideSoftKeyBoard(requireContext(),binding.editSearch)
                iconsList!!.clear()

                count=20
                isLoading=false
                loadingFinished=false
                search(binding.editSearch.text.toString().trim(),count,true) // you can do anything
                return@setOnEditorActionListener true
            }
            false
        }
        binding.iconlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                            search(binding.editSearch.text.toString().trim(),count,false)

                            isLoading=true



                        }
                    }



                }
            }
        })
        return binding.root
    }

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }

    private fun search(s: String, i: Int,load:Boolean) {

        if (load){
            progressDialog!!.show()
        }

        viewModel.searchIcon(requireContext(), s, i).observe(requireActivity(), {
            Log.d("Resp", it.body().toString())

            if (load){
                progressDialog!!.dismiss()
            }

            binding.progressBar.visibility = View.GONE
            if (it.code() == 200) {
                val resp = Gson().fromJson(it.body(), SearchBean::class.java)

                totalcount= resp.totalCount!!
                iconsList!!.clear()
                iconsList!!.addAll(resp.icons!!.toMutableList())
                binding.iconlist.adapter!!.notifyDataSetChanged()
                if (count == lastcount) {
                    loadingFinished = true
                }
                isLoading=false

            } else {
                Toast.makeText(requireContext(), "Some Error Occured", Toast.LENGTH_SHORT).show()
            }
        })
    }


}