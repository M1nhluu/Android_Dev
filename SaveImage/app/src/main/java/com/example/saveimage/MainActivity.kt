package com.example.saveimage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Xử lý insets để hỗ trợ edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ánh xạ các thành phần giao diện
        val edtUrl = findViewById<EditText>(R.id.edtUrl)
        val btnDownload = findViewById<Button>(R.id.btnDownload)
        val imgView = findViewById<ImageView>(R.id.imgView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Xử lý sự kiện click Button
        btnDownload.setOnClickListener {
            val imageUrl = edtUrl.text.toString()
            if (imageUrl.isNotEmpty()) {
                DownloadImageTask(imgView, progressBar).execute(imageUrl)
            }
        }
    }
}
