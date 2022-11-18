package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentProfileDetailsBinding
import com.antoniocostadossantos.onlybooks.model.UserModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileDetailsFragment(val idUser: Int) : Fragment() {

    private lateinit var binding: FragmentProfileDetailsBinding
    private val userViewModel: UserViewModel by viewModel()
    private lateinit var userModel: UserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserById()

        binding.editProfile.setOnClickListener {
            checkFields()
        }
    }

    private fun checkFields() {
        when {
            binding.inputNickname.text.toString().isEmpty() -> {
                binding.titleNickname.error = "Digite algum nome"
            }
            binding.inputNickname.text.toString().length > 20 -> {
                binding.titleNickname.error = "Apelido muito grande"
            }
            else -> {
                binding.titleNickname.error = null
                binding.titleDescription.error = null
                binding.titleEmail.error = null
                binding.titlePassword.error = null

                userModel.nome = binding.inputNickname.text.toString()
                userModel.descricao = binding.inputDescription.text.toString()
                userModel.email = binding.inputEmail.text.toString()
                userModel.senha = binding.inputPassword.text.toString()

                setUpdateProfile()
            }
        }
    }

    private fun setUpdateProfile() {
        userViewModel.updateUser(idUser, userModel)
        verifyUpdateUser()
    }

    private fun verifyUpdateUser() {
        userViewModel.updateUser.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    toast("Informações alteradas")
                    saveInCache("name", userModel.nome)
                    saveInCache("description", userModel.descricao)
                    saveInCache("email", userModel.email)
                    saveInCache("password", userModel.senha)
                    goToProfileFragment()
                }
                is StateResource.Error -> {
                    toast("Erro ao alterar informações")
                }
                else -> {
                }
            }
        }
    }

    private fun getUserById() {
        userViewModel.getUserById(idUser)
        verifyGetUserId()
    }

    private fun verifyGetUserId() {
        userViewModel.userIdResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    userModel = response.data!!
                    displayData(userModel)
                }
                is StateResource.Error -> {
                }
                else -> {
                }
            }
        }
    }

    private fun displayData(userModel: UserModel) {
        binding.inputNickname.setText(userModel.nome)
        binding.inputDescription.setText(userModel.descricao)
        binding.inputEmail.setText(userModel.email)
        binding.inputPassword.setText(userModel.senha)

    }

    private fun goToProfileFragment() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, ProfileFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun saveInCache(key: String, value: String) {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

}