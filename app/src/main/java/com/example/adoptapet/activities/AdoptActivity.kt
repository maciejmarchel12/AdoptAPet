package com.example.adoptapet.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.adoptapet.R
import com.example.adoptapet.models.AdoptModel
import com.example.adoptapet.databinding.ActivityAdoptBinding
import com.example.adoptapet.main.MainApp
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i

@Suppress("DEPRECATION")
class AdoptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdoptBinding
    var adopt = AdoptModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityAdoptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("AdoptAPet Activity started...")

        if (intent.hasExtra("pet_edit")) {
            edit = true
            adopt = intent.extras?.getParcelable("pet_edit")!!
            binding.adoptTitle.setText(adopt.title)
            binding.description.setText(adopt.description)
            binding.btnAdd.setText(R.string.save_adopt)
        }

        binding.btnAdd.setOnClickListener() {
            adopt.title = binding.adoptTitle.text.toString()
            adopt.description = binding.description.text.toString()
            if (adopt.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_adopt_title, Snackbar.LENGTH_LONG)
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

        //Old button event handler

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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}