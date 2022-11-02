package com.antoniocostadossantos.onlybooks.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityForgotPasswordBinding
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.hide
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    private val userViewModel: UserViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendEmail.setOnClickListener {
            checkFields()
        }

        binding.loginButton.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToLogin() {
        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
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
            else -> {
                getNewPassword()
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

    private fun getNewPassword() {
        val email = binding.emailInput.text.toString().lowercase()
        userViewModel.recoveryPassword(email)
        verifyRecovery()
    }

    private fun verifyRecovery() {
        userViewModel.recoveryPassword.observe(this) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.errorRecovery.hide()
                    startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))

                }
                is StateResource.Error -> {
                    binding.errorRecovery.show()
                }
                else -> {
                }
            }
        }
    }
}