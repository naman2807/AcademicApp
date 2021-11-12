package com.example.academicapp.fragments.admin

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminUploadPdfFragmentBinding

class AdminUploadPdfFragment: Fragment() {
    private lateinit var binding: AdminUploadPdfFragmentBinding
    private lateinit var pdfUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminUploadPdfFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.function_text, resources.getStringArray(R.array.subjects))
        binding.subjectTypeEditText.setAdapter(adapter)
    }
}