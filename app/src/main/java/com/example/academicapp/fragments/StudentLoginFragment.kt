package com.example.academicapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academicapp.databinding.StudentLoginBinding

class StudentLoginFragment: Fragment() {
    private lateinit var binding: StudentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StudentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
}