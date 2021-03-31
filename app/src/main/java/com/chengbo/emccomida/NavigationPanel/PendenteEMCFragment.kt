package com.chengbo.emccomida.NavigationPanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chengbo.emccomida.R


class PendenteEMCFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_pendenteemc, null)
        requireActivity().title = "Pedido Pendente"
        return v
    }
}