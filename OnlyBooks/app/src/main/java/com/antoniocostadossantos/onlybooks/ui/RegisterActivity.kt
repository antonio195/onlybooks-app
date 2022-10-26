package com.antoniocostadossantos.onlybooks.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.R
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
                register()
            }
        }
    }

    private fun register() {
        val name = binding.userInput.text.toString().lowercase()
        val email = binding.emailInput.text.toString().lowercase()
        val password = binding.passwordInput.text.toString()
        val photo = "https://firebasestorage.googleapis.com/v0/b/onlybooks-3a802.appspot.com/o/images%2Fusuariopadrao.png?alt=media&token=2d0733ab-36cf-40cb-ac3d-b2ab854c7ed3"

        val user = UserModelDTO(name, email, password)
        userViewModel.register(user)
        verifyLogin()
    }

    private fun verifyLogin() {
        userViewModel.register.observe(this) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.errorRegister.hide()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

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

    private fun saveInCache(key: String, value: String) {
        val preferences = getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }
}