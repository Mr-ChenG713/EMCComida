package com.chengbo.emccomida.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chengbo.emccomida.NavigationPanel.*
import com.chengbo.emccomida.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomePanel_BottomNavigation : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_panel__bottom_navigation)

        val navigationView = findViewById<BottomNavigationView>(R.id.home_bottom_navigation)
        navigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.getItemId()) {
            R.id.ResHome -> fragment = HomeFragment()
            R.id.ResPedPendente -> fragment = PendenteEMCFragment()
            R.id.ResEcomenda -> fragment = EcomendaFragment()
            R.id.ResProfile -> fragment = ProfileFragment()
            R.id.Resinfo -> fragment = InfoFragment()
        }
        return loadcheffragment(fragment)
    }

    private fun loadcheffragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment)
                .commit()
            return true
        }
        return false
    }
}