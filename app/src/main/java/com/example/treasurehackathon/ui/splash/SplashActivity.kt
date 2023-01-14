package com.example.treasurehackathon.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.treasurehackathon.core.data.Resource
import com.example.treasurehackathon.databinding.ActivitySplashBinding
import com.example.treasurehackathon.ui.home.donatur.HomeDonaturActivity
import com.example.treasurehackathon.ui.loginsignup.LoginSignupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    private val viewModel:SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val prefManager:SharedPreferences =
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var email = prefManager.getString(SESSION_EMAIL, "") ?: ""
        var password =prefManager.getString(SESSION_PASSWORD,"")?:""
        var token=prefManager.getString(SESSION_TOKEN,"")?:""

        Handler(Looper.getMainLooper()).postDelayed({
            if (email.trim()!=""&&password.trim()!=""&&token.trim()!="") {
                //val x=viewModel.loginWithId(token)
                viewModel.loginWithEmailPassword2(email,password).observe(this){
                    when(it){
                        is Resource.Success->{
                            val s= it.data
                            prefManager.edit().putString(SESSION_TOKEN,s?.id).apply()
                            prefManager.edit().putString(SESSION_EMAIL,s?.email).apply()
                            prefManager.edit().putString(SESSION_PASSWORD,password).apply()
                            val intent: Intent? = when(s?.role){
                                "yayasan"-> Intent(this, HomeDonaturActivity::class.java).putExtra(
                                    SplashActivity.SESSION_DATA,s)
                                "donatur"-> Intent(this, HomeDonaturActivity::class.java).putExtra(
                                    SplashActivity.SESSION_DATA,s)
                                "user"->Intent(this, HomeDonaturActivity::class.java).putExtra(
                                    SplashActivity.SESSION_DATA,s)
                                else-> Intent(this, LoginSignupActivity::class.java)
                            }
                            intent?.let {  startActivity(it)}
                            finish()
                        }
                        is Resource.Loading->{

                        }

                        is Resource.Error->{
                            deleteSession(prefManager)
                            Toast.makeText(this,"Failed to login", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            } else {
                deleteSession(prefManager)
                startActivity(Intent(this, LoginSignupActivity::class.java))
            }

        }, DELAY_TIME)

    }

    private fun deleteSession(prefManager: SharedPreferences){
        prefManager.edit().putString(SESSION_EMAIL,"").apply()
        prefManager.edit().putString(SESSION_PASSWORD,"").apply()
        prefManager.edit().putString(SESSION_TOKEN,"").apply()
    }
    companion object {
        const val DELAY_TIME: Long = 2_000
        const val SESSION_EMAIL:String="email"
        const val SESSION_PASSWORD:String="password"
        const val SESSION_TOKEN=""
        const val SESSION_DATA="session_data"
    }
}