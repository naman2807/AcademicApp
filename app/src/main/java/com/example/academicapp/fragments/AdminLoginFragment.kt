package com.example.academicapp.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.academicapp.activities.AdminActivity
import com.example.academicapp.databinding.AdminLoginBinding

class AdminLoginFragment : Fragment() {
    private lateinit var binding: AdminLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = AdminLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.login.setOnClickListener {
            if (validate()) {
                sharedPreferences = requireActivity().getSharedPreferences("login", MODE_PRIVATE)
                editor = sharedPreferences.edit()
                editor.putString("USER_ID", "admin")
                editor.commit()
                val intent = Intent(requireContext(), AdminActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            } else {
                binding.adminIdLoginLayout.isErrorEnabled = true
                binding.adminIdLoginLayout.error = ""
                binding.adminIdPasswordLayout.isErrorEnabled = true
                binding.adminIdPasswordLayout.error = ""
                Toast.makeText(requireContext(), "Check your credentials", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun validate(): Boolean {
        return binding.adminIdLogin.text.toString().equals("GLA123", false) &&
                binding.adminPasswordLogin.text.toString().equals("12345")

    }
}