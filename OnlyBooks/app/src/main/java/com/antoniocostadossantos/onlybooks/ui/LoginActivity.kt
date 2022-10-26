package com.antoniocostadossantos.onlybooks.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityLoginBinding
import com.antoniocostadossantos.onlybooks.util.SecurityPreferences
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

//            val base = userViewModel.login.value?.data?.items?.get(0)
//            if (base != null) {
//                val id = base.id.toString()
//                val nome = base.nome.toString()
//                val email = base.email.toString()
//                val password = base.senha.toString()
//                val description = base.descricao.toString()
//                val photo = base.photo.toString()
//                val header = base.header.toString()
//
//
//                val prefs = SecurityPreferences(applicationContext)
//                prefs.setId(id)
//                prefs.setName(nome)
//                prefs.setEmail(email)
//                prefs.setPassword(password)
//                prefs.setDescription(description)
//                prefs.setPhoto(photo)
//                prefs.setHeader(header)
//            }
        }

        binding.forgotPassword.setOnClickListener {
            forgotPasswordActivity()
        }


//        println(userViewModel.login.value?.data?.items)

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

        val preferences = SecurityPreferences(applicationContext)

        preferences.setEmail(email)
        preferences.setPassword(password)

        userViewModel.login(email, password)
        verifyLogin()
    }

    private fun verifyLogin() {
        userViewModel.login.observe(this) { response ->
            when (response) {

                is StateResource.Success -> {

                    val id = response.data?.id.toString()
                    val name = response.data?.nome.toString()
                    val email = response.data?.email.toString()
                    val password = response.data?.senha.toString()
                    val description = response.data?.descricao.toString()
                    val header = response.data?.header.toString()
                    val photo = response.data?.photo.toString()

                    saveInCache("id", id)
                    saveInCache("name", name)
                    saveInCache("email", email)
                    saveInCache("password", password)
                    saveInCache("description", description)
                    saveInCache("header", header)
                    saveInCache("photo", photo)

                    binding.errorLogin.hide()
                    startActivity(Intent(this@LoginActivity, BaseFragmentActivity::class.java))
                    finish()
                }
                is StateResource.Error -> {
                    binding.errorLogin.show()
                }
                else -> {
                    println("erro login activity")
                }
            }

        }
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