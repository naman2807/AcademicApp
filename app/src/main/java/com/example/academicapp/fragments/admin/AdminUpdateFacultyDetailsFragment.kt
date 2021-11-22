package com.example.academicapp.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academicapp.databinding.AdminAddFacultyFragmentBinding
import com.example.academicapp.databinding.AdminUpdateFacultyFragmentBinding
import com.example.academicapp.models.Faculty

class AdminUpdateFacultyDetailsFragment(private val faculty: Faculty): Fragment() {
    private lateinit var binding: AdminAddFacultyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminAddFacultyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setValues(faculty)
    }

    private fun setValues(faculty: Faculty) {
        binding.apply {
            facultyFirstNameEditText.setText(faculty.firstName)
            facultyLastNameEditText.setText(faculty.lastname)
            facultyAddressEditText.setText(faculty.address)
            facultyContactEditText.setText(faculty.contact)
            facultyQualificationsEditText.setText(faculty.qualification)
            facultyDomainEditText.setText(faculty.domain)
        }
    }
}