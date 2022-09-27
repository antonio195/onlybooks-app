package com.antoniocostadossantos.onlybooks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ActivityBaseFragmentBinding
import com.antoniocostadossantos.onlybooks.ui.fragments.*

class BaseFragmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseFragmentBinding
    private val navHostFragment = NavHostFragment
    private val navController = NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> startNavigation(HomeFragment())
                R.id.menu_ebooks -> startNavigation(EbookFragment())
                R.id.menu_new_book -> startNavigation(NewBookFragment())
                R.id.menu_audiobook -> startNavigation(AudioBookFragment())
                R.id.menu_profile -> startNavigation(ProfileFragment())
                else -> {
                    startNavigation(HomeFragment())
                }
            }
        }
    }

    private fun startNavigation(fragment: Fragment): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        return true
    }
}