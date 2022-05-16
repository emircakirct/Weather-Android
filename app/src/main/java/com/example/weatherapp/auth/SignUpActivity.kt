package com.example.weatherapp.auth

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.google.firebase.auth.AuthResult
import android.widget.Toast
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.Task

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivitySignupBinding? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.signInText.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding!!.regProgressBar.visibility = View.INVISIBLE
        mAuth = FirebaseAuth.getInstance()
        binding!!.regBtn.setOnClickListener(this)
    }

    private fun createUserAccount(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {

                    // user account created successfully
                    Toast.makeText(
                        applicationContext,
                        "you have signup successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    updateUI()
                } else {
                    // account creation failed
                    Toast.makeText(
                        this, "account creation failed" +
                                task.exception!!.message, Toast.LENGTH_SHORT
                    ).show()
                    binding!!.regBtn.visibility = View.VISIBLE
                    binding!!.regProgressBar.visibility = View.INVISIBLE
                }
            }
    }

    private fun updateUI() {
        val homeActivity = Intent(applicationContext, MainActivity::class.java)
        startActivity(homeActivity)
        finish()
    }

    override fun onClick(view: View) {
        binding!!.regBtn.visibility = View.INVISIBLE
        binding!!.regProgressBar.visibility = View.VISIBLE
        val email = binding!!.regMail.text.toString()
        val password = binding!!.regPassword.text.toString()
        val password2 = binding!!.regPassword2.text.toString()
        val name = binding!!.regName.text.toString()
        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || password != password2) {

            // something goes wrong : all fields must be filled
            // we need to display an error message
            Toast.makeText(this, "Please Verify all fields", Toast.LENGTH_SHORT).show()
            binding!!.regBtn.visibility = View.VISIBLE
            binding!!.regProgressBar.visibility = View.INVISIBLE
        } else {
            createUserAccount(email, password)
        }
    }
}