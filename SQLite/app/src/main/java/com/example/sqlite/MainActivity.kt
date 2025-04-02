package com.example.sqlite

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnShow: Button
    private lateinit var tvResult: TextView

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
        edtName = findViewById(R.id.editUsername)
        edtPhone = findViewById(R.id.editPhone)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnShow = findViewById(R.id.btnShow)
        tvResult = findViewById(R.id.tvResult)

        // Khởi tạo DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Xử lý nút "Thêm"
        btnAdd.setOnClickListener {
            val name = edtName.text.toString()
            val phone = edtPhone.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                if (databaseHelper.insertContact(name, phone)) {
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút "Sửa"
        btnUpdate.setOnClickListener {
            val name = edtName.text.toString()
            val phone = edtPhone.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                if (databaseHelper.updateContact(name, phone)) {
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Không tìm thấy tên này!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút "Xóa"
        btnDelete.setOnClickListener {
            val name = edtName.text.toString()
            if (name.isNotEmpty()) {
                if (databaseHelper.deleteContact(name)) {
                    Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Không tìm thấy tên này!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập tên để xóa!", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút "Hiển thị"
        btnShow.setOnClickListener {
            tvResult.text = databaseHelper.getAllContacts()
        }
    }
}
