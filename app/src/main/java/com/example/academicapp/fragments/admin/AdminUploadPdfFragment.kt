package com.example.academicapp.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academicapp.databinding.AdminUploadPdfFragmentBinding

class AdminUploadPdfFragment: Fragment() {
    private lateinit var binding: AdminUploadPdfFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminUploadPdfFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}