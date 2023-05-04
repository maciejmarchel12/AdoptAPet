package com.example.adoptapet.helpers

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.activity.result.ActivityResultLauncher
import com.example.adoptapet.R

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>, context: Context) {

    var imagePickerTargetIntent = Intent()
    //var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    imagePickerTargetIntent.action = Intent.ACTION_OPEN_DOCUMENT
    imagePickerTargetIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    imagePickerTargetIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    imagePickerTargetIntent.type = "image/*"
    imagePickerTargetIntent = Intent.createChooser(imagePickerTargetIntent,
                                                    context.getString(R.string.select_pet_image))
    intentLauncher.launch(imagePickerTargetIntent)
}