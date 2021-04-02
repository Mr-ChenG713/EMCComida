package com.chengbo.emccomida.activity



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


import com.chengbo.emccomida.R



class HomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun abrirPrato (view: View){

        val intent = Intent(this@HomeActivity, PratoActivity::class.java)
        startActivity(intent)
    }
}