package com.geticons.UI.DetailPages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.geticons.R
import com.geticons.UI.Home.Adapters.IconListAdapter
import com.geticons.databinding.ActivityIconSetDetailBinding
import com.geticons.databinding.ActivityMainBinding

class IconSetDetail : AppCompatActivity() {
    lateinit var binding:ActivityIconSetDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_icon_set_detail)

        binding.iconlist.adapter=IconListAdapter(){
            val intent=Intent(this,iconDetailActivty::class.java)
            startActivity(intent)
        }

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.authorname.setOnClickListener {
            val intent= Intent(this,AuthorDetailActivity::class.java)
            startActivity(intent)
        }
    }
}