package com.antoniocostadossantos.onlybooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.registerButton.setOnClickListener {
            registerActivity()
        }
        binding.loginButton.setOnClickListener {
            checkFields()
        }

        binding.forgotPassword.setOnClickListener {
            forgotPasswordActivity()
        }
    }

    private fun registerActivity() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun forgotPasswordActivity(){
        val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkFields() {
        when {
            binding.emailInput.text.toString().isEmpty() -> {
                binding.emailInput.error = "Digite o email"
            }

            !emailValidation(binding.emailInput.text.toString()) -> {
                binding.emailInput.error = "Digite um email válido"
            }

            binding.passwordInput.text.toString().isEmpty() -> {
                binding.passwordInput.error = "Digite a senha"
            }

            binding.passwordInput.text.toString().length < 5 -> {
                binding.passwordInput.error = "Senha muito pequena"
            }

            else -> {
                val email: String = binding.emailInput.text.toString()
                val password: String = binding.passwordInput.text.toString()
                login(email, password)
            }
        }
    }

    private fun emailValidation(email: String): Boolean {
        val pattern =
            Regex(
                "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|" +
                        "(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})\$"
            )
        val result = pattern.containsMatchIn(email)
        return result
    }

    private fun login(email: String, password: String) {
        Toast.makeText(this, "Logando...", Toast.LENGTH_SHORT).show()
    }
}