package com.example.bravo

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)

        val playButton = findViewById<Button>(R.id.playButton)
        val exitButton = findViewById<Button>(R.id.exitButton)
        val pulse = AnimationUtils.loadAnimation(this, R.anim.button_pulse)

        // "Play" megnyomásakor induljon a névbekérős képernyő
        playButton.setOnClickListener {
            it.startAnimation(pulse)
            val intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }

        // kilépés az appból
        exitButton.setOnClickListener {
            it.startAnimation(pulse)

            finishAffinity()
        }
    }
}
