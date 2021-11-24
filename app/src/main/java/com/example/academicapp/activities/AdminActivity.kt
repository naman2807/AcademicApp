package com.example.academicapp.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.academicapp.R
import com.example.academicapp.fragments.OptionsFragment
import com.example.academicapp.fragments.admin.*
import com.example.academicapp.viewmodels.AdminViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class AdminActivity: AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var selectedFragment : Fragment
    private val viewModel: AdminViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        //Setting up Navigation Drawer
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.upload_pdf -> {
                    selectedFragment = AdminUploadPdfFragment()
                    supportActionBar?.title = "Upload Pdf"
                }
                R.id.add_faculty -> {
                    selectedFragment = AdminAddFacultyFragment()
                    supportActionBar?.title = "Add Faculty"
                }
                R.id.update_faculty -> {
                    selectedFragment = AdminUpdateFacultyFragment()
                    supportActionBar?.title = "Update Faculty"
                }
                R.id.logout -> logout()
            }

            viewModel.selectFragment(selectedFragment)
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit()
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }

        //        Setting up Bottom Navigation Drawer
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    selectedFragment = AdminHomeFragment()
                    supportActionBar?.title = "Academia"
                }
                R.id.upload_notice -> {
                    selectedFragment = AdminUploadNoticeFragment()
                    supportActionBar?.title = "Upload Notice"
                }
                R.id.upload_function_image -> {
                    selectedFragment = AdminUploadFunctionImageFragment()
                    supportActionBar?.title = "Upload Function Image"
                }
            }

            viewModel.selectFragment(selectedFragment)
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit()
            return@setOnItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    private fun logout(){
        sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        finish()
    }

}