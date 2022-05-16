package com.example.weatherapp.auth

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.google.firebase.auth.AuthResult
import com.example.weatherapp.ui.MainActivity
import android.widget.Toast
import com.example.weatherapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth!!.currentUser
        if (user != null) {
            //user is already connected  so we need to redirect him to home page
            updateUI()
        }
        binding.loginToReg.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginProgress.visibility = View.INVISIBLE
        binding.loginnBtn.setOnClickListener {
            binding.loginProgress.visibility = View.VISIBLE
            binding.loginnBtn.visibility = View.INVISIBLE
            val mail = binding.loginMail.text.toString()
            val password = binding.loginPassword.text.toString()
            if (mail.isEmpty() || password.isEmpty()) {
                showMessage("Please Verify All Field")
                binding.loginnBtn.visibility = View.VISIBLE
                binding.loginProgress.visibility = View.INVISIBLE
            } else {
                signIn(mail, password)
            }
        }
    }

    private fun signIn(mail: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    binding.loginProgress.visibility = View.INVISIBLE
                    binding.loginnBtn.visibility = View.VISIBLE
                    updateUI()
                } else {
                    showMessage(task.exception!!.message)
                    binding.loginnBtn.visibility = View.VISIBLE
                    binding.loginProgress.visibility = View.INVISIBLE
                }
            }
    }

    private fun updateUI() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showMessage(text: String?) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
    }
}