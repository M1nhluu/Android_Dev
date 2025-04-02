package com.example.sharedpreferenceth4

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var btnShow: Button
    private lateinit var tvResult: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ánh xạ View từ layout
        edtUsername = findViewById(R.id.editUsername)
        edtPassword = findViewById(R.id.editPassword)
        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)
        btnShow = findViewById(R.id.btnShow)
        tvResult = findViewById(R.id.tvResult)

        // Khởi tạo PreferenceHelper
        preferenceHelper = PreferenceHelper(this)

        // Xử lý nút "Lưu"
        btnSave.setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng swap đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            } else {
                preferenceHelper.saveUser(username, password)
                Toast.makeText(this, "Đã lưu!", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút "Xóa"
        btnDelete.setOnClickListener {
            preferenceHelper.deleteUser()
            tvResult.text = "Dữ liệu đã bị xóa!"
            Toast.makeText(this, "Đã xóa dữ liệu!", Toast.LENGTH_SHORT).show()
        }

        // Xử lý nút "Hiển thị"
        btnShow.setOnClickListener {
            val (username, password) = preferenceHelper.getUser()
            if (username != null && password != null) {
                tvResult.text = "Tên: $username\nMật khẩu: $password"
            } else {
                tvResult.text = "Không có dữ liệu!"
            }
        }
    }
}
