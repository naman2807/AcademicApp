package com.example.academicapp.fragments.admin

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.academicapp.databinding.AdminUploadNoticeFragmentBinding
import com.google.firebase.storage.FirebaseStorage

class AdminUploadNoticeFragment: Fragment() {
    private lateinit var binding: AdminUploadNoticeFragmentBinding
    private lateinit var imageUri: Uri

    val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            imageUri = it
            binding.notice.setImageURI(imageUri)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminUploadNoticeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.notice.setOnClickListener {
            selectImage()
        }

        binding.uploadNoticeButton.setOnClickListener {
            uploadNotice()
        }
    }

    private fun uploadNotice() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Uploading Notice...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val fileName: String = binding.noticeDescriptionEditText.text.toString()
        val storageReference = FirebaseStorage.getInstance().getReference("notices/$fileName")

        storageReference.putFile(imageUri)
            .addOnSuccessListener {
                binding.notice.setImageURI(null)
                Toast.makeText(requireContext(), "$fileName notice Uploaded Successfully", Toast.LENGTH_SHORT).show()
                if(progressDialog.isShowing) progressDialog.dismiss()
            }.addOnFailureListener{
                if(progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(requireContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show()
            }
    }

    private fun selectImage() {
        getImage.launch("image/*")
    }

    private fun clearData(){

    }

}