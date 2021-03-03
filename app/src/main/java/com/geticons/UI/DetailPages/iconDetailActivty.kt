package com.geticons.UI.DetailPages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.geticons.R
import com.geticons.databinding.ActivityIconDetailActivtyBinding

class iconDetailActivty : AppCompatActivity() {
    lateinit var binding: ActivityIconDetailActivtyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_icon_detail_activty)

        initClickListeners()
    }

    private fun initClickListeners() {

        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.authorname.setOnClickListener {
            val intent=Intent(this,AuthorDetailActivity::class.java)
            startActivity(intent)
        }
    }
}