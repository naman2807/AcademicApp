package com.example.academicapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.academicapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connectivityManager: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isAvailable) {
            sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
            val person = sharedPreferences.getString("USER_ID", "null")
            if (person!!.equals("admin")) {
                val intent = Intent(this, AdminActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            } else {
                setContentView(R.layout.activity_main)
            }
        } else {
            setContentView(R.layout.network_error)
            val button = findViewById<Button>(R.id.error_button)
            button.setOnClickListener {
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }
        }

    }
}