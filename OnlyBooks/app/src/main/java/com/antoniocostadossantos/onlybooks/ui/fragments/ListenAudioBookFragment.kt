package com.antoniocostadossantos.onlybooks.ui.fragments

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentListenAudioBookBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.viewModel.ChapterViewModel
import com.bumptech.glide.Glide
import com.google.android.material.slider.Slider
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class ListenAudioBookFragment(val audiobook: AudioBookModel) : Fragment() {

    private lateinit var binding: FragmentListenAudioBookBinding
    private lateinit var mediaPlayer: MediaPlayer
    private val chapterViewModel: ChapterViewModel by viewModel()
    private var URLAudioBook: String = ""
    private var playing: Boolean = false
    private var paused: Boolean = false
    private var currentPosition = 0F
    private var duration = 0
    private var newPosition: Int = 0
    private var changedPosition: Boolean = false

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListenAudioBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayData()
        getUrl()

        binding.progressBar.addOnChangeListener(object : Slider.OnChangeListener {
            override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                mediaPlayer.pause()
                mediaPlayer.seekTo(value.toInt())
                newPosition = slider.value.toInt()
                changedPosition = true
                mediaPlayer.start()
            }

        })

        binding.btnPlayPause.setOnClickListener {
            if (playing) {
                playing = false
                paused = false
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_baseline_play_circle_filled_24)
                pauseAudio()
            } else {
                playing = true
                paused = true
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_filled_24)
                playAudio()
            }
        }

        binding.btnBack10.setOnClickListener {
            backTeenSeconds()
        }

        binding.btnForward10.setOnClickListener {
            forwardTeenSeconds()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable!!)
        mediaPlayer.stop()
    }

    private fun backTeenSeconds() {
        mediaPlayer.pause()
        var seconds = TimeUnit.MILLISECONDS.toSeconds(currentPosition.toLong())
        seconds -= 10
        var miliseconds = TimeUnit.SECONDS.toMillis(seconds)
        mediaPlayer.seekTo(miliseconds.toInt())
        currentPosition = miliseconds.toFloat()
        mediaPlayer.start()
    }

    private fun forwardTeenSeconds() {
        mediaPlayer.pause()
        var seconds = TimeUnit.MILLISECONDS.toSeconds(currentPosition.toLong())
        seconds += 10
        var miliseconds = TimeUnit.SECONDS.toMillis(seconds)
        mediaPlayer.seekTo(miliseconds.toInt())
        currentPosition = miliseconds.toFloat()
        mediaPlayer.start()
    }

    private fun prepareAudio() {
        mediaPlayer = MediaPlayer()
        val audioUrl = URLAudioBook
        duration = mediaPlayer.duration
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource(audioUrl)
        mediaPlayer.prepare()
        currentPosition = mediaPlayer.currentPosition.toFloat()
        binding.finalTime.text = SimpleDateFormat("mm:ss").format(mediaPlayer.duration)
    }

    private fun pauseAudio() {
        mediaPlayer.pause()
    }

    private fun playAudio() {
        try {
            mediaPlayer.start()
            binding.progressBar.valueFrom = currentPosition
            binding.progressBar.value = currentPosition
            binding.progressBar.valueTo = mediaPlayer.duration.toFloat()
//            checkPosition()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

//    private fun checkPosition() {
//        if (playing) {
//            handler.postDelayed(Runnable {
//                handler.postDelayed(runnable!!, delay.toLong())
//                currentPosition = mediaPlayer.currentPosition.toFloat()
//                binding.progressBar.value = currentPosition
//                binding.currentTime.text = SimpleDateFormat("mm:ss").format(currentPosition)
//            }.also { runnable = it }, delay.toLong())
//        }
//    }

    private fun displayData() {
        binding.audiobookTitle.text = audiobook.nameAudioBook
        binding.audiobookAuthor.text = audiobook.authorAudioBook
        val cover = binding.audiobookCover
        Glide.with(cover)
            .load(audiobook.urlAudioBook)
            .into(cover)
    }

    private fun getUrl() {
        chapterViewModel.getChapterAudioBook(audiobook.idAudioBook)
        checkUrlResponse()
    }

    private fun checkUrlResponse() {
        chapterViewModel.getChapterAudioBook.observe(viewLifecycleOwner) { response ->
            when (response) {

                is StateResource.Success -> {
                    println(response.data?.get(0)!!.urlAudio)
                    URLAudioBook = response.data?.get(0)!!.urlAudio
                    prepareAudio()
                }
                is StateResource.Error -> {
                }
                else -> {
                    println("AudioBookDetailsFragment linha 131")
                }
            }

        }
    }
}