package com.chengbo.emccomida.NavigationPanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chengbo.emccomida.Model.Prato
import com.chengbo.emccomida.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private val database = Firebase.database
    private lateinit var messagesListener: ValueEventListener
    private val listPrato:MutableList<Prato> = ArrayList()
    val myRef = database.getReference("Prato")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_home, null)
        requireActivity().title = "Inicio"

        listPrato.clear()
        setupRecyclerView(pratoRecyclerView)

        return v
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        messagesListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listPrato.clear()
                dataSnapshot.children.forEach { child ->
                    val prato: Prato? =
                        Prato(child.child("Nome").getValue<String>(),
                            child.child("quantidade").getValue<String>(),
                            child.child("preco").getValue<String>(),
                            child.child("descricao").getValue<String>(),
                            child.child("ImageURL").getValue<String>(),
                            child.key)
                    prato?.let { listPrato.add(it) }
                }
                recyclerView.adapter = PratoViewAdapter(listPrato)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "messages:onCancelled: ${error.message}")
            }
        }
        myRef.addValueEventListener(messagesListener)

        deleteSwipe(recyclerView)
    }

    class PratoViewAdapter(private val values: List<Prato>) :
        RecyclerView.Adapter<PratoViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.prato_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val prato = values[position]
            holder.mNomeTextView.text = prato.Nome
            holder.mQuantTextView.text = prato.Quantidade
            holder.mPrecoTextView.text = prato.Preco
            holder.mPosterImgeView?.let {
                Glide.with(holder.itemView.context)
                    .load(prato.ImageURL)
                    .into(it)
            }

            holder.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, Prato::class.java).apply {
                    putExtra("id", prato.id)
                }
                v.context.startActivity(intent)
            }

            /*holder.itemView.setOnLongClickListener{ v ->
                val intent = Intent(v.context, EditActivity::class.java).apply {
                    putExtra("id", prato.id)
                }
                v.context.startActivity(intent)
                true
            }*/

        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mNomeTextView: TextView = view.
            val mQuantTextView: TextView = view.dateTextView
            val mPrecoTextView: TextView = view.dateTextView
            val mPosterImgeView: ImageView? = view.posterImgeView
        }
    }

    private fun deleteSwipe(recyclerView: RecyclerView){
        val touchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listPrato.get(viewHolder.adapterPosition).id?.let { myRef.child(it).setValue(null) }
                listPrato.removeAt(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}