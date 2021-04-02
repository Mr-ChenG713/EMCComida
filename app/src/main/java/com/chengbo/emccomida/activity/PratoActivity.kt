package com.chengbo.emccomida.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.chengbo.emccomida.R
import com.chengbo.emccomida.adapter.PratoAdapter
import com.chengbo.emccomida.model.Pratos
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_prato.*

class PratoActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var pratos: MutableList<Pratos>
    lateinit var pratosAdapter: PratoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prato)

        //ToolBar
        supportActionBar?.title = "Pratos"

        //Iniciar Firebase
        ref = FirebaseDatabase.getInstance().getReference("Pratos")
        pratos = mutableListOf()

        var btn_ins_prato = findViewById<Button>(R.id.btn_ins_prato)

        btn_ins_prato.setOnClickListener {

            abrirPratoInsPag()
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    pratos.clear()
                    for (prt in dataSnapshot.children) {
                        val prato = prt.getValue(Pratos::class.java)
                        pratos.add(prato!!)
                    }

                    pratosAdapter = PratoAdapter(this@PratoActivity, pratos)

                    recy_prato.apply {
                        layoutManager = LinearLayoutManager(this@PratoActivity)
                        adapter = pratosAdapter
                    }
                }
            }
        })
    }

    private fun abrirPratoInsPag (){

        val intent = Intent(this@PratoActivity, InserirPratoActivity::class.java)
        startActivity(intent)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}