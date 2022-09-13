package com.antoniocostadossantos.onlybooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.antoniocostadossantos.onlybooks.databinding.ActivityLoginBinding
import com.antoniocostadossantos.onlybooks.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.loginButton.setOnClickListener {
            loginActivity()
        }

        binding.registerButton.setOnClickListener {
            checkFields()
        }
    }

    private fun loginActivity() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkFields() {
        when {

            binding.userInput.text.toString().isEmpty() -> {
                binding.userInput.error = "Digite o usuário"
            }
            binding.userInput.text.toString().length < 3 -> {
                binding.userInput.error = "Nome de usuário muito pequeno"
            }

            binding.userInput.text.toString().length > 20 -> {
                binding.userInput.error = "Nome de usuário muito grande"
            }

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
                val user: String = binding.userInput.text.toString()
                val email: String = binding.emailInput.text.toString()
                val password: String = binding.passwordInput.text.toString()
                register(user, email, password)
            }
        }
    }

    private fun register(user: String, email: String, password: String) {
        Toast.makeText(this, "Cadastrando...", Toast.LENGTH_SHORT).show()
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
}