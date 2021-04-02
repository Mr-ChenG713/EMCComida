package com.chengbo.emccomida.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chengbo.emccomida.R
import com.chengbo.emccomida.model.Pratos
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InserirPratoActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_prato)

        //Iniciar Firabase
        ref = FirebaseDatabase.getInstance().getReference("Pratos")

        var btn_save = findViewById<Button>(R.id.btn_ins_ins_prato)
        var btn_cancel = findViewById<Button>(R.id.btn_prato_back)

        btn_save.setOnClickListener {

            savedata()
        }

        btn_cancel.setOnClickListener {

            voltarPrato()
        }


    }

    private fun savedata (){

        if (!validarcampo()){
            return
        }

        var edit_nome = findViewById<EditText>(R.id.ins_prato_nome)
        var edit_quanti = findViewById<EditText>(R.id.ins_prato_quanti)
        var edit_preco = findViewById<EditText>(R.id.ins_prato_preco)
        var edit_desc = findViewById<EditText>(R.id.ins_prato_desc)

        val prato = Pratos()
        prato.id = ref.push().key.toString()
        prato.nome = edit_nome.text.toString()
        prato.quantidade = edit_quanti.text.toString()
        prato.preco = edit_preco.text.toString()
        prato.descricao = edit_desc.text.toString()

        ref.child(prato.id).setValue(prato).addOnCompleteListener {
            Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
            edit_nome.setText("")
            edit_quanti.setText("")
            edit_preco.setText("")
            edit_desc.setText("")

        }
    }

    private fun validarcampo (): Boolean{

        var valido = true
        var edit_nome = findViewById<EditText>(R.id.ins_prato_nome)
        var edit_quanti = findViewById<EditText>(R.id.ins_prato_quanti)
        var edit_preco = findViewById<EditText>(R.id.ins_prato_preco)
        var edit_desc = findViewById<EditText>(R.id.ins_prato_desc)

        if (TextUtils.isEmpty(edit_nome.text.toString())) {
            edit_nome.setError("Introduza o nome do prato")
            edit_nome.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_quanti.text.toString()) || edit_quanti.text.toString() <= "0"){
            edit_quanti.setError("Introduza a quantidade e maior que zero")
            edit_quanti.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_preco.text.toString()) || edit_preco.text.toString() <= "0"){
            edit_preco.setError("Introduza o preço e maior que zero")
            edit_preco.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_desc.text.toString())){
            edit_desc.setError("Descreve o Prato")
            edit_desc.requestFocus()
            valido = false
        }else{
            edit_nome.setError(null)
            edit_quanti.setError(null)
            edit_preco.setError(null)
            edit_desc.setError(null)
        }

        return  valido
    }

    private fun voltarPrato (){

        val intent = Intent(this@InserirPratoActivity, PratoActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
}