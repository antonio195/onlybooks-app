package com.antoniocostadossantos.onlybooks.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityRegisterBinding
import com.antoniocostadossantos.onlybooks.model.UserModelDTO
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.hide
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            goToLogin()
        }

        binding.registerButton.setOnClickListener {
            checkFields()
        }
    }

    private fun goToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkFields() {
        when {
            binding.userInput.text.toString().isEmpty() -> {
                binding.titleNickname.error = "Digite o usuário"
            }
            binding.userInput.text.toString().length < 3 -> {
                binding.titleNickname.error = "Nome de usuário muito pequeno"
            }

            binding.userInput.text.toString().length > 20 -> {
                binding.titleNickname.error = "Nome de usuário muito grande"
            }

            binding.emailInput.text.toString().isEmpty() -> {
                binding.titleEmail.error = "Digite o email"
            }

            !emailValidation(binding.emailInput.text.toString()) -> {
                binding.titleEmail.error = "Digite um email válido"
            }

            binding.passwordInput.text.toString().isEmpty() -> {
                binding.titlePassword.error = "Digite a senha"
            }

            binding.passwordInput.text.toString().length < 5 -> {
                binding.titlePassword.error = "Senha muito pequena"
            }

            else -> {
                binding.titleNickname.error = null
                binding.titleEmail.error = null
                binding.titlePassword.error = null
                register()
            }
        }
    }

    private fun register() {
        val name = binding.userInput.text.toString().lowercase()
        val email = binding.emailInput.text.toString().lowercase()
        val password = binding.passwordInput.text.toString()

        val user = UserModelDTO(name, email, password)
        userViewModel.register(user)
        verifyLogin()
    }

    private fun verifyLogin() {
        userViewModel.register.observe(this) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.errorRegister.hide()
                    goToLogin()
                }
                is StateResource.Error -> {
                    binding.errorRegister.show()
                }
                else -> {
                    println(response)
                }
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
}