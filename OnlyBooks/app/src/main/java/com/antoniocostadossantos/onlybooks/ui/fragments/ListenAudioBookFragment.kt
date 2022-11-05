package com.antoniocostadossantos.onlybooks.ui.fragments

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.onlybooks.databinding.FragmentListenAudioBookBinding
import com.antoniocostadossantos.onlybooks.util.toast
import java.io.IOException


class ListenAudioBookFragment : Fragment() {

    private lateinit var binding: FragmentListenAudioBookBinding

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListenAudioBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idBtnPlay.setOnClickListener {
            playAudio()
        }
        binding.idBtnPause.setOnClickListener {
            pauseAudio()
        }


    }

    private fun pauseAudio() {
        if (mediaPlayer.isPlaying()) {
            // pausing the media player if media player
            // is playing we are calling below line to
            // stop our media player.
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            // below line is to display a message
            // when media player is paused.
            toast("Audio has been paused")
        } else {
            // this method is called when media
            // player is not playing.
            toast("Audio has not played")
        }
    }


    private fun playAudio() {
        val audioUrl =
            "https://firebasestorage.googleapis.com/v0/b/onlybooks-3a802.appspot.com/o/audios%2FDuzz-ASAS.mp3?alt=media&token=915dd2d5-7f91-479f-a394-c892b3dc7e7f"

        mediaPlayer = MediaPlayer()

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        toast("Audio started playing..")
    }
}