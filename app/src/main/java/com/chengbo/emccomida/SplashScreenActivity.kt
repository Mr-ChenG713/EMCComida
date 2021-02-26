package com.chengbo.emccomida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.chengbo.emccomida.activity.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        handler = Handler()
        handler.postDelayed({

            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) //delaying 2 segundo

    }
}