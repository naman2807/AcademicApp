package com.example.academicapp.fragments.admin

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminUploadFunctionImageFragmentBinding
import com.example.academicapp.viewmodels.AdminViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AdminUploadFunctionImageFragment : Fragment() {
    private lateinit var binding: AdminUploadFunctionImageFragmentBinding
    private var imageUri: Uri? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog
    private val viewModel: AdminViewModel by activityViewModels()

    val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            imageUri = it
            viewModel.setFunctionImageUri(it)
            binding.functionImageEditText.setText(File(imageUri!!.path).name.toString())
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
        imageUri = viewModel.functionImageUri.value

        binding.functionImageEditText.setOnClickListener {
            selectImage()
        }

        binding.uploadFunctionImageButton.setOnClickListener {
            uploadFunctionImage()
        }
    }

    private fun uploadFunctionImage() {
        showProgressBar()
        val information: String = binding.functionInformationEditText.text.toString()
        val functionType: String = binding.functionTypeEditText.text.toString()
        if (validateData()) {
            hideProgressBar()
            Toast.makeText(requireContext(),
                "Empty Fields. Cannot upload image",
                Toast.LENGTH_SHORT).show()
        } else {
            databaseReference =
                FirebaseDatabase.getInstance().getReference("Functions/$functionType/${getCurrentDate()}")
            databaseReference.setValue(information).addOnCompleteListener {
                if (it.isSuccessful) {
                    uploadImage()
                } else {
                    hideProgressBar()
                    Toast.makeText(requireContext(),
                        "Cannot upload information to database",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateData(): Boolean{
        var isError = false
        val information: String = binding.functionInformationEditText.text.toString()
        val functionType: String = binding.functionTypeEditText.text.toString()
        if (information.trim().isBlank() || information.trim().isEmpty()){
            binding.functionInformationLayout.isErrorEnabled = true
            binding.functionInformationLayout.error = "Empty Field"
            isError = true
        }
        if( functionType.trim()
                .isEmpty() || functionType.trim().isBlank()){
            binding.functionTypeLayout.isErrorEnabled = true
            binding.functionTypeLayout.error = "Empty Field"
            isError = true
        }

        if(imageUri == null){
            binding.functionImageLayout.isErrorEnabled = true
            binding.functionImageLayout.error = "Empty Field"
            isError = true
        }
        return isError
    }

    private fun uploadImage() {
        val functionType: String = binding.functionTypeEditText.text.toString()
        storageReference = FirebaseStorage.getInstance().getReference("Functions/$functionType/${getCurrentDate()}")
        storageReference.putFile(imageUri!!).addOnSuccessListener {
            hideProgressBar()
            clearData()
            Toast.makeText(requireContext(),
                "Image uploaded successfully.",
                Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            hideProgressBar()
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
        dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_bar)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

    private fun getCurrentDate():String {
        val sdf = SimpleDateFormat("yyyy/MM/dd/hh:mm:ss")
        val date = Calendar.getInstance().time
        return sdf.format(date)
    }

    private fun clearData(){
        binding.functionImageEditText.setText("")
        binding.functionTypeEditText.setText("")
        binding.functionInformationEditText.setText("")
        binding.functionInformationLayout.isErrorEnabled = false
        binding.functionInformationLayout.error = ""
        binding.functionTypeLayout.isErrorEnabled = false
        binding.functionTypeLayout.error = ""
        binding.functionImageLayout.isErrorEnabled = false
        binding.functionImageLayout.error = ""
    }
}