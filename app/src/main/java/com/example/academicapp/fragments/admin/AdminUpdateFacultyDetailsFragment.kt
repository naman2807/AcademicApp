package com.example.academicapp.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminAddFacultyFragmentBinding
import com.example.academicapp.databinding.AdminUpdateFacultyFragmentBinding
import com.example.academicapp.models.Faculty
import com.google.firebase.database.DatabaseReference

class AdminUpdateFacultyDetailsFragment(private val faculty: Faculty): Fragment() {
    private lateinit var binding: AdminAddFacultyFragmentBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminAddFacultyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.uploadFacultyButton.setText(getString(R.string.update_faculty))
        setValues(faculty)
        binding.uploadFacultyButton.setOnClickListener {
            updateFaculty(getFaculty())
        }
    }

    private fun updateFaculty(faculty: Faculty) {
        TODO("Not yet implemented")
    }

    private fun getFaculty(): Faculty {
        TODO("Not yet implemented")
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