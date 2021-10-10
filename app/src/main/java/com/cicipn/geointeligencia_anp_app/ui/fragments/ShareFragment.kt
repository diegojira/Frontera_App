package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
import com.cicipn.geointeligencia_anp_app.other.TrackingUtility
import com.cicipn.geointeligencia_anp_app.ui.viewmodels.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_share.*
import javax.inject.Inject
import kotlin.math.round


@AndroidEntryPoint
class ShareFragment: Fragment(R.layout.fragment_share){

    companion object{
        lateinit var uri : Uri
    }
    @Inject
    lateinit var sharedPref: SharedPreferences
    //lateinit var uri: Uri
    lateinit var foto_gallery : ImageView

    private val viewModel: ShareViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()

        // Fragment title
        requireActivity().tvToolbarTitle.text = "Comparte ${sharedPref.getString(KEY_NAME, "en redes")}"

        btnShareTwitter.setOnClickListener{
/*            var hashTag = ShareHashtag.Builder().setHashtag("#GeointeligenciaANP").build()

            var shareContent = ShareLinkContent.Builder().setQuote("¡He aportado ${tvTotalDistance.text.toString()} \n durante ${tvTotalTime.text.toString()}!")
                .setShareHashtag(hashTag)
                .setContentUrl(Uri.parse("https://piiglab.org/"))
                .build()
            ShareDialog.show(activity, shareContent)*/

            //uri = Uri.parse("https://piiglab.org/")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "#Cuxtal ¡He rrecorido ${tvTotalDistance.text.toString()} durante ${tvTotalTime.text.toString()}!")
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.type = "image/jpeg"
            intent.setPackage("com.twitter.android")
            startActivity(intent)


        }

        btnTakePhoto.setOnClickListener{
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            var uri = data?.data
            if (uri != null) {
                ShareFragment.uri = uri
            }
            ivPhotoToShare.setImageURI(uri)
        }
    }



    // To get fresh data
    private fun subscribeToObservers(){
        viewModel.totalTimeRoute.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totaltimeRoute = TrackingUtility.getFormattedStopWatchTime(it)
                tvTotalTime.text = totaltimeRoute
            }
        })

        viewModel.totalDistance.observe(viewLifecycleOwner,{
            it?.let {
                val km = it / 1000f
                val totalDistance = round(km * 10f) / 10f
                val totaldistanceString = "${totalDistance} km"
                tvTotalDistance.text = totaldistanceString
            }
        })
    }


}