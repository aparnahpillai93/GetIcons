package com.geticons.UI.DetailPages

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.geticons.R
import com.geticons.UI.Home.Adapters.IconListAdapter
import com.geticons.Util.ConstantHelper
import com.geticons.data.ResponseBean.IconListOFIconSetBean
import com.geticons.data.ResponseBean.IconSetDetailBean
import com.geticons.data.ResponseBean.IconsItem
import com.geticons.data.ViewMOdal.MainViewModel
import com.geticons.databinding.ActivityIconSetDetailBinding
import com.google.gson.Gson

class IconSetDetail : AppCompatActivity() {

    lateinit var binding:ActivityIconSetDetailBinding
    var iconsList: ArrayList<IconsItem?>? = arrayListOf()
    var iconsetid=0
    lateinit var viewModel: MainViewModel
    var authorId=0
    var progressDialog : ProgressDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_icon_set_detail)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading")
        progressDialog!!.setCancelable(false)

        if (intent.hasExtra(ConstantHelper.ICONSET_ID)){
            iconsetid=intent.getIntExtra(ConstantHelper.ICONSET_ID,0)
            Log.d("iconsetid",iconsetid.toString())
            getIconSetDetails(iconsetid)
            getIconSetIcons(iconsetid)
        }


        binding.iconlist.adapter=IconListAdapter(iconsList){ i: Int, b: Boolean ->
            val intent = Intent(this, iconDetailActivty::class.java)
            intent.putExtra(ConstantHelper.ICON_ID, i)
            intent.putExtra(ConstantHelper.IsPremium, b)
            startActivity(intent)
        }

        initClickListeners()
    }

    private fun getIconSetIcons(iconsetid: Int) {
        progressDialog!!.show()
        viewModel.getIcons_IconSet(iconsetid).observe(this,{
            Log.d("Response",it.body().toString())
            progressDialog!!.dismiss()
            if (it.code()==200){

                val resp=Gson().fromJson(it.body(),IconListOFIconSetBean::class.java)
                iconsList!!.addAll(resp.icons!!.toMutableList())
                binding.iconlist.adapter!!.notifyDataSetChanged()
            }
        })
    }

    var url=""

    private fun getIconSetDetails(iconsetid: Int) {
        progressDialog!!.show()
        viewModel.getIconSetDetail(iconsetid).observe(this,{
            Log.d("Response",it.body().toString())
            progressDialog!!.dismiss()
            if (it.code()==200){
                try {
                    val resp=Gson().fromJson(it.body(),IconSetDetailBean::class.java)
                    if (resp.isPremium!!){
                       binding.badgeicon.visibility= View.VISIBLE
                        binding.badge.text="Premium"
                    }
                    else{
                        binding.badgeicon.visibility= View.GONE
                        binding.badge.text="Free"
                    }
                    binding.textView2.text=if (resp.name!=null) resp.name else ConstantHelper.NotApplicable
                    binding.textView3.text=if (resp.type!=null) resp.type else ConstantHelper.NotApplicable
                    binding.price.text=if (resp.prices!=null) "$ "+resp.prices[0]!!.price else ConstantHelper.NotApplicable
                    binding.authorname.text=if (resp.author!=null) resp.author.name else ConstantHelper.NotApplicable
                    authorId= resp.author?.userId!!
                    if (resp.prices!=null){
                        if (resp.prices[0]!!.license!=null){
                            binding.licensename.text= resp.prices[0]!!.license!!.name

                        }
                        else{
                            binding.licensename.text=ConstantHelper.NotApplicable
                        }
                    }else{
                        binding.licensename.text=ConstantHelper.NotApplicable
                    }

                    binding.readme.text=if (resp.readme!=null) resp.readme.toSpanned() else ConstantHelper.NotApplicable
                    url= resp.websiteurl!!
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        })
    }

    private fun initClickListeners() {
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.authorname.setOnClickListener {
            val intent= Intent(this,AuthorDetailActivity::class.java)
            intent.putExtra(ConstantHelper.AuthorID,authorId)
            startActivity(intent)
        }

        binding.website.setOnClickListener {
            val intent=Intent(this,WebViewActivity::class.java)
            intent.putExtra(ConstantHelper.URL,url)
            startActivity(intent)
        }
    }

    fun String.toSpanned(): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this)
        }
    }
}