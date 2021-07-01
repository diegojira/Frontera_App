package com.cicipn.geointeligencia_anp_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants
import com.cicipn.geointeligencia_anp_app.ui.fragments.IOnBackPressed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToTrackingFragmentIfNeeded(intent)

        setSupportActionBar(toolbar)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        bottomNavigationView.setOnNavigationItemReselectedListener{/* NO-OP */ }

        navHostFragment.findNavController()
                .addOnDestinationChangedListener{ _, destination, _->
                    when(destination.id) {
                        // Settings to set the visibility of the navigation bar, in other fragments that doesn't belong in this list the navigation will disappear
                        R.id.shareFragment, R.id.routeFragment, R.id.mapFragment, R.id.pollFragment ->
                            bottomNavigationView.visibility = View.VISIBLE
                        else -> bottomNavigationView.visibility = View.GONE
                    }
                }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?){
        if(intent?.action == Constants.ACTION_SHOW_TRACKING_FRAGMENT) {
            navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }

    //Inhabilitara el boton de regresar
    override fun onBackPressed() {
        val fragment1 =
            this.supportFragmentManager.findFragmentById(R.id.shareFragment)
        (fragment1 as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
        val fragment2 =
            this.supportFragmentManager.findFragmentById(R.id.routeFragment)
        (fragment2 as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
        val fragment3 =
            this.supportFragmentManager.findFragmentById(R.id.mapFragment)
        (fragment3 as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }

        val fragment4 =
            this.supportFragmentManager.findFragmentById(R.id.pollFragment)
        (fragment4 as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }
}
