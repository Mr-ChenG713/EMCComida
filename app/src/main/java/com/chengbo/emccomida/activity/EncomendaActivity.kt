package com.chengbo.emccomida.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chengbo.emccomida.R

class EncomendaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encomenda)

        //ToolBar
        supportActionBar?.title = "Encomendas"
    }
}