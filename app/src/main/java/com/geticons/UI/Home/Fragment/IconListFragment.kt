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
import com.geticons.UI.DetailPages.iconDetailActivty
import com.geticons.UI.Home.Adapters.IconListAdapter
import com.geticons.databinding.FragmentIconListBinding


class IconListFragment : Fragment() {

    lateinit var binding: FragmentIconListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_icon_list, container, false)
        binding.iconlist.adapter=IconListAdapter(){

            val intent=Intent(requireContext(),iconDetailActivty::class.java)
            startActivity(intent)
        }
        return binding.root
    }


}