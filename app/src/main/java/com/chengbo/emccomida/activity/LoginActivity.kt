package com.chengbo.emccomida.activity

import android.accounts.AccountAuthenticatorResponse
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.chengbo.emccomida.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var listener: FirebaseAuth.AuthStateListener
    private lateinit var dialog: AlertDialog
    private val compositeDisposable = CompositeDisposable()
    private lateinit var cloudFuncitons: ICloudFunctions

    companion object {
        private val APP_REQUES_CODE = 7171 //any number
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun onStop() {
        if (listener != null) {
            firebaseAuth.removeAuthStateListener(listener)
        }
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        dialog = SpotsDialog.Builder().setContext(this).setCancelable(false).build()
        cloudFuncitons = RetrofitCloudClient.getInstance().create(ICloudFunctions::class.java)
        listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {

                //tem sessão iniciada
                Toast.makeText(this@LoginActivity, "Já tem sessão iniciada", Toast.LENGTH_SHORT).show()
            } else {

                //não tem sessão iniciada
                val accessToken = AccountKit.getCurrentAccessToken()
                if (accessToken != null) {
                    getCustomerToken(accessToken)
                } else {
                    phoneLogin()
                }
            }
        }
    }

    private fun phoneLogin() {
        val intent = Intent(this@LoginActivity, AccountKitActivity::class.java)
        val configurationBuidConfig = AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE, AccoutActivity.ResponseType.TOKEN)
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuidConfig.build())
        startActivityForResult(intent, APP_REQUES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_REQUES_CODE) {
            handleFaceebookLoginResult(resultCode, data)
        }
    }

    private fun handleFaceebookLoginResult(resultCode: Int, data: Intent?) {
        val result = data!!.getParcelableExtra<AccountKitLoginResult>(AccountKitLoginResult.RESULT_KEY)
        if (result!!.error != null) {
            Toast.makeText(this@LoginActivity, "" + result!!.error!!.userFacingMessage, Toast.LENGTH_SHORT).show()
        } else if (result.wasCancelled() || resultCode == Activity.RESULT_CANCELED) {
            finish()
        } else {
            if (result.accessToken != null) {
                getCustomToken(result.accessToken!!)
                Toast.makeText(this@LoginActivity, "Login Sucesso!!!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getCustomToken(accessToken: AccessToken) {
        dialog!!.show()
        compositeDisposable.add(cloudFuncitons!!.getCustomToken(accessToken.token)
                .subscribeOn(Schedulers.io())
                .obeserveOn(AndroidSchedulers.mainThread())
                .subscribe({ responseBody ->
                    val customToken = responseBody.sting()
                    signInWithCustomToken(customToken)
                }, { t: Throwable? ->
                    dialog!!.dismiss()
                    Toast.makeText(this@LoginActivity, "" + t!!.mesage, Toast.LENGTH_SHORT).show()
                }));

    }

    private fun signInWithCustomToken(customToken: Sting) {

        dialog!!.dismiss()
        firebaseAuth!!.signInWithCustomToken(customToken)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Login Invalida !!! ", Toast.LENGTH_SHORT).show()
                    }
                }
    }
}
