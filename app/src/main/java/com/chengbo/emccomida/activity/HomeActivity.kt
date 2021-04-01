package com.chengbo.emccomida.activity


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.chengbo.emccomida.R
import com.chengbo.emccomida.fragment.CoronaTipsFragment
import com.chengbo.emccomida.fragment.QnaFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem: MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Iniciar firebase
        auth = FirebaseAuth.getInstance()
        //Iniciar componentes
        drawerLayout=findViewById(R.id.drawerLayout)
        toolbar=findViewById(R.id.toolbar)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        navigationView=findViewById(R.id.navigationView)

        setUpToolBar()
        openHome()
        //displaying  the hamburger icon on the home button of the action bar
        val actionBarDrawerToggle=
            ActionBarDrawerToggle(this@HomeActivity, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        //enabling the the different fragments on the menu drawer to listen to the click
        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.home ->{
                    openHome()
                }
                R.id.myProflie ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,MyProfileFragment()).commit()
                    supportActionBar?.title="Prefil"
                    drawerLayout.closeDrawers()}
                R.id.favouriteRestaurants ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,FavouriteRestaurantsFragment()).commit()
                    supportActionBar?.title="Restaurante Favorito"
                    drawerLayout.closeDrawers()}
                R.id.orderHistory ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,OrderHistoryFragment()).commit()
                    supportActionBar?.title="Encomenda"
                    drawerLayout.closeDrawers()}
                R.id.coronaPrecautions->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,CoronaTipsFragment()).commit()
                    supportActionBar?.title="Atenção Covid19"
                    drawerLayout.closeDrawers()}
                R.id.qna ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,QnaFragmentt()).commit()
                    supportActionBar?.title="Sobre"
                    drawerLayout.closeDrawers()}
                R.id.logOut ->{
                    val dialog= AlertDialog.Builder(this)
                    dialog.setTitle("Terminar Sessão")
                    dialog.setIcon(R.drawable.ic_baseline_exit_to_app_24)
                    dialog.setMessage("Terminar Sessão")
                    dialog.setPositiveButton("Sim") { dialogInterface: DialogInterface, i: Int ->
                        auth.signOut()
                        startActivity(Intent(this,LoginActivity::class.java))
                    }
                    dialog.setNegativeButton("Não") { dialogInterface: DialogInterface, i: Int -> }
                    dialog.show()

                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    //setup toolbar as an action bar
    private fun setUpToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setCheckedItem(R.id.home)
    }

    //opening the drawer layout to open from the left
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    //setting home fragment as the default screen to be displayed when the user successfully logins
    private fun openHome(){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,HomeFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title="Restaurantes"
    }

    override fun onBackPressed() {
        when(supportFragmentManager.findFragmentById(R.id.frameLayout)){
            else-> ActivityCompat.finishAffinity(this)
            //super.onBackPressed()
        }
    }
}