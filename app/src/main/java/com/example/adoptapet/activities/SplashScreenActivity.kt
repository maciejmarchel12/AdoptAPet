package com.example.adoptapet.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.adoptapet.R


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

        val iv_petIcon = findViewById<ImageView>(R.id.iv_petIcon)

        iv_petIcon.alpha = 0f
        iv_petIcon.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, AdoptListActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }

}