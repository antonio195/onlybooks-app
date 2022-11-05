package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentNewBookBinding
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewBookFragment : Fragment() {

    private lateinit var binding: FragmentNewBookBinding

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createEbook.setOnClickListener {
            getIdUser()
        }

        binding.viewCreateEbook.setOnClickListener {
            getIdUser()
        }
    }

    private fun getIdUser() {
        val idUser = getDataInCache("id")!!.toInt()
        getUserById(idUser)
        verifyResponse()
    }

    private fun goToCreateEbook(newEbook: EbookModel) {

        val createEbookFragment = CreateEbookFragment(newEbook)

        val bundle = Bundle()
        bundle.putString("newEbook", "newEbook")
        createEbookFragment.arguments = bundle

        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, createEbookFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getUserById(idUser: Int) {
        userViewModel.getUserById(idUser)
        verifyResponse()
    }

    private fun verifyResponse() {
        userViewModel.userIdResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    val userModel = response.data!!
                    val newEbook: EbookModel = EbookModel(
                        "",
                        "",
                        "",
                        "",
                        true,
                        "",
                        "",
                        "",
                        0,
                        userModel,
                        "",
                        0.0,
                        0,
                        true,
                        "https://firebasestorage.googleapis.com/v0/b/onlybooks-3a802.appspot.com/o/images%2FsemCapa.png?alt=media&token=31b91efc-2cc4-4352-9740-3606f30cddde"
                    )
                    goToCreateEbook(newEbook)
                }
                is StateResource.Error -> {
                }
                else -> {
                }
            }
        }

    }

    private fun getDataInCache(key: String): String? {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences?.getString(key, "")
    }

}