package com.bignerdranch.android.criminalintent

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import java.io.File
import java.lang.IllegalStateException
import java.util.*

private const val ARG_IMAGE = "image"

class ImageDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val inflater = it.layoutInflater
            val dialogView = inflater.inflate(R.layout.fragment_image_dialog, null)

            val imageView: ImageView = dialogView.findViewById(R.id.crime_image) as ImageView

            val photoFile: File = arguments?.getSerializable(ARG_IMAGE) as File
            if (photoFile.exists()) {
                val bitmap = getScaledBitmap(photoFile.path, it)
                imageView.setImageBitmap(bitmap)
            } else {
                imageView.setImageDrawable(null)
            }
            builder.apply {
                setView(dialogView)
                setPositiveButton("ok") { _, _ -> }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity can not null")
    }

    companion object {
        fun newInstance(photoFile: File): DialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_IMAGE, photoFile)
            }

            return ImageDialogFragment().apply {
                arguments = args
            }
        }
    }
}