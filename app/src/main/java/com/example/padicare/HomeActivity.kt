package com.example.padicare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val tvScanNow: TextView = findViewById(R.id.tv_scan_now)
        val btnAboutUs: Button = findViewById(R.id.btn_about_us)

        tvScanNow.setOnClickListener{
            // navigasi ke halaman ScanActivity
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }

        btnAboutUs.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }
}