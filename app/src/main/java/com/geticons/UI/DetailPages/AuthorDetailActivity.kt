package com.geticons.UI.DetailPages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.geticons.R
import com.geticons.UI.Home.Adapters.IconListAdapter
import com.geticons.UI.Home.Adapters.IconSetAdapter
import com.geticons.Util.ConstantHelper
import com.geticons.databinding.ActivityAuthorDetailBinding

class AuthorDetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityAuthorDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_author_detail)
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.list.adapter=IconSetAdapter(ConstantHelper.AuthorDetail){
            val intent= Intent(this,IconSetDetail::class.java)
            startActivity(intent)
        }
    }
}