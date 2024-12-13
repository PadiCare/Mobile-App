package com.example.padicare

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ConfirmImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_image)

        // Inisialisasi elemen UI
        val btnBack: ImageView = findViewById(R.id.btn_back)
        val imageToConfirm: ImageView = findViewById(R.id.image_to_confirm)
        val btnConfirm: Button = findViewById(R.id.btn_confirm)

        // Ambil URI gambar dari Intent
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")

        // Tampilkan gambar
        if (imageUri != null) {
            imageToConfirm.setImageURI(imageUri)
        } else {
            Toast.makeText(this, "Image not found!", Toast.LENGTH_SHORT).show()
        }

        // Tombol Back untuk kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Tombol Konfirmasi untuk memulai proses scan
        btnConfirm.setOnClickListener {
            if (imageUri != null) {
                scanImage(imageUri)
            } else {
                Toast.makeText(this, "No image to scan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun scanImage(imageUri: Uri) {
        try {
            val file = getFileFromUri(imageUri)
            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestBody)

            // Panggil API menggunakan Retrofit
            RetrofitInstance.api.uploadImage(imagePart).enqueue(object : Callback<UploadResponse> {
                override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ConfirmImageActivity, "Scan Successful!", Toast.LENGTH_SHORT).show()
                        // Pindah ke halaman hasil
                    } else {
                        Toast.makeText(this@ConfirmImageActivity, "Scan Failed!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    Toast.makeText(this@ConfirmImageActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileFromUri(uri: Uri): File {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val file = File(cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return file
    }
}
