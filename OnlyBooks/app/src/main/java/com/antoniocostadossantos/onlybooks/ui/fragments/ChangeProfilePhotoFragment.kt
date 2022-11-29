package com.antoniocostadossantos.onlybooks.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.onlybooks.databinding.FragmentChangeProfilePhotoBinding
import com.antoniocostadossantos.onlybooks.model.UserModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.hide
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.UserViewModel
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ChangeProfilePhotoFragment(val idUser: Int) : Fragment() {

    private lateinit var binding: FragmentChangeProfilePhotoBinding
    private lateinit var userModelSuper: UserModel
    private val userViewModel: UserViewModel by viewModel()
    lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeProfilePhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserById(idUser)

        binding.selectImage.setOnClickListener {
            selectImage()
        }

        binding.uploadImage.setOnClickListener {
            if (::imageUri.isInitialized) {
                uploadImage()
                binding.progressBar.show()
            } else {
                toast("Selecione uma foto")
            }

        }
    }

    private fun setUpdateProfile() {
        userViewModel.updateUser(idUser, userModelSuper)
        verifyUpdateUser()
    }

    private fun verifyUpdateUser() {
        userViewModel.updateUser.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.progressBar.hide()
                    saveInCache("photo", userModelSuper.photo)
                }
                is StateResource.Error -> {
                    binding.progressBar.hide()
                    toast("Erro ao alterar informações")
                }
                else -> {
                }
            }
        }
    }

    private fun getUserById(idUser: Int) {
        userViewModel.getUserById(idUser)
        verifyUserResponse()
    }

    private fun verifyUserResponse() {
        userViewModel.userIdResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    val userModel = response.data!!
                    userModelSuper = response.data!!
                    displayData(userModel.photo)
                }
                is StateResource.Error -> {
                }
                else -> {
                }
            }
        }
    }

    private fun displayData(urlPhoto: String) {
        Glide.with(binding.profilePhoto)
            .load(urlPhoto)
            .into(binding.profilePhoto)
    }

    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("profilePictures/$name").downloadUrl.addOnSuccessListener {
                displayData(it.toString())
                userModelSuper.photo = it.toString()
                setUpdateProfile()
            }.addOnFailureListener {
            }
    }

    fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    fun uploadImage() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference =
            FirebaseStorage.getInstance().getReference("profilePictures/$fileName")

        storageReference.putFile(imageUri).addOnSuccessListener {
            toast("Foto alterada")
            getURL(fileName)
        }.addOnFailureListener {
            toast("Falha ao alterar foto")
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                imageUri = data?.data!!
//                binding.ivBannerEbook.setImageURI(imageUri)
            }
        }

    private fun getDataInCache(key: String): String? {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences?.getString(key, "")
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