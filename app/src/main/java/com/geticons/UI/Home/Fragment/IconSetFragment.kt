package com.geticons.UI.Home.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.geticons.R
import com.geticons.UI.DetailPages.IconSetDetail
import com.geticons.UI.Home.Adapters.IconSetAdapter
import com.geticons.Util.ConstantHelper
import com.geticons.databinding.FragmentIconSetBinding


class IconSetFragment : Fragment() {


    lateinit var binding: FragmentIconSetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_icon_set, container, false)
        binding.setlist.adapter=IconSetAdapter(ConstantHelper.Home){
            val intent=Intent(requireContext(),IconSetDetail::class.java)
            startActivity(intent)
        }
        return binding.root
    }


}