package com.example.academicapp.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academicapp.databinding.AdminUpdateFacultyFragmentBinding
import com.example.academicapp.models.Faculty

class AdminUpdateFacultyDetailsFragment(private val faculty: Faculty): Fragment() {
    private lateinit var binding: AdminUpdateFacultyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminUpdateFacultyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setValues(faculty)
    }

    private fun setValues(faculty: Faculty) {

    }
}