package com.chengbo.emccomida.adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.chengbo.emccomida.R
import com.chengbo.emccomida.activity.PratoActivity
import com.chengbo.emccomida.model.Pratos
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.prato_item.view.*
import kotlinx.android.synthetic.main.update_prato.view.*

class PratoAdapter (val context: Context, val pratos: List<Pratos>) :
    RecyclerView.Adapter<PratoAdapter.PratosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PratosViewHolder {

        return PratosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.prato_item, parent, false))
    }

    override fun onBindViewHolder(holder: PratosViewHolder, position: Int) {

        holder.bindItem(pratos[position])
    }

    override fun getItemCount(): Int  = pratos.size

    inner class PratosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvNome = itemView.item_prato_nome
        val tvPreco = itemView.item_prato_preco
        val tvQuanti = itemView.item_prato_quanti
        val tvDesc = itemView.item_prato_desc
        val btnUpdate = itemView.btn_update_prato
        val btnDelete = itemView.btn_delete_prato

        fun bindItem(prato: Pratos) {
            tvNome.text = prato.nome
            tvPreco.text = prato.preco
            tvQuanti.text = prato.quantidade
            tvDesc.text = prato.descricao

            btnUpdate.setOnClickListener { showUpdateDialog(prato) }
            btnDelete.setOnClickListener { deleteInfo(prato) }
        }

        fun showUpdateDialog(prato: Pratos) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Atualizar")

            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.update_prato, null)

            view.update_prato_nome.setText(prato.nome)
            view.update_prato_preco.setText(prato.preco)
            view.update_prato_quant.setText(prato.quantidade)
            view.update_prato_desc.setText(prato.descricao)

            builder.setView(view)
            builder.setPositiveButton("Atualizar") { dialog, which ->
                val dbUsers = FirebaseDatabase.getInstance().getReference("Pratos")

                val nome = view.update_prato_nome.text.toString().trim()
                val quanti = view.update_prato_quant.text.toString().trim()
                val preco = view.update_prato_preco.text.toString().trim()
                val desc = view.update_prato_desc.text.toString().trim()

                if (nome.isEmpty()) {
                    view.update_prato_nome.error = "Introduza o Nome do Prato"
                    view.update_prato_nome.requestFocus()
                    return@setPositiveButton
                }else if (quanti.isEmpty()){
                    view.update_prato_quant.error = "Introduza quantidade"
                    view.update_prato_quant.requestFocus()
                    return@setPositiveButton
                }else if (preco.isEmpty()){
                    view.update_prato_preco.error = "Introduza o preço"
                    view.update_prato_preco.requestFocus()
                    return@setPositiveButton
                }else if (desc.isEmpty()){
                    view.update_prato_desc.error = "Descrve algo sobre o Prato"
                    view.update_prato_desc.requestFocus()
                    return@setPositiveButton
                }


                val prato = Pratos(prato.id, nome, quanti, preco, desc)

                dbUsers.child(prato.id).setValue(prato).addOnCompleteListener {
                    Toast.makeText(context, "Atualizar com Sucesso !!!", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Não") { dialog, which ->

            }

            val alert = builder.create()
            alert.show()
        }

        fun deleteInfo(prato: Pratos) {
            val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Eliminar...")
            progressDialog.show()

            val mydatabase = FirebaseDatabase.getInstance().getReference("Pratos")
            mydatabase.child(prato.id).removeValue()

            Toast.makeText(context, "Eliminado com Sucesso", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, PratoActivity::class.java)
            context.startActivity(intent)
        }
    }


}