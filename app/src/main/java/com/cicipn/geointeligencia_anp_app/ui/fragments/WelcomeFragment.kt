package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_SEND1
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_SEND2
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_SEND3
import com.cicipn.geointeligencia_anp_app.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment: Fragment(R.layout.fragment_welcome){

    @Inject
    lateinit var sharedPref: SharedPreferences

    @set:Inject
    var isFirstAppOpen = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!isFirstAppOpen) {
            val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.welcomeFragment, true)
                    .build()
            findNavController().navigate(
                R.id.action_welcomeFragment_to_routeFragment,
                savedInstanceState,
                navOptions
            )
        }

        tvContinue.setOnClickListener{
            val success = writeNicknameToSharedPref()
            if (success) {
                findNavController().navigate(R.id.action_welcomeFragment_to_routeFragment)
            } else {
                Snackbar.make(requireView(), "Por favor ingresa un apodo", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun writeNicknameToSharedPref(): Boolean {
        val nickname = etNickname.text.toString()
        if(nickname.isEmpty()) {
            return false
        }

        sharedPref.edit()
                .putString(KEY_NAME, nickname)
                .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
                .putBoolean(KEY_SEND1, false)
                .putBoolean(KEY_SEND2,false)
                .putBoolean(KEY_SEND3,false)
                .apply()
        val toolBarText = "Bienvenido $nickname"
        requireActivity().tvToolbarTitle.text = toolBarText
        return true
    }
}