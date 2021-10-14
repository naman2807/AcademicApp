package com.example.academicapp.fragments.faculty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.academicapp.R
import com.example.academicapp.databinding.FacultyHomeFragmentBinding

class FacultyHomeFragment: Fragment() {
    private lateinit var binding: FacultyHomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FacultyHomeFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.finish()
    }
}