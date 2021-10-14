package com.example.academicapp.fragments.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academicapp.databinding.StudentHomeFragmentBinding

class StudentHomeFragment: Fragment() {
    private lateinit var binding: StudentHomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StudentHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}