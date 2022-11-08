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
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.show
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

        chooseEbookGif()

        binding.createEbook.setOnClickListener {
            getIdUserForNewEbook()
        }

        binding.viewCreateEbook.setOnClickListener {
            getIdUserForNewEbook()
        }

        binding.createAudiobook.setOnClickListener {
            getIdUserForNewAudioBook()
        }

        binding.viewCreateAudiobook.setOnClickListener {
            getIdUserForNewAudioBook()
        }
    }

    private fun getIdUserForNewAudioBook() {
        val idUser = getDataInCache("id")!!.toInt()
        getUserById(idUser)
        verifyResponseForNewAudioBook()
    }

    private fun verifyResponseForNewAudioBook() {
        userViewModel.userIdResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    val userModel = response.data!!
                    val newAudioBook: AudioBookModel = AudioBookModel(
                        getDataInCache("name")!!,
                        "",
                        "",
                        "",
                        false,
                        "",
                        "",
                        "",
                        0,
                        userModel,
                        "",
                        "",
                        "",
                        0,
                        true,
                        "https://firebasestorage.googleapis.com/v0/b/onlybooks-3a802.appspot.com/o/images%2FsemCapa.png?alt=media&token=31b91efc-2cc4-4352-9740-3606f30cddde"
                    )

                    goToCreateAudioBook(newAudioBook)
                }
                is StateResource.Error -> {
                }
                else -> {
                }
            }
        }

    }

    private fun goToCreateAudioBook(audioBook: AudioBookModel) {

        val createAudioBookFragment = CreateAudioBookFragment(audioBook)

        val bundle = Bundle()
        bundle.putString("newEbook", "newEbook")
        createAudioBookFragment.arguments = bundle

        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, createAudioBookFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getIdUserForNewEbook() {
        val idUser = getDataInCache("id")!!.toInt()
        getUserById(idUser)
        verifyResponseForNewEbook()
    }

    private fun verifyResponseForNewEbook() {
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
                    goToCreateNewEbook(newEbook)
                }
                is StateResource.Error -> {
                }
                else -> {
                }
            }
        }

    }

    private fun goToCreateNewEbook(newEbook: EbookModel) {

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
        verifyResponseForNewEbook()
    }

    private fun drawNumber(): Int = (0..2).random()

    private fun chooseEbookGif() {
        when (drawNumber()) {
            0 -> {
                binding.livro1.show()
            }
            1 -> {
                binding.livro2.show()
            }
            2 -> {
                binding.livro3.show()
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