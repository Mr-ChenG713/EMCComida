package com.chengbo.emccomida.activity



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button


import com.chengbo.emccomida.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Iniciar Firebase
        auth = Firebase.auth

        var btn_logout = findViewById<Button>(R.id.btn_logout)
        btn_logout.setOnClickListener {

            logout()
        }
    }
 
    fun abrirPrato (view: View){

        val intent = Intent(this@HomeActivity, PratoActivity::class.java)
        startActivity(intent)
    }

    fun abrirEmcPendnete (view: View){

        val intent = Intent(this@HomeActivity, EmcPendenteActivity::class.java)
        startActivity(intent)
    }

    fun abrirncomenda (view: View){

        val intent = Intent(this@HomeActivity, EncomendaActivity::class.java)
        startActivity(intent)
    }

    fun abrirPrefil (view: View){

        val intent = Intent(this@HomeActivity, PrefilActivity::class.java)
        startActivity(intent)
    }

    private fun logout(){

        Firebase.auth.signOut()
        val intent = Intent(this@HomeActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}