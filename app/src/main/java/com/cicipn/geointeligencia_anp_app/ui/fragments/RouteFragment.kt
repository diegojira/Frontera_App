package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.cicipn.geointeligencia_anp_app.other.TrackingUtility
import com.cicipn.geointeligencia_anp_app.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_route.*
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cicipn.geointeligencia_anp_app.adapters.RouteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AppSettingsDialog


@AndroidEntryPoint
class RouteFragment: Fragment(R.layout.fragment_route), EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var routeAdapter: RouteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        setupRecyclerView()

        viewModel.routeSortedByDate.observe(viewLifecycleOwner, Observer {
            routeAdapter.submitList(it)
        })

        fab.setOnClickListener{
            findNavController().navigate(R.id.action_routeFragment_to_trackingFragment)
        }
        // Titulo del fragmento
        requireActivity().tvToolbarTitle.text = "Bienvenido"
    }

    private fun setupRecyclerView() = rvRuns.apply {
        routeAdapter = RouteAdapter()
        adapter = routeAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestPermission() {
        if(TrackingUtility.hasLocationPermission(requireContext())){
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                    this,
                    "Necesitas aceptar los permisos para usar ésta aplicación.",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Necesitas aceptar los permisos para usar ésta aplicación.",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}