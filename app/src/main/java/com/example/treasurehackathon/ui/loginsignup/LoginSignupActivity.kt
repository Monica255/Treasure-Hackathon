package com.example.treasurehackathon.ui.loginsignup

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.treasurehackathon.R
import com.example.treasurehackathon.core.data.Resource
import com.example.treasurehackathon.databinding.ActivityLoginSignupBinding
import com.example.treasurehackathon.ui.home.donatur.HomeDonaturActivity
import com.example.treasurehackathon.ui.splash.SplashActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

@AndroidEntryPoint
class LoginSignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSignupBinding
    private val viewModel: LoginSignupViewModel by viewModels()
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefManager: SharedPreferences =
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

        binding.cbShowPass.setOnClickListener {
            if (binding.cbShowPass.isChecked) {
                binding.etMasukPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etMasukPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        val emailStream = RxTextView.textChanges(binding.etMasukEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailAlert(it)
        }


        val passwordStream = RxTextView.textChanges(binding.etMasukPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showPasswordAlert(it)
        }

        val invalidFieldsStream = Observable.combineLatest(
            emailStream,
            passwordStream,
            BiFunction {emailInvalid: Boolean, passwordInvalid: Boolean ->
                !emailInvalid && !passwordInvalid
            })

        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                binding.btMasuk.isEnabled = true
                binding.btMasuk.alpha=1F
                val email=binding.etMasukEmail.text.toString().trim()
                val password=binding.etMasukPassword.text.toString().trim()
                binding.btMasuk.setOnClickListener {
/*
                    viewModel.loginWithEmailPassword2(email,password).observe(this){
                        when(it){
                            is Resource.Success->{
                                Log.d("TAG",it.data.toString())
                                val s= it.data
                                prefManager.edit().putString(SplashActivity.SESSION_EMAIL,s?.email).apply()
                                prefManager.edit().putString(SplashActivity.SESSION_PASSWORD,password).apply()
                                showLoading(false)
                                val intent: Intent? = when(s?.role){
                                    "yayasan"-> Intent(this, HomeDonaturActivity::class.java).putExtra(
                                        SplashActivity.SESSION_DATA,s)
                                    "donatur"-> Intent(this, HomeDonaturActivity::class.java).putExtra(
                                        SplashActivity.SESSION_DATA,s)
                                    "user"->Intent(this, HomeDonaturActivity::class.java).putExtra(
                                        SplashActivity.SESSION_DATA,s)
                                    else-> null
                                }
                                intent?.let {  startActivity(it)}
                            }
                            is Resource.Loading->{
                                showLoading(true)
                            }

                            is Resource.Error->{
                                showLoading(false)
                                Toast.makeText(this,"Failed to login",Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
*/
                    viewModel.loginWithEmailPassword2(email,password).observe(this){
                        when(it){
                            is Resource.Success->{
                                Log.d("TAG",it.data.toString())
                                val s= it.data
                                prefManager.edit().putString(SplashActivity.SESSION_TOKEN,s?.id).apply()
                                prefManager.edit().putString(SplashActivity.SESSION_EMAIL,s?.email).apply()
                                prefManager.edit().putString(SplashActivity.SESSION_PASSWORD,password).apply()
                                showLoading(false)
                                val intent: Intent? = when(s?.role){
                                    "yayasan"-> Intent(this, HomeDonaturActivity::class.java).putExtra(
                                        SplashActivity.SESSION_DATA,s)
                                    "donatur"-> Intent(this, HomeDonaturActivity::class.java).putExtra(
                                        SplashActivity.SESSION_DATA,s)
                                    "user"->Intent(this, HomeDonaturActivity::class.java).putExtra(
                                        SplashActivity.SESSION_DATA,s)
                                    else-> null
                                }
                                intent?.let {  startActivity(it)}
                            }
                            is Resource.Loading->{
                                showLoading(true)
                            }

                            is Resource.Error->{
                                showLoading(false)
                                deleteSession(prefManager)
                                Toast.makeText(this,"Failed to login",Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            } else {
                binding.btMasuk.isEnabled = false
                binding.btMasuk.alpha=0.7F
            }
        }


    }
    private fun deleteSession(prefManager: SharedPreferences){
        prefManager.edit().putString(SplashActivity.SESSION_EMAIL,"").apply()
        prefManager.edit().putString(SplashActivity.SESSION_PASSWORD,"").apply()
        prefManager.edit().putString(SplashActivity.SESSION_TOKEN,"").apply()
    }
    private fun showPasswordAlert(it: Boolean) {
        binding.ilMasukPassword.helperText = if (it) getString(R.string.pass_not_valid) else ""
    }

    private fun showEmailAlert(it: Boolean) {
        binding.ilMasukEmail.helperText = if (it) getString(R.string.email_not_valid) else ""
    }

    private fun showLoading(isShow:Boolean){
        binding.progressBar.visibility=if(isShow) View.VISIBLE else View.GONE
    }
}