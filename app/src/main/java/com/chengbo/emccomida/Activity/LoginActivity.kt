package com.chengbo.emccomida.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chengbo.emccomida.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Iniciar Firebase
        auth = Firebase.auth

        var btnlogin = findViewById<Button>(R.id.login_entrar)

        btnlogin.setOnClickListener {
            login()
        }


    }

    private fun login(){

        if (!validarcampo()){
            return
        }

        var edit_mail = findViewById<EditText>(R.id.ins_login_mail)
        var edit_pass = findViewById<EditText>(R.id.ins_login_password)

        auth.signInWithEmailAndPassword(edit_mail.text.toString(), edit_pass.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){

                        val currentUser = auth.currentUser
                        abrirHome()
                    } else{

                        Toast.makeText(this@LoginActivity, "Passord Incorreto !!!", Toast.LENGTH_SHORT).show()
                    }
                }

    }

    // Verfificar se tem sessao iniciada
    public override fun onStart() {

        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            abrirHome();
        }
    }

    private fun validarcampo (): Boolean{

        var valido = true
        var edit_mail = findViewById<EditText>(R.id.ins_login_mail)
        var edit_pass = findViewById<EditText>(R.id.ins_login_password)

        if (!isValidEmail(edit_mail.text.toString())){
            edit_mail.setError("Introduza o email válido")
            edit_mail.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_pass.text.toString())){
            edit_pass.setError("Introduza password válido")
            edit_pass.requestFocus()
            valido = false
        }else{
            edit_mail.setError(null)
            edit_pass.setError(null)
        }

        return  valido
    }

    private fun abrirHome (){

        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun abrirRegisto(view: View){

        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}

