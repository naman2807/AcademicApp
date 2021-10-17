package com.example.academicapp.activities

import android.content.ActivityNotFoundException
import android.content.Intent
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
import com.example.academicapp.fragments.admin.AdminHomeFragment
import com.example.academicapp.fragments.admin.AdminUploadNoticeFragment
import com.example.academicapp.viewmodels.AdminViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class AdminActivity: AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var selectedFragment : Fragment
    private val viewModel: AdminViewModel by viewModels()

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
                R.id.setting -> Toast.makeText(this, "Navigation 1", Toast.LENGTH_SHORT).show()
                R.id.gg -> Toast.makeText(this, "Navigation 2", Toast.LENGTH_SHORT).show()
                R.id.setting2 -> Toast.makeText(this, "Navigation 3", Toast.LENGTH_SHORT).show()
            }
            return@setNavigationItemSelectedListener true
        }

        //        Setting up Bottom Navigation Drawer
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> selectedFragment = AdminHomeFragment()
                R.id.upload_notice -> selectedFragment = AdminUploadNoticeFragment()
                R.id.bottom_2e -> Toast.makeText(this, "Bottom Navigation 3", Toast.LENGTH_SHORT)
                    .show()
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}