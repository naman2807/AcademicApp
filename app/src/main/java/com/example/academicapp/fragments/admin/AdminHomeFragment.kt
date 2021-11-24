package com.example.academicapp.fragments.admin

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.material.Snackbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminHomeFragmentBinding
import com.example.academicapp.recyclerview.admin.AdminImportantLinksAdapter
import com.example.academicapp.recyclerview.admin.MySliderImageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.smarteist.autoimageslider.SliderView

class AdminHomeFragment: Fragment() {
    private lateinit var binding: AdminHomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       binding.imageSlider.adapter = MySliderImageAdapter(requireContext())

        val images: ArrayList<Int> = arrayListOf(
            R.drawable.screenshot1,
            R.drawable.screenshot2
        )
        val texts: ArrayList<String> = arrayListOf(
            "Exam Portal",
            "GLAMS Portal"
        )
        binding.importantLinksRecyclerView.adapter = AdminImportantLinksAdapter(requireContext(),images, texts)
        binding.importantLinksRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.importantLinksRecyclerView.setHasFixedSize(true)

        binding.imageSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }
}