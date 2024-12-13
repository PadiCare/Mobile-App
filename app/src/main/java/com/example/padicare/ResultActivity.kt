package com.example.padicare

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val btnBack: ImageView = findViewById(R.id.btn_back)
        val imageResult: ImageView = findViewById(R.id.image_result)
        val textResult: TextView = findViewById(R.id.text_result)
        val btnScanAgain: Button = findViewById(R.id.btn_scan_again)

        val resultText = intent.getStringExtra("resultText")
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")

        textResult.text = resultText
        if (imageUri != null) {
            imageResult.setImageURI(imageUri)
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnScanAgain.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}