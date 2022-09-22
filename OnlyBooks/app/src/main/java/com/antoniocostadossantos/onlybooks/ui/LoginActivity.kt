package com.antoniocostadossantos.onlybooks.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityLoginBinding
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.hide
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val userViewModel: UserViewModel by viewModel()

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

        verifyLogin()
    }

    private fun registerActivity() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun forgotPasswordActivity() {
        val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
        startActivity(intent)
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
                login()
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

    private fun login() {
        val email: String = binding.emailInput.text.toString()
        val password: String = binding.passwordInput.text.toString()

        userViewModel.login(email, password)
        verifyLogin()
    }

    private fun verifyLogin() {
        userViewModel.login.observe(this) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.errorLogin.hide()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
                is StateResource.Error ->{
                    binding.errorLogin.show()
                }
                else -> {
                    println("erro login activity")
                }
            }

        }
    }
}