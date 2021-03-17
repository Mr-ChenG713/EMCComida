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

        //toolBar

        //toolBar
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "EMCComida"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var btnlogout = findViewById<Button>(R.id.btn_logout)

        btnlogout.setOnClickListener {

            logout()
        }

    }

    private fun logout (){

        Firebase.auth.signOut()
        voltarLogin()
    }

    private fun voltarLogin(){

        val intent = Intent(this@HomeActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}