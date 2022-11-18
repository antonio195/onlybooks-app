package com.antoniocostadossantos.onlybooks.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        verifyUserCache()
    }

    private fun verifyUserCache() {
        val email = getDataInCache("email")

        if (email.isNullOrEmpty()) {
            goToLogin()
        } else {
            goToBaseFragment()
        }
    }

    private fun goToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, 1500)
    }

    private fun goToBaseFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, BaseFragmentActivity::class.java))
            finish()
        }, 1500)
    }

    private fun getDataInCache(key: String): String? {
        val preferences = getSharedPreferences(
            "UserData",
            MODE_PRIVATE
        )
        return preferences?.getString(key, "")
    }
}