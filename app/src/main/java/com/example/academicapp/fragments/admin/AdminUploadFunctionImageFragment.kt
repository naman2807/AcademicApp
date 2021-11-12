package com.example.academicapp.fragments.admin

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminUploadFunctionImageFragmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.File

class AdminUploadFunctionImageFragment: Fragment() {
    private lateinit var binding: AdminUploadFunctionImageFragmentBinding
    private lateinit var imageUri: Uri
    private lateinit var databaseReference: DatabaseReference

    val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            imageUri = it
            binding.functionImageEditText.setText(File(imageUri.path).name.toString())
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminUploadFunctionImageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.functionImageEditText.setOnClickListener {
            selectImage()
        }

        binding.uploadFunctionImageButton.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        val information: String = binding.functionInformationEditText.text.toString()
        val functionType: String = binding.functionTypeEditText.text.toString()
    }

    private fun selectImage() {
        getImage.launch("image/*")
    }

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.function_text, resources.getStringArray(R.array.functions))
        binding.functionTypeEditText.setAdapter(adapter)
    }
}