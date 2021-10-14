package com.example.academicapp.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.academicapp.R
import com.google.android.material.navigation.NavigationView

class AdminActivity: AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        //Setting up Navigation Drawer
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.setting -> Toast.makeText(this, "Navigation 1", Toast.LENGTH_SHORT).show()
                R.id.gg -> Toast.makeText(this, "Navigation 2", Toast.LENGTH_SHORT).show()
                R.id.setting2 -> Toast.makeText(this, "Navigation 3", Toast.LENGTH_SHORT).show()
            }
            return@setNavigationItemSelectedListener true
        }

    }
}