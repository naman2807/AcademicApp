package com.example.academicapp.fragments.admin

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminUploadPdfFragmentBinding
import java.io.File

class AdminUploadPdfFragment: Fragment() {
    private lateinit var binding: AdminUploadPdfFragmentBinding
    private lateinit var pdfUri: Uri

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
            binding.subjectPdfEditText.setText(File(pdfUri.path).name.toString())
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
        binding.subjectPdfEditText.setOnClickListener {
            selectPdf()
        }
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
}