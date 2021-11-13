package com.example.academicapp.fragments.admin

import android.app.Activity.RESULT_OK
import android.app.Application
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminUploadPdfFragmentBinding
import com.example.academicapp.viewmodels.AdminViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class AdminUploadPdfFragment: Fragment() {
    private lateinit var binding: AdminUploadPdfFragmentBinding
    private var pdfUri: Uri? = null
    private lateinit var dialog: Dialog
    private lateinit var storageReference: StorageReference
    private val viewModel: AdminViewModel by activityViewModels()

    /*
    1st method of setting action after selecting pdf
     */
//    val getPdf = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult(),
//        ActivityResultCallback<ActivityResult> {result ->
//            if(result.resultCode == RESULT_OK){
//                pdfUri = result.data!!.data!!
//                binding.subjectPdfEditText.setText(File(pdfUri.path).name.toString())
//            }else{
//                Toast.makeText(requireContext(), "Pdf cannot be selected", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    )

    /*
    2nd method of setting action after selecting pdf
     */
    val getPdf = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            pdfUri = it
            viewModel.setPdfUri(it)
            binding.subjectPdfEditText.setText(File(pdfUri!!.path).name.toString())
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminUploadPdfFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pdfUri = viewModel.pdfUri.value

        binding.subjectPdfEditText.setOnClickListener {
            selectPdf()
        }

        binding.uploadPdfButton.setOnClickListener {
            uploadPdf()
        }
    }

    private fun uploadPdf() {
        if(!validateData()){
            Toast.makeText(requireContext(), "Some fields are empty", Toast.LENGTH_SHORT).show()
        }else {
                showProgressBar()
                val subject = binding.subjectTypeEditText.text.toString()
                val pdf = binding.subjectPdfEditText.text.toString()
                storageReference = FirebaseStorage.getInstance().getReference("pdf/$subject/$pdf")
                uploadToStorage()
        }
    }

    private fun uploadToStorage(){
        storageReference.putFile(pdfUri!!).addOnSuccessListener {
            hideProgressBar()
            Toast.makeText(requireContext(), "Pdf uploaded successfully", Toast.LENGTH_SHORT).show()
            clearData()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Uploading Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateData(): Boolean {
        val subject = binding.subjectTypeEditText.text.toString()
        if (subject.trim().isBlank() || subject.trim().isEmpty()) {
            binding.subjectTypeLayout.isErrorEnabled = true
            binding.subjectTypeLayout.error = "Empty Field"
            if (pdfUri == null) {
                binding.subjectPdfLayout.isErrorEnabled = true
                binding.subjectPdfLayout.error = "Empty Field"
            }
            return false
        }
        if (pdfUri == null) {
            binding.subjectPdfLayout.isErrorEnabled = true
            binding.subjectPdfLayout.error = "Empty Field"
            if (subject.trim().isBlank() || subject.trim().isEmpty()) {
                binding.subjectTypeLayout.isErrorEnabled = true
                binding.subjectTypeLayout.error = "Empty Field"
            }
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.function_text, resources.getStringArray(R.array.subjects))
        binding.subjectTypeEditText.setAdapter(adapter)
    }

    private fun selectPdf(){
        /*
        1st method of selecting pdf and returning back to fragment after selection
         */
//        val intent = Intent()
//        intent.type = "application/pdf"
//        intent.action = Intent.ACTION_GET_CONTENT
//        getPdf.launch(intent)

        /*
        2nd method of selecting pdf and returning back to fragment after selection
         */
        getPdf.launch("application/pdf")
    }

    private fun showProgressBar(){
        dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_bar_pdf)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

    private fun clearData(){
        binding.subjectPdfEditText.setText("")
        binding.subjectTypeEditText.setText("")
        binding.subjectPdfLayout.isErrorEnabled = false
        binding.subjectPdfLayout.error = ""
        binding.subjectTypeLayout.isErrorEnabled = false
        binding.subjectTypeLayout.error = ""
    }

}