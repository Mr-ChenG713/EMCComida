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

        var btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener {

            login()
            //abrirNavigation()
        }

    }

    // Verfificar se tem sessao iniciada
   public override fun onStart() {

        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            abrirNavigation();
        }
    }

    private fun login (){

        if (!validarcampo()){
            return
        }

        var edit_mail = findViewById<EditText>(R.id.login_email_restaurante)
        var edit_pass = findViewById<EditText>(R.id.login_pass_restaurante)

        auth.signInWithEmailAndPassword(edit_mail.text.toString(), edit_pass.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){

                    val currentUser = auth.currentUser
                    Toast.makeText(this@LoginActivity, "Bem-Vindo !!!", Toast.LENGTH_SHORT).show()
                    abrirNavigation()
                } else{

                    Toast.makeText(this@LoginActivity, "Login Inválida !!!", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun abrirNavigation (){

        val intent = Intent(this@LoginActivity, HomePanel_BottomNavigation::class.java)
        startActivity(intent)
        finish()
    }

    fun abrirRegistPage(view: View){

        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun abrirRecuPass (view: View){

        val intent = Intent(this@LoginActivity, RecuPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validarcampo (): Boolean{

        var valido = true
        var edit_mail = findViewById<EditText>(R.id.login_email_restaurante)
        var edit_pass = findViewById<EditText>(R.id.login_pass_restaurante)

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

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}

