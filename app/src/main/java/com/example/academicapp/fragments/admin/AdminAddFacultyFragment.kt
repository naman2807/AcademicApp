package com.example.academicapp.fragments.admin

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminAddFacultyFragmentBinding
import com.example.academicapp.models.Faculty
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class AdminAddFacultyFragment : Fragment() {
    private lateinit var binding: AdminAddFacultyFragmentBinding
    private lateinit var firebaseStorageFor12: StorageReference
    private lateinit var firebaseStorageFor10: StorageReference
    private lateinit var firebaseStorageForProfile: StorageReference
    private lateinit var firebaseDatabase: DatabaseReference
    private var tenthMarksheetUri: Uri? = null
    private var twelthMarksheetUri: Uri? = null
    private var profileImageUri: Uri? = null
    private lateinit var dialog: Dialog
    private lateinit var downloadImageUri: String

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
        savedInstanceState: Bundle?,
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
            if (!isAnyFieldEmpty()) {
                uploadProfileImage()
            } else {
                Toast.makeText(requireContext(), "Some fields are empty!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.facultyProfileImage.setOnClickListener {
            selectProfileImage()
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

    private fun uploadProfile() {
        val contact = binding.facultyContactEditText.text.toString()
        firebaseDatabase =
            FirebaseDatabase.getInstance().getReference("Faculties/$contact")
        firebaseDatabase.setValue(getFacultyInstance()).addOnSuccessListener {
            hideProgressBar()
            clearData()
            Toast.makeText(requireContext(), "Faculty Added Successfully", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(requireContext(), "Profile uploading error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadTwelthMarksheet() {
        val contact = binding.facultyContactEditText.text.toString()
        firebaseStorageFor12 = FirebaseStorage.getInstance()
            .getReference("Faculties/$contact/twelth_marksheet")
        firebaseStorageFor12.putFile(twelthMarksheetUri!!).addOnSuccessListener {
            uploadTenthMarksheet()
        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(requireContext(), "Twelth marksheet uploading error", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun uploadTenthMarksheet() {
        val contact = binding.facultyContactEditText.text.toString()
        firebaseStorageFor10 = FirebaseStorage.getInstance()
            .getReference("Faculties/$contact/tenth_marksheet")
        firebaseStorageFor10.putFile(tenthMarksheetUri!!).addOnSuccessListener {
            uploadProfile()
        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(requireContext(), "Tenth marksheet uploading error", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun uploadProfileImage() {
        showProgressBar()
        val contact = binding.facultyContactEditText.text.toString()
        firebaseStorageForProfile =
            FirebaseStorage.getInstance().getReference("Faculties/$contact/profile_pic")
        firebaseStorageForProfile.putFile(profileImageUri!!).addOnSuccessListener {
            val result = it.storage.downloadUrl
            result.addOnSuccessListener {
                downloadImageUri = it.toString()
                uploadTwelthMarksheet()
            }
        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(requireContext(), "Twelth marksheet uploading error", Toast.LENGTH_SHORT)
                .show()
        }
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

    private fun getFacultyInstance(): Faculty {
        return Faculty(binding.facultyFirstNameEditText.text.toString(),
            binding.facultyLastNameEditText.text.toString(),
            binding.facultyAddressEditText.text.toString(),
            binding.facultyContactEditText.text.toString(),
            binding.facultyQualificationsEditText.text.toString(),
            binding.facultyDomainEditText.text.toString(),
            generatePassword(),
            generateEmailId(
                binding.facultyFirstNameEditText.text.toString(),
                binding.facultyLastNameEditText.text.toString()
            ),
            downloadImageUri)
    }

    private fun generatePassword(): String {
        val password = StringBuilder()
        for (i in 0..4) {
            password.append((0..9).random().toString())
        }
        return password.toString()
    }

    private fun isAnyFieldEmpty(): Boolean {
        var isEmpty: Boolean = false
        val name = binding.facultyFirstNameEditText.text.toString()
        val address = binding.facultyAddressEditText.text.toString()
        val contact = binding.facultyContactEditText.text.toString()
        val qualification = binding.facultyQualificationsEditText.text.toString()
        val domain = binding.facultyDomainEditText.text.toString()
        val twelthMarksheet = binding.faculty12MarksheetEditText.text.toString()
        val tenthMarksheet = binding.faculty10MarksheetEditText.text.toString()
        if (name.trim().isBlank() || name.trim().isEmpty()) {
            binding.facultyFirstNameLayout.isErrorEnabled = true
            binding.facultyFirstNameLayout.error = "Empty Field"
            isEmpty = true
        }

        if (address.trim().isEmpty() || address.trim().isBlank()) {
            binding.facultyAddressLayout.isErrorEnabled = true
            binding.facultyAddressLayout.error = "Empty Field"
            isEmpty = true
        }

        if (contact.trim().isBlank() || contact.trim().isEmpty()) {
            binding.facultyContactLayout.isErrorEnabled = true
            binding.facultyContactLayout.error = "Empty Field"
            isEmpty = true
        }

        if (qualification.trim().isEmpty() || qualification.trim().isBlank()) {
            binding.facultyQualificationsLayout.isErrorEnabled = true
            binding.facultyQualificationsLayout.error = "Empty Field"
            isEmpty = true
        }

        if (domain.trim().isBlank() || domain.trim().isEmpty()) {
            binding.facultyDomainLayout.isErrorEnabled = true
            binding.facultyDomainLayout.error = "Empty Field"
            isEmpty = true
        }

        if (twelthMarksheet.trim().isEmpty() || twelthMarksheet.trim().isBlank()) {
            binding.faculty12MarksheetLayout.isErrorEnabled = true
            binding.faculty12MarksheetLayout.error = "Empty Field"
            isEmpty = true
        }

        if (tenthMarksheet.trim().isBlank() || tenthMarksheet.trim().isEmpty()) {
            binding.faculty10MarksheetLayout.isErrorEnabled = true
            binding.faculty10MarksheetLayout.error = "Empty Field"
            isEmpty = true
        }

        if (profileImageUri == null) {
            isEmpty = true
        }
        return isEmpty
    }

    private fun clearData() {
        binding.facultyFirstNameEditText.setText("")
        binding.facultyLastNameEditText.setText("")
        binding.facultyAddressEditText.setText("")
        binding.facultyContactEditText.setText("")
        binding.facultyQualificationsEditText.setText("")
        binding.facultyDomainEditText.setText("")
        binding.faculty12MarksheetEditText.setText("")
        binding.faculty10MarksheetEditText.setText("")
        binding.facultyProfileImage.setImageResource(R.drawable.profile)
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
}