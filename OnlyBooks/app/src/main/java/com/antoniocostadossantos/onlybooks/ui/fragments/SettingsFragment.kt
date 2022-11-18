package com.antoniocostadossantos.onlybooks.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentSettingsBinding
import com.antoniocostadossantos.onlybooks.ui.LoginActivity
import com.antoniocostadossantos.onlybooks.util.toast


class SettingsFragment : Fragment() {

    private lateinit var dialog: AlertDialog

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfile.setOnClickListener {
            goToProfileDetails()
        }

        binding.logof.setOnClickListener {
            showDialogLogof()
        }
    }

    private fun goToProfileDetails() {

        val id = getDataInCache("id")!!.toInt()
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, ProfileDetailsFragment(id))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showDialogLogof() {
        val build = AlertDialog.Builder(context as FragmentActivity)
        val view = layoutInflater.inflate(R.layout.custom_dialog_logof, null)
        build.setView(view)

        val no = view.findViewById<Button>(R.id.btn_no)
        val yes = view.findViewById<Button>(R.id.btn_yes)

        no.setOnClickListener {
            dialog.dismiss()
        }

        yes.setOnClickListener {
            toast("Saindo...")
            clearUserCache()
        }

        dialog = build.create()
        dialog.show()

    }

    private fun clearUserCache() {
        saveInCache("id", "")
        saveInCache("name", "")
        saveInCache("email", "")
        saveInCache("password", "")
        saveInCache("description", "")
        saveInCache("header", "")
        saveInCache("photo", "")
        goToLogin()
    }

    private fun goToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
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


    private fun getDataInCache(key: String): String? {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences?.getString(key, "")
    }
}