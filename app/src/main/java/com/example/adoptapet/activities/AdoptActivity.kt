package com.example.adoptapet.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adoptapet.R
import com.example.adoptapet.models.AdoptModel
import com.example.adoptapet.databinding.ActivityAdoptBinding
import com.example.adoptapet.helpers.showImagePicker
import com.example.adoptapet.main.MainApp
import com.example.adoptapet.models.Location
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.i
import java.text.DateFormat
import java.util.Date

@Suppress("DEPRECATION") // Had to use this as Parcelable was being deprecated
class AdoptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdoptBinding
    var adopt = AdoptModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false
   // var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        edit = false

        binding = ActivityAdoptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("AdoptAPet Activity started...") //Logcat message

        //For Editing the Listing

        if (intent.hasExtra("pet_edit")) {
            edit = true
            adopt = intent.extras?.getParcelable("pet_edit")!!
            binding.adoptTitle.setText(adopt.title)
            binding.description.setText(adopt.description)
            binding.email.setText(adopt.email)
            binding.petAge.setText(adopt.petAge.toString())
            binding.availableDate.setText(adopt.availableDate)
            binding.btnAdd.setText(R.string.save_adopt)
            Picasso.get()
                .load(adopt.image)
                .into(binding.petImage)
            if (adopt.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_pet_image)
            }
        }

        //Button for adding listing

        binding.btnAdd.setOnClickListener() {
            adopt.title = binding.adoptTitle.text.toString()
            adopt.description = binding.description.text.toString()
            adopt.email = binding.email.text.toString()
            adopt.petAge = Integer.parseInt(binding.petAge.text.toString())
            adopt.availableDate = binding.availableDate.text.toString()

            //validation attempt
            //BUG FOUND FOR adopt.petAge, Crashes due to it being an Int, will need to fix for CA2

            if (adopt.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_adopt_title, Snackbar.LENGTH_LONG)
                    .show()
            } else if (adopt.description.isEmpty()) {
                Snackbar.make(it,R.string.enter_adopt_description, Snackbar.LENGTH_LONG)
                    .show()
            } else if (adopt.email.isEmpty()) {
                Snackbar.make(it,R.string.enter_adopt_email, Snackbar.LENGTH_LONG)
                    .show()
            } else if (adopt.petAge.toString().isEmpty()) {
                Snackbar.make(it,R.string.enter_adopt_age, Snackbar.LENGTH_LONG)
                    .show()
            } else if (adopt.availableDate.isEmpty()) {
                Snackbar.make(it,R.string.enter_adopt_date, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.adoptions.update(adopt.copy())
                } else {
                    app.adoptions.create(adopt.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

            //Old button event handler adopt.petAge.toString().isEmpty()

            /*binding.btnAdd.setOnClickListener() {
            adopt.title = binding.adoptTitle.text.toString()
            adopt.description = binding.description.text.toString()
            if (adopt.title.isNotEmpty()) {
                app.adoptions.add(adopt.copy())
                i("add button pressed: $adopt")
                for (i in app.adoptions.indices)
                    { i("Adoption[$i]:${this.app.adoptions[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }*/

        //Button for adding Images

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher,this)
        }

        binding.adoptLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (adopt.zoom != 0f) {
                location.lat = adopt.lat
                location.lng = adopt.lng
                location.zoom = adopt.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.adoptions.delete(adopt)
                setResult(RESULT_OK)
                finish()
            }
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }

    // Image Picker Callback
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Received Result ${result.data!!.data}")
                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            adopt.image = image

                            Picasso.get()
                                   .load(adopt.image)
                                   .into(binding.petImage)
                            binding.chooseImage.setText(R.string.change_pet_image)
                        } // end of if statement
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Received Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            adopt.lat = location.lat
                            adopt.lng = location.lng
                            adopt.zoom = location.zoom
                        } // end of if statement
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}