package com.chengbo.emccomida.NavigationPanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chengbo.emccomida.Model.Prato
import com.chengbo.emccomida.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class AdcionarPrato : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var databasea: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adcionar_prato)

        //Iniciar Firebase
        auth = Firebase.auth
        database = Firebase.database.reference.child("Prato")
        databasea = Firebase.database.reference.child("Restaurante")

        var btn_submeter = findViewById<Button>(R.id.submeter_food)

        btn_submeter.setOnClickListener {

            submeterfood()
        }
    }

    private fun submeterfood(){

        if (!validarcampo()){
            return
        }else {

            var edit_nome = findViewById<EditText>(R.id.foodnome)
            var edit_desc = findViewById<EditText>(R.id.fooddesc)
            var edit_quanti = findViewById<EditText>(R.id.foodquantidade)
            var edit_preco = findViewById<EditText>(R.id.foodpreco)

            var prato = Prato()
            prato.id = UUID.randomUUID().toString()
            prato.Nome = edit_nome.text.toString()
            prato.Descricao = edit_desc.text.toString()
            prato.Quantidade = edit_quanti.text.toString()
            prato.Preco = edit_preco.text.toString()

            database.child(prato.id!!).setValue(prato)
            Toast.makeText(this@AdcionarPrato, "Submeter com Sucesso", Toast.LENGTH_SHORT).show()
            clearcampo()
        }

    }


    private fun validarcampo(): Boolean{

        var valido = true

        var edit_nome = findViewById<EditText>(R.id.foodnome)
        var edit_desc = findViewById<EditText>(R.id.fooddesc)
        var edit_quanti = findViewById<EditText>(R.id.foodquantidade)
        var edit_preco = findViewById<EditText>(R.id.foodpreco)

        if (TextUtils.isEmpty(edit_nome.text.toString())){
            edit_nome.setError("Introduza o nome do Prato")
            edit_nome.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_desc.text.toString())){
            edit_desc.setError("Introduza uma breve descrição")
            edit_desc.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_quanti.text.toString())){
            edit_quanti.setError("Introduza quantidade")
            edit_quanti.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_preco.text.toString())){
            edit_preco.setError("Introduza o preço")
            edit_preco.requestFocus()
            valido = false
        }else{

            edit_nome.setError(null)
            edit_desc.setError(null)
            edit_quanti.setError(null)
            edit_preco.setError(null)
        }

        return valido
    }

    private fun clearcampo (){
        var edit_nome = findViewById<EditText>(R.id.foodnome)
        var edit_desc = findViewById<EditText>(R.id.fooddesc)
        var edit_quanti = findViewById<EditText>(R.id.foodquantidade)
        var edit_preco = findViewById<EditText>(R.id.foodpreco)

        edit_nome.text.clear()
        edit_desc.text.clear()
        edit_quanti.text.clear()
        edit_preco.text.clear()

    }
}