package com.example.academicapp.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academicapp.databinding.AdminHomeFragmentBinding

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
}