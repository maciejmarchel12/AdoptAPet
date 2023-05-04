package com.example.adoptapet.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adoptapet.R
import com.example.adoptapet.adapters.AdoptAdapter
import com.example.adoptapet.adapters.AdoptListener
import com.example.adoptapet.databinding.ActivityAdoptListBinding
import com.example.adoptapet.main.MainApp
import com.example.adoptapet.models.AdoptModel

class AdoptListActivity : AppCompatActivity(), AdoptListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityAdoptListBinding
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdoptListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        // binding.recyclerView.adapter = AdoptAdapter(app.adoptions)
        binding.recyclerView.adapter = AdoptAdapter(app.adoptions.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AdoptActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, AdoptMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                //notifyItemRangeChanged(0,app.adoptions.size)
                notifyItemRangeChanged(0,app.adoptions.findAll().size)
            }
        }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { }

    override fun onAdoptClick(adopt: AdoptModel, pos : Int) {
        val launcherIntent = Intent(this, AdoptActivity::class.java)
        launcherIntent.putExtra("pet_edit", adopt)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    // Old onAdoptClick
    /*override fun onAdoptClick(adopt: AdoptModel) {
        val launcherIntent = Intent(this, AdoptActivity::class.java)
        getClickResult.launch(launcherIntent)
    }*/

    //Recycler View

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.adoptions.findAll().size)
            }
            else //delete
                if (it.resultCode == 99)
                        (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }
}

