package com.asmita.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userName = findViewById<EditText>(R.id.username)
        val passWord = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        loginBtn.setOnClickListener {
            if (userName.text.toString() == "gfg" && passWord.text.toString() == "geeksforgeeks"
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    "Modular Architecture Works Fine",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
