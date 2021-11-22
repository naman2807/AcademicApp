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
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminAddFacultyFragmentBinding
import com.example.academicapp.databinding.AdminUpdateFacultyFragmentBinding
import com.example.academicapp.models.Faculty
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class AdminUpdateFacultyDetailsFragment(private val faculty: Faculty): Fragment() {
    private lateinit var binding: AdminAddFacultyFragmentBinding
    private lateinit var databaseReference: DatabaseReference
    private var twelthMarksheet: String? = ""
    private var tenthMarksheet: String? = ""
    private var imageUri: String? = ""
    private var tenthMarksheetUri: Uri? = null
    private var twelthMarksheetUri: Uri? = null
    private var profileImageUri: Uri? = null
    private lateinit var firebaseStorageFor12: StorageReference
    private lateinit var firebaseStorageFor10: StorageReference
    private lateinit var firebaseStorageForProfile: StorageReference
    private lateinit var dialog: Dialog
    private var isImageChanged = false

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
        binding = AdminAddFacultyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.uploadFacultyButton.setText(getString(R.string.update_faculty))
        setValues(faculty)
        binding.faculty10MarksheetEditText.setOnClickListener {
            selectTenthMarksheetPdf()
        }

        binding.faculty12MarksheetEditText.setOnClickListener {
            selectTwelthMarksheetPdf()
        }


        binding.facultyProfileImage.setOnClickListener {
            isImageChanged = true
            selectProfileImage()
        }

        binding.uploadFacultyButton.setOnClickListener {
            updateFaculty()
        }
    }

    private fun selectTenthMarksheetPdf() {
        getTenthMarksheetUri.launch("application/pdf")
    }

    private fun selectTwelthMarksheetPdf() {
        getTwelthMarksheetUri.launch("application/pdf")
    }

    private fun selectProfileImage() {
        getProfileImageUri.launch("image/*")
    }

    private fun updateFaculty() {
        showProgressBar()
        val contact = binding.facultyContactEditText.text.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Faculties/$contact")
        databaseReference.setValue(getFaculty()).addOnSuccessListener {
            hideProgressBar()
            Toast.makeText(requireContext(), "Updated Success", Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, AdminHomeFragment())?.commit()
        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(requireContext(), "Updated Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFaculty(): Faculty {
        val firstName = binding.facultyFirstNameEditText.text.toString()
        val lastName = binding.facultyLastNameEditText.text.toString()
        val address = binding.facultyAddressEditText.text.toString()
        val contact = binding.facultyContactEditText.text.toString()
        val qualification = binding.facultyQualificationsEditText.text.toString()
        val domain = binding.facultyDomainEditText.text.toString()
        val twelth = binding.faculty12MarksheetEditText.text.toString()
        val tenth = binding.faculty10MarksheetEditText.text.toString()

        if (!twelth.equals("")){
            uploadTwelthMarksheet()
        }

        if (!tenth.equals("")){
            uploadTenthMarksheet()
        }

        return Faculty(firstName = firstName, lastname = lastName, address = address, contact = contact, qualification, domain = domain,
        faculty.password, generateEmailId(firstName = firstName, lastName = lastName), imageUri, twelthMarksheet, tenthMarksheet)

    }

    private fun uploadTwelthMarksheet() {
        val contact = binding.facultyContactEditText.text.toString()
        firebaseStorageFor12 = FirebaseStorage.getInstance()
            .getReference("Faculties/$contact/twelth_marksheet")
        firebaseStorageFor12.putFile(twelthMarksheetUri!!).addOnSuccessListener {
            val result = it.storage.downloadUrl
            result.addOnSuccessListener {
                twelthMarksheet = it.toString()
                uploadTwelthMarksheet()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Twelth marksheet uploading error", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun uploadTenthMarksheet() {
        val contact = binding.facultyContactEditText.text.toString()
        firebaseStorageFor10 = FirebaseStorage.getInstance()
            .getReference("Faculties/$contact/tenth_marksheet")
        firebaseStorageFor10.putFile(tenthMarksheetUri!!).addOnSuccessListener {
            val result = it.storage.downloadUrl
            result.addOnSuccessListener {
                tenthMarksheet = it.toString()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Tenth marksheet uploading error", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun uploadProfileImage() {
        val contact = binding.facultyContactEditText.text.toString()
        firebaseStorageForProfile =
            FirebaseStorage.getInstance().getReference("Faculties/$contact/profile_pic")
        firebaseStorageForProfile.putFile(profileImageUri!!).addOnSuccessListener {
            val result = it.storage.downloadUrl
            result.addOnSuccessListener {
                imageUri = it.toString()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Twelth marksheet uploading error", Toast.LENGTH_SHORT)
                .show()
        }
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

        Glide.with(binding.facultyProfileImage.context)
            .load(faculty.downloadImageUri).placeholder(R.drawable.profile)
            .circleCrop()
            .error(R.drawable.profile)
            .into(binding.facultyProfileImage)

        imageUri = faculty.downloadImageUri
        twelthMarksheet = faculty.download12MarksheetUri
        tenthMarksheet = faculty.download10MarksheetUri
    }

    override fun onResume() {
        super.onResume()
        val qualificationAdapter = ArrayAdapter(requireContext(),
            R.layout.function_text,
            resources.getStringArray(R.array.qualifications))
        val domainAdapter = ArrayAdapter(requireContext(),
            R.layout.function_text,
            resources.getStringArray(R.array.domains))
        binding.facultyQualificationsEditText.setAdapter(qualificationAdapter)
        binding.facultyDomainEditText.setAdapter(domainAdapter)
    }

    private fun generateEmailId(firstName: String?, lastName: String?): String {
        return if (lastName != null) {
            "${firstName!!.lowercase()}.${lastName.lowercase()}@gla.ac.in"
        } else {
            "${firstName!!.lowercase()}@gla.ac.in"
        }
    }

    private fun showProgressBar() {
        dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_bar)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}