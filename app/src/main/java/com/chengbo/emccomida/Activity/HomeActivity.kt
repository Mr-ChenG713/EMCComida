package com.chengbo.emccomida.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.chengbo.emccomida.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var btnlogin = findViewById<Button>(R.id.btn_main_login)
        var btnregister = findViewById<Button>(R.id.btn_main_registar)

        btnlogin.setOnClickListener {

            val intentLogin = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intentLogin)
            finish()
        }

        btnregister.setOnClickListener {

            val intentRegister = Intent(this@HomeActivity, RegisterActivity::class.java)
            startActivity(intentRegister)
            finish()
        }


    }


}