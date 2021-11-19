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
import com.example.academicapp.databinding.AdminAddFacultyFragmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class AdminAddFacultyFragment: Fragment() {
    private lateinit var binding: AdminAddFacultyFragmentBinding
    private lateinit var firebaseStorage: StorageReference
    private lateinit var firebaseDatabase: DatabaseReference
    private var tenthMarksheetUri: Uri? = null
    private var twelthMarksheetUri: Uri? = null
    private var profileImageUri: Uri? = null

    val getTenthMarksheetUri = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            tenthMarksheetUri = it
            binding.faculty10MarksheetEditText.setText(File(tenthMarksheetUri!!.path).name.toString())
        }
    )

    val getTwelthMarksheetUri = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            twelthMarksheetUri = it
            binding.faculty12MarksheetEditText.setText(File(twelthMarksheetUri!!.path).name.toString())
        }
    )

    val getProfileImageUri = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            profileImageUri = it
            binding.facultyProfileImage.setImageURI(profileImageUri)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminAddFacultyFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.faculty10MarksheetEditText.setOnClickListener {
            selectTenthMarksheetPdf()
        }

        binding.faculty12MarksheetEditText.setOnClickListener {
            selectTwelthMarksheetPdf()
        }

        binding.uploadFacultyButton.setOnClickListener {
            isAnyFieldEmpty()
        }

        binding.facultyProfileImage.setOnClickListener {
            selectProfileImage()
        }
    }

    private fun selectTenthMarksheetPdf(){
        getTenthMarksheetUri.launch("application/pdf")
    }

    private fun selectTwelthMarksheetPdf(){
        getTwelthMarksheetUri.launch("application/pdf")
    }

    private fun selectProfileImage(){
        getProfileImageUri.launch("image/*")
    }

    private fun uploadProfile(){
        val firstName = binding.facultyFirstNameEditText.text.toString()
        val lastName = binding.facultyLastNameEditText.text.toString()
        val email = generateEmailId(firstName = firstName, lastName = lastName)
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Faculties/$email")
        firebaseStorage = FirebaseStorage.getInstance().getReference("Faculties/$email")
    }

    private fun generateEmailId(firstName: String?, lastName: String?): String{
        return if (lastName != null){
            "${firstName!!.lowercase()}.${lastName.lowercase()}@gla.ac.in"
        }else {
            "${firstName!!.lowercase()}@gla.ac.in"
        }
    }

    private fun generatePassword(): String{
        val password = StringBuilder()
        for (i in 0..4){
            password.append((0..9).random().toString())
        }
        return password.toString()
    }

    private fun isAnyFieldEmpty(): Boolean{
        var isEmpty: Boolean = false
        val name = binding.facultyFirstNameEditText.text.toString()
        val address = binding.facultyAddressEditText.text.toString()
        val contact = binding.facultyContactEditText.text.toString()
        val qualification = binding.facultyQualificationsEditText.text.toString()
        val domain = binding.facultyDomainEditText.text.toString()
        val twelthMarksheet = binding.faculty12MarksheetEditText.text.toString()
        val tenthMarksheet = binding.faculty10MarksheetEditText.text.toString()
        if(name.trim().isBlank() || name.trim().isEmpty()){
            binding.facultyFirstNameLayout.isErrorEnabled = true
            binding.facultyFirstNameLayout.error = "Empty Field"
            isEmpty = true
        }

        if(address.trim().isEmpty() || address.trim().isBlank()){
            binding.facultyAddressLayout.isErrorEnabled = true
            binding.facultyAddressLayout.error = "Empty Field"
            isEmpty = true
        }

        if(contact.trim().isBlank() || contact.trim().isEmpty()){
            binding.facultyContactLayout.isErrorEnabled = true
            binding.facultyContactLayout.error = "Empty Field"
            isEmpty = true
        }

        if(qualification.trim().isEmpty() || qualification.trim().isBlank()){
            binding.facultyQualificationsLayout.isErrorEnabled = true
            binding.facultyQualificationsLayout.error = "Empty Field"
            isEmpty = true
        }

        if(domain.trim().isBlank() || domain.trim().isEmpty()){
            binding.facultyDomainLayout.isErrorEnabled = true
            binding.facultyDomainLayout.error = "Empty Field"
            isEmpty = true
        }

        if(twelthMarksheet.trim().isEmpty() || twelthMarksheet.trim().isBlank()){
            binding.faculty12MarksheetLayout.isErrorEnabled = true
            binding.faculty12MarksheetLayout.error = "Empty Field"
            isEmpty = true
        }

        if(tenthMarksheet.trim().isBlank() || tenthMarksheet.trim().isEmpty()){
            binding.faculty10MarksheetLayout.isErrorEnabled = true
            binding.faculty10MarksheetLayout.error = "Empty Field"
            isEmpty = true
        }

        if(profileImageUri == null){
            isEmpty = true
        }
        return isEmpty
    }

    override fun onResume() {
        super.onResume()
        val qualificationAdapter = ArrayAdapter(requireContext(), R.layout.function_text, resources.getStringArray(R.array.qualifications))
        val domainAdapter = ArrayAdapter(requireContext(), R.layout.function_text, resources.getStringArray(R.array.domains))
        binding.facultyQualificationsEditText.setAdapter(qualificationAdapter)
        binding.facultyDomainEditText.setAdapter(domainAdapter)
    }
}