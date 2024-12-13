package com.example.padicare

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import retrofit2.*
import java.io.FileOutputStream

class ScanActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private val CAMERA_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        // Inisialisasi tombol
        val btnBack: ImageView = findViewById(R.id.btn_back)
        val btnTakePhoto: ImageView = findViewById(R.id.btn_take_photo)
        val btnUploadImage: Button = findViewById(R.id.btn_upload_image)

        btnBack.setOnClickListener {
            finish()
        }

        // fitur live camera
        btnTakePhoto.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(this, "Camera not supported", Toast.LENGTH_SHORT).show()
            }
        }

        // fitur upload gambar dari galeri
        btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        navigateToConfirmImage(imageUri)
                    }
                }
                CAMERA_REQUEST -> {
                    val photoBitmap = data?.extras?.get("data") as? Bitmap
                    if (photoBitmap != null) {
                        val imageFile = bitmapToFile(photoBitmap)
                        if (imageFile != null) {
                            val imageUri = Uri.fromFile(imageFile)
                            navigateToConfirmImage(imageUri)
                        }
                    }
                }
            }
        }
    }

    // Fungsi untuk berpindah ke halaman konfirmasi gambar
    private fun navigateToConfirmImage(imageUri: Uri) {
        val intent = Intent(this, ConfirmImageActivity::class.java)
        intent.putExtra("imageUri", imageUri)
        startActivity(intent)
    }

    // Fungsi untuk mengonversi Bitmap ke File
    private fun bitmapToFile(bitmap: Bitmap): File? {
        return try {
            val file = File(cacheDir, "temp_image.jpg")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}