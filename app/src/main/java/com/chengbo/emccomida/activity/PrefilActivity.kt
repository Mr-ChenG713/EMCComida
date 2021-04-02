package com.chengbo.emccomida.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chengbo.emccomida.R

class PrefilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefil)

        //ToolBar
        supportActionBar?.title = "Prefil"
    }
}