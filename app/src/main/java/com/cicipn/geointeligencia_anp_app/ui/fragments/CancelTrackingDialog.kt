package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cicipn.geointeligencia_anp_app.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelTrackingDialog: DialogFragment () {

    private var yesListener: (() -> Unit)? = null

    fun setYesListener(listener: () -> Unit) {
        yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("¿Cancelar el registro de la ruta?")
            .setMessage("¿Estás seguro que quieres cancelar el registro de la ruta?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Sí") { _, _ ->
                yesListener?.let { yes ->
                    yes() }
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }
}