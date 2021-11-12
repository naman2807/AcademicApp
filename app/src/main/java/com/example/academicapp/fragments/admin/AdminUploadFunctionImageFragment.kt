package com.example.academicapp.fragments.admin

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminUploadFunctionImageFragmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class AdminUploadFunctionImageFragment : Fragment() {
    private lateinit var binding: AdminUploadFunctionImageFragmentBinding
    private lateinit var imageUri: Uri
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog

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
        savedInstanceState: Bundle?,
    ): View? {
        binding = AdminUploadFunctionImageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.functionImageEditText.setOnClickListener {
            selectImage()
        }

        binding.uploadFunctionImageButton.setOnClickListener {
            uploadFunctionImage()
        }
    }

    private fun uploadFunctionImage() {
        val information: String = binding.functionInformationEditText.text.toString()
        val functionType: String = binding.functionTypeEditText.text.toString()
        if (information.trim().isBlank() || information.trim().isEmpty() || functionType.trim()
                .isEmpty() || functionType.trim().isBlank()
        ) {
            Toast.makeText(requireContext(),
                "Empty Fields. Cannot upload image",
                Toast.LENGTH_SHORT).show()
        } else {
            databaseReference =
                FirebaseDatabase.getInstance().getReference("Functions/$functionType")
            databaseReference.setValue(information).addOnCompleteListener {
                if (it.isSuccessful) {
                    uploadImage()
                } else {
                    Toast.makeText(requireContext(),
                        "Cannot upload information to database",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun uploadImage() {
        val functionType: String = binding.functionTypeEditText.text.toString()
        storageReference = FirebaseStorage.getInstance().getReference("Functions/$functionType")
        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(requireContext(),
                "Image uploaded successfully.",
                Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(requireContext(),
                "Cannot upload image to database",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        getImage.launch("image/*")
    }

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(),
            R.layout.function_text,
            resources.getStringArray(R.array.functions))
        binding.functionTypeEditText.setAdapter(adapter)
    }

    private fun showProgressBar(){

    }

    private fun hideProgressBar(){

    }
}