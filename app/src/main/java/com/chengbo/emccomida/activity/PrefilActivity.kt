package com.chengbo.emccomida.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.chengbo.emccomida.R
import com.chengbo.emccomida.model.Pratos
import com.chengbo.emccomida.model.Restaurantes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.update_prato.view.*

class PrefilActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefil)

        //ToolBar
        supportActionBar?.title = "Prefil"

        //Iniciar Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Restaurantes")


        var btn_atualizar = findViewById<Button>(R.id.btn_update_profile)
        btn_atualizar.setOnClickListener {

        }

        loadProfile()
    }

    private fun loadProfile() {

        var text_nome = findViewById<TextView>(R.id.prefil_nome_show)
        var text_email = findViewById<TextView>(R.id.prefil_email_show)
        var text_contato = findViewById<TextView>(R.id.prefil_contato_show)
        var text_morada = findViewById<TextView>(R.id.prefil_mora_show)
        var text_cp = findViewById<TextView>(R.id.prefil_cp_show)
        var text_cidade = findViewById<TextView>(R.id.prefil_cidade_show)


        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        text_email.text =  user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                text_nome.text = snapshot.child("nome").value.toString()
                text_contato.text = snapshot.child("contatotele").value.toString()
                text_morada.text = snapshot.child("morada").value.toString()
                text_cp.text = snapshot.child("codigopostal").value.toString()
                text_cidade.text = snapshot.child("cidade").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
    
}