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
import com.chengbo.emccomida.Model.UserModel
import com.chengbo.emccomida.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Iniciar Firebase
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("user")

        var btn_registar = findViewById<Button>(R.id.regista_entrar)

        btn_registar.setOnClickListener{

            registar()
        }

    }

    private fun registar (){
        if (!validarcampo()){
            return
        }


        var edit_nome = findViewById<EditText>(R.id.ins_reg_nome)
        var edit_nif = findViewById<EditText>(R.id.ins_reg_nif)
        var edit_mail = findViewById<EditText>(R.id.ins_reg_mail)
        var edit_pass = findViewById<EditText>(R.id.ins_reg_pass)
        var edit_morada = findViewById<EditText>(R.id.ins_reg_morada)
        var edit_tele = findViewById<EditText>(R.id.ins_reg_tele)

        auth.createUserWithEmailAndPassword(edit_mail.text.toString(), edit_pass.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){

                    val currentUser = auth.currentUser
                    //val currentUserdb = databaseReference.child(currentUser?.uid!!)
                    val userModel = UserModel()
                    if (currentUser != null) {
                        userModel.uid = currentUser.uid
                    }
                    userModel.nome = edit_nome.text.toString()
                    userModel.nif = edit_nif.text.toString()
                    userModel.mail = edit_mail.text.toString()
                    userModel.morada = edit_morada.text.toString()
                    userModel.telemovel = edit_tele.text.toString()

                    userModel.uid?.let { it1 -> databaseReference?.child(it1)?.setValue(userModel) }

                    Toast.makeText(this@RegisterActivity, "Registo com Sucesso", Toast.LENGTH_SHORT).show()
                    homepage()
                }else{

                    Toast.makeText(this@RegisterActivity, "Este email já existe", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun validarcampo(): Boolean{

        var valido = true
        var edit_nome = findViewById<EditText>(R.id.ins_reg_nome)
        var edit_nif = findViewById<EditText>(R.id.ins_reg_nif)
        var edit_mail = findViewById<EditText>(R.id.ins_reg_mail)
        var edit_pass = findViewById<EditText>(R.id.ins_reg_pass)
        var edit_morada = findViewById<EditText>(R.id.ins_reg_morada)
        var edit_tele = findViewById<EditText>(R.id.ins_reg_tele)


        if (TextUtils.isEmpty(edit_nome.text.toString())){
            edit_nome.setError("Introduza o seu nome")
            edit_nome.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_nif.text.toString())){
            edit_nif.setError("Introduza o seu NIF")
            edit_nif.requestFocus()
            valido = false
        }else if (edit_nif.text.length != 9){
            edit_nif.setError("O NIF tem 9 números")
            edit_nif.requestFocus()
            valido = false
        }else if (!isValidEmail(edit_mail.text.toString())){
            edit_mail.setError("Introduza o email válido")
            edit_mail.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_pass.text.toString()) || edit_pass.text.length < 6){
            edit_pass.setError("Password inválida, tem que ser maior ou igaul 6 caracter")
            edit_pass.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_morada.text.toString())){
            edit_morada.setError("Introduza a morada")
            edit_morada.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_tele.text.toString())){
            edit_tele.setError("Introduza o número de telemóvel")
            edit_tele.requestFocus()
            valido = false
        }else{

            edit_nome.setError(null)
            edit_nif.setError(null)
            edit_mail.setError(null)
            edit_pass.setError(null)
            edit_morada.setError(null)
            edit_tele.setError(null)
        }

        return valido
    }

    fun abrirEntrada(view: View){

        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun homepage(){

        val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}


