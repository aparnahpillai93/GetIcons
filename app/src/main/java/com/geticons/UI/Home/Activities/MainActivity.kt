package com.geticons.UI.Home.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.geticons.R
import com.geticons.UI.Home.Fragment.IconListFragment
import com.geticons.UI.Home.Fragment.IconSetFragment
import com.geticons.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        setViewPager(binding.viewpager)
    }

    lateinit var adapter: viewPagerAdapter
    private fun setViewPager(viewpager: ViewPager) {

        adapter= viewPagerAdapter(supportFragmentManager)
        val count=2

        for (i in 0 until count){

            if (i==0){
                val bundle=Bundle()
                val fragment_= IconSetFragment();
                fragment_.arguments=bundle

                adapter.addFrag(fragment_, "Icon Set")
            }
            else{
                val bundle=Bundle()
                val fragment_= IconListFragment();
                fragment_.arguments=bundle
                adapter.addFrag(fragment_, "Icons")
            }


        }
        viewpager.adapter=adapter

        binding.tabs.setupWithViewPager(viewpager)
        binding.tabs.getTabAt(0)?.select()
    }

    class viewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

        private final val mFragmentList=ArrayList<Fragment>()
        private final val mFragmentTitleList=ArrayList<String>()


        fun viewPagerAdapter(manager: FragmentManager){


        }

        // this counts total number of tabs
        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getItemPosition(object_: Any): Int {
            return POSITION_NONE
        }
        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }
        override fun getPageTitle(position: Int):CharSequence{
            return mFragmentTitleList.get(position)
        }
        fun addFrag(fragment: Fragment, title: String){

            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)

        }
    }

}