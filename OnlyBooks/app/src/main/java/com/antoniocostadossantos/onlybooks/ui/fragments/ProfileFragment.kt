package com.antoniocostadossantos.onlybooks.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentProfileBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.ui.adapter.AudiobookItemVerticalAdapter
import com.antoniocostadossantos.onlybooks.ui.adapter.EbookItemVerticalAdapter
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.gone
import com.antoniocostadossantos.onlybooks.util.hide
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.viewModel.AudioBookViewModel
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import com.antoniocostadossantos.onlybooks.viewModel.LibraryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var ebookAdapter: EbookItemVerticalAdapter
    private lateinit var audioBookItemAdapter: AudiobookItemVerticalAdapter
    private val audioBookViewModel: AudioBookViewModel by viewModel()
    private val libraryViewModel: LibraryViewModel by viewModel()
    private val ebookViewModel: EbookViewModel by viewModel()
    private var checked = true
    private lateinit var dialogOptions: AlertDialog
    private lateinit var dialogPhoto: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startDataOnScreen()
        binding.viewMyWorks.gone()
        binding.showMyAudiobooks.alpha = 0.5F

        binding.switchMyWorks.setOnClickListener {
            checkSwitchMyWorks(checked)
            checked = checked != true
        }

        binding.showMyEbooks.setOnClickListener {
            showMyWorksEbook()
        }

        binding.showMyAudiobooks.setOnClickListener {
            showMyWorksAudioBooks()
        }

        binding.showMyAudiobooksInLibrary.setOnClickListener {
            showAudioBookLibrary()
        }
        binding.showMyEbooksInLibrary.setOnClickListener {
            showEbookLibrary()
        }

        binding.settingsButton.setOnClickListener {
            goToSettings()
        }

        binding.profileImage.setOnClickListener {
            showDialogPhoto(getDataInCache("photo")!!)
        }
    }

    private fun startDataOnScreen() {
        displayData()
        getMyEbooks()
        getMyAudioBooks()
        getMyEbookInLibrary()
        getMyAudioBookssInLibrary()
        countEbookForId()
        countAudioBookForId()
    }

    private fun showDialogPhoto(photoURL: String) {
        val build = AlertDialog.Builder(context as FragmentActivity)
        val view = layoutInflater.inflate(R.layout.custom_dialog_photo_view, null)
        build.setView(view)
        val seePhoto = view.findViewById<ImageView>(R.id.photo_image)
        val closePhoto = view.findViewById<ImageView>(R.id.close_photo)

        closePhoto.setOnClickListener {
            dialogPhoto.dismiss()
        }

        dialogPhoto = build.create()
        dialogPhoto.show()

        Glide.with(seePhoto)
            .load(photoURL)
            .into(seePhoto)
    }

    private fun displayData() {
        binding.textName.text = getDataInCache("name")
        binding.textDescription.text = getDataInCache("description")

        val photoURL = getDataInCache("photo")
        val photo = binding.profileImage

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.person_vector)
            .error(R.drawable.ic_baseline_error_24)

        Glide.with(photo)
            .applyDefaultRequestOptions(requestOptions)
            .load(photoURL)
            .into(photo)
    }

    private fun checkSwitchMyWorks(checked: Boolean) {

        when (checked) {
            true -> {
                binding.viewMyWorks.show()
            }
            false -> {
                binding.viewMyWorks.gone()
            }
        }
    }

    private fun showMyWorksEbook() {
        binding.myWorkRecyclerviewEbook.show()
        binding.showMyAudiobooks.alpha = 0.7F
        binding.showMyEbooks.alpha = 1F
        binding.myWorkRecyclerviewAudiobook.hide()
    }

    private fun showMyWorksAudioBooks() {
        binding.myWorkRecyclerviewAudiobook.show()
        binding.showMyEbooks.alpha = 0.7F
        binding.showMyAudiobooks.alpha = 1F
        binding.myWorkRecyclerviewEbook.hide()
    }

    private fun setupEbooks(list: List<EbookModel>) {
        this.ebookAdapter = EbookItemVerticalAdapter(binding.root.context)

        val recyclerView = binding.myWorkRecyclerviewEbook

        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)

        recyclerView.adapter = this.ebookAdapter
        this.ebookAdapter.setList(list.sortedBy {
            it.idEbook
        })
    }

    private fun setupAudioBooks(list: List<AudioBookModel>) {
        this.audioBookItemAdapter = AudiobookItemVerticalAdapter(binding.root.context)

        val recyclerView = binding.myWorkRecyclerviewAudiobook

        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)

        recyclerView.adapter = this.audioBookItemAdapter

        audioBookViewModel.suggestions.observe(viewLifecycleOwner) {
            this.audioBookItemAdapter.setList(list.sortedBy {
                it.idAudioBook
            })
        }
    }

    private fun getMyEbooks() {
        ebookViewModel.getMyEbooks(getDataInCache("id")!!.toInt())
        verifyMyEbooks()
    }

    private fun verifyMyEbooks() {
        ebookViewModel.myEbooks.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    setupEbooks(response.data!!)
                }
                is StateResource.Error -> {
                }
                else -> {
                    println(response)
                }
            }
        }
    }

    private fun getMyAudioBooks() {
        audioBookViewModel.getMyEbooks(getDataInCache("id")!!.toInt())
        verifyMyAudioBooks()
    }

    private fun verifyMyAudioBooks() {
        audioBookViewModel.myAudioBooks.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    setupAudioBooks(response.data!!)
                }
                is StateResource.Error -> {
                }
                else -> {
                    println(response)
                }
            }
        }
    }

    private fun showEbookLibrary() {
        binding.showMyEbooksInLibrary.alpha = 1F
        binding.showMyAudiobooksInLibrary.alpha = 0.7F
        binding.myWorkRecyclerviewEbookLibrabry.show()
        binding.myWorkRecyclerviewAudiobookLibrabry.gone()
    }

    private fun showAudioBookLibrary() {
        binding.showMyEbooksInLibrary.alpha = 0.7F
        binding.showMyAudiobooksInLibrary.alpha = 1F
        binding.myWorkRecyclerviewEbookLibrabry.gone()
        binding.myWorkRecyclerviewAudiobookLibrabry.show()
    }

    private fun setupAudioBookLibrary(list: List<AudioBookModel>) {
        this.audioBookItemAdapter = AudiobookItemVerticalAdapter(binding.root.context)
        val recyclerView = binding.myWorkRecyclerviewAudiobookLibrabry
        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)
        recyclerView.adapter = this.audioBookItemAdapter
        this.audioBookItemAdapter.setList(list)
    }

    private fun getMyAudioBookssInLibrary() {
        audioBookViewModel.getMyAudioBookssInLibrary(getDataInCache("id")!!.toInt())
        verifyMyAudioBooksInLibrary()
    }

    private fun verifyMyAudioBooksInLibrary() {
        audioBookViewModel.myAudioBooksInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    setupAudioBookLibrary(response.data!!)
                }
                is StateResource.Error -> {
                }
                else -> {
                    println(response)
                }
            }
        }
    }

    private fun setupEbookLibrary(list: List<EbookModel>) {
        this.ebookAdapter = EbookItemVerticalAdapter(binding.root.context)
        val recyclerView = binding.myWorkRecyclerviewEbookLibrabry
        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)
        recyclerView.adapter = this.ebookAdapter
        this.ebookAdapter.setList(list)
    }

    private fun getMyEbookInLibrary() {
        ebookViewModel.getMyEbooksInLibrary(getDataInCache("id")!!.toInt())
        verifyMyEbooksInLibrary()
    }

    private fun verifyMyEbooksInLibrary() {
        ebookViewModel.myEbooksInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    setupEbookLibrary(response.data!!)
                }
                is StateResource.Error -> {
                }
                else -> {
                    println(response)
                }
            }
        }
    }

    private fun countEbookForId() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.countEbookForId(idUser)
        verifyCountEbookForId()
    }

    private fun verifyCountEbookForId() {
        libraryViewModel.countEbookForId.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.qntsEbooks.text = response.data!!.toString()
                }
                is StateResource.Error -> {
                }
                else -> {
                    println(response)
                }
            }
        }
    }

    private fun countAudioBookForId() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.countAudioBookForId(idUser)
        verifyCountAudioBookForId()
    }

    private fun verifyCountAudioBookForId() {
        libraryViewModel.countAudioBookForId.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.qntsAudiobooks.text = response.data!!.toString()
                }
                is StateResource.Error -> {
                }
                is StateResource.Loading -> {
                }

            }
        }
    }

    private fun goToSettings() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, SettingsFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getDataInCache(key: String): String? {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences?.getString(key, "")
    }

}