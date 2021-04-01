package com.chengbo.emccomida.NavigationPanel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.chengbo.emccomida.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_profile, null)
        requireActivity().title = "Adicionar Menu"

        var btn_addmenu = v.findViewById<Button>(R.id.post_dish)
        btn_addmenu.setOnClickListener {

            startActivity(Intent(context, AdcionarPrato::class.java))
        }

        return v
    }
}