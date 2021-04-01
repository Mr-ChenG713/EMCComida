package com.chengbo.emccomida.NavigationPanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.chengbo.emccomida.Activity.HomeActivity
import com.chengbo.emccomida.R
import com.google.firebase.auth.FirebaseAuth

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_info, null)
        requireActivity().title = "Informação"


        var btn_logout = v.findViewById<Button>(R.id.logout_app)
        var btn_inforesta = v.findViewById<Button>(R.id.profile_restaurante)

        btn_logout.setOnClickListener {

            Logout()
        }

        btn_inforesta.setOnClickListener {


        }


        return v
    }

    private fun Logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(activity, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}