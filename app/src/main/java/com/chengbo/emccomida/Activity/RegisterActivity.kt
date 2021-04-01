package com.chengbo.emccomida.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chengbo.emccomida.Model.Restaurante
import com.chengbo.emccomida.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    var role = "Restaurante"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Iniciar Firebase
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("Restaurante")

        var btn_registar = findViewById<Button>(R.id.btn_reg_restaurante)
        var btn_voltar_login = findViewById<Button>(R.id.reg_resta_go_login)

        btn_registar.setOnClickListener {

            registarResta()
        }

        btn_voltar_login.setOnClickListener {

            irPaginaLogin()
        }
    }

    private fun registarResta (){
        if (!validarcampo()){
            return
        }

        var edit_nome = findViewById<EditText>(R.id.nome_restaurante)
        var edit_email = findViewById<EditText>(R.id.email_restaurante)
        var edit_pass = findViewById<EditText>(R.id.password_restaurante)
        var edit_tele = findViewById<EditText>(R.id.tele_restaurante)
        var edit_morada = findViewById<EditText>(R.id.morada_restaurante)
        var edit_cp = findViewById<EditText>(R.id.code_restaurante)
        var edit_cidade = findViewById<EditText>(R.id.cidade_restaurante)

        auth.createUserWithEmailAndPassword(edit_email.text.toString(), edit_pass.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){

                    val currentUser = auth.currentUser
                    //val currentUserdb = databaseReference.child(currentUser?.uid!!)
                    val restauranteModel = Restaurante()
                    if (currentUser != null) {
                        restauranteModel.uid = currentUser.uid
                    }

                    restauranteModel.nome = edit_nome.text.toString()
                    restauranteModel.mail = edit_email.text.toString()
                    restauranteModel.contatotele = edit_tele.text.toString()
                    restauranteModel.morada = edit_morada.text.toString()
                    restauranteModel.codigopostal = edit_cp.text.toString()
                    restauranteModel.cidade = edit_cidade.text.toString()

                    restauranteModel.uid?.let { it -> databaseReference?.child(it)?.setValue(restauranteModel) }
                    Toast.makeText(this@RegisterActivity, "Registo com Sucesso", Toast.LENGTH_SHORT).show()
                    irPaginaLogin()
                }else{

                    Toast.makeText(this@RegisterActivity, "Este email já existe", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun validarcampo(): Boolean{

        var valido = true

        var edit_nome = findViewById<EditText>(R.id.nome_restaurante)
        var edit_email = findViewById<EditText>(R.id.email_restaurante)
        var edit_pass = findViewById<EditText>(R.id.password_restaurante)
        var edit_tele = findViewById<EditText>(R.id.tele_restaurante)
        var edit_morada = findViewById<EditText>(R.id.morada_restaurante)
        var edit_cp = findViewById<EditText>(R.id.code_restaurante)
        var edit_cidade = findViewById<EditText>(R.id.cidade_restaurante)

        if (TextUtils.isEmpty(edit_nome.text.toString())){
            edit_nome.setError("Introduza o nome de Restaurante")
            edit_nome.requestFocus()
            valido = false
        }else if (!isValidEmail(edit_email.text.toString())){
            edit_email.setError("Introduza o email válido")
            edit_email.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_pass.text.toString()) || edit_pass.text.length < 6){
            edit_pass.setError("Password inválida, tem que ser maior ou igaul 6 caracter")
            edit_pass.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_tele.text.toString())){
            edit_tele.setError("Introduza o número de telemóvel")
            edit_tele.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_morada.text.toString())){
            edit_morada.setError("Introduza a morada")
            edit_morada.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_morada.text.toString())){
            edit_cp.setError("Introduza o Código Postal")
            edit_cp.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_cidade.text.toString())){
            edit_cidade.setError("Introduza Cidade")
            edit_cidade.requestFocus()
            valido = false
        }else{

            edit_nome.setError(null)
            edit_email.setError(null)
            edit_pass.setError(null)
            edit_tele.setError(null)
            edit_morada.setError(null)
            edit_cp.setError(null)
            edit_cidade.setError(null)
        }

        return valido
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun irPaginaLogin (){

        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}


