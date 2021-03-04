package com.geticons.UI.DetailPages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.geticons.R
import com.geticons.UI.Home.Adapters.IconSetAdapter
import com.geticons.Util.ConstantHelper
import com.geticons.data.ResponseBean.AuthorDetailBean
import com.geticons.data.ResponseBean.AuthorIconSetBean
import com.geticons.data.ViewMOdal.MainViewModel
import com.geticons.databinding.ActivityAuthorDetailBinding
import com.google.gson.Gson

class AuthorDetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityAuthorDetailBinding

    lateinit var viewModel: MainViewModel
    var authorid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_author_detail)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        binding.back.setOnClickListener {
            onBackPressed()
        }
        authorid=intent.getIntExtra(ConstantHelper.AuthorID,0)
//        authorid=100
        getAuthorDetail()
        getAuthorIconSets()
       /* binding.list.adapter=IconSetAdapter(ConstantHelper.AuthorDetail){
            val intent= Intent(this,IconSetDetail::class.java)
            startActivity(intent)
        }*/
    }

    private fun getAuthorIconSets() {
        viewModel.getAuthorIconSets(authorid).observe(this,{
            if (it.code()==200){
                Log.d("REsponse", it.body().toString())
                val resp=Gson().fromJson(it.body(), AuthorIconSetBean::class.java)
                binding.list.adapter= IconSetAdapter(ConstantHelper.AuthorDetail,resp.iconsets){
                    val intent= Intent(this,IconSetDetail::class.java)
                    intent.putExtra(ConstantHelper.ICONSET_ID,it)
                    startActivity(intent)
                }
            }
        })
    }

    private fun getAuthorDetail() {
        Log.e("Author_ID",authorid.toString())
        viewModel.getAuthorDetail(authorid).observe(this, {
            if (it.code() == 200) {
                Log.d("REsponse", it.body().toString())
                val resp=Gson().fromJson(it.body(), AuthorDetailBean::class.java)
                binding.authorname.text=resp.name
            } else if (it.code() == 404) {
                Log.d("REsponse", it.body().toString())
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show()
            }
        })
    }
}