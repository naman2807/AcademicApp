package com.example.academicapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.academicapp.R
import com.example.academicapp.databinding.FacultyLoginBinding

class FacultyLoginFragment: Fragment(){
    private lateinit var binding: FacultyLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FacultyLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_facultyLoginFragment_to_facultyHomeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.finish()
    }
}