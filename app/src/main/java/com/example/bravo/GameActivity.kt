package com.example.bravo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * A játék 3. és egyben utolsó Layout-ja.
 * A játék fő Logikája található.
 */

class GameActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var players: ArrayList<String>
    private var mode = "normal" // "normal" vagy "drinking"
    private var currentPlayer: String? = null

    private var lastRotation = 0f

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // DB helper
        dbHelper = DatabaseHelper(this)
        dbHelper.createDatabase()

        // Játékosok fogadása az Intentből
        players = intent.getStringArrayListExtra("players") ?: arrayListOf("Players")

        // View-ok
        val tvMode = findViewById<TextView>(R.id.tvMode)
        val tvInstruction = findViewById<TextView>(R.id.tvInstruction)
        val tvResult = findViewById<TextView>(R.id.textView)
        val btnNextPlayer = findViewById<Button>(R.id.btnNextPlayer)
        val btnTruth = findViewById<Button>(R.id.btnTruth)
        val btnDare = findViewById<Button>(R.id.btnDare)
        val switchGameMode = findViewById<SwitchMaterial>(R.id.switchGameMode)
        val pulse = AnimationUtils.loadAnimation(this, R.anim.button_pulse)

        // Csúszka kezdeti állapota
        switchGameMode.isChecked = false  // Alapértelmezetten Normal mód

        // Gombok
        // Következő játékos választása
        btnNextPlayer.setOnClickListener {
            it.startAnimation(pulse)
            spinBottle(players)
        }

        btnTruth.setOnClickListener {
            it.startAnimation(pulse)
            if (currentPlayer == null) {
                tvResult.text = getString(R.string.select_player_first)
                return@setOnClickListener
            }

            val q = dbHelper.getRandomChallenge("truth", mode)
            tvResult.text = getString(R.string.player_question, currentPlayer, q)
            val flash = AnimationUtils.loadAnimation(this, R.anim.button_flashing)
            tvResult.startAnimation(flash)
            val glowPulse = AnimationUtils.loadAnimation(this, R.anim.glow_pulse)
            tvResult.startAnimation(glowPulse)
            currentPlayer = null
        }

        btnDare.setOnClickListener {
            it.startAnimation(pulse)
            if (currentPlayer == null) {
                tvResult.text = getString(R.string.select_player_first)
                return@setOnClickListener
            }

            val q = dbHelper.getRandomChallenge("dare", mode)
            tvResult.text = getString(R.string.player_dare, currentPlayer, q)
            val flash = AnimationUtils.loadAnimation(this, R.anim.button_flashing)
            tvResult.startAnimation(flash)
            val glowPulse = AnimationUtils.loadAnimation(this, R.anim.glow_pulse)
            tvResult.startAnimation(glowPulse)
            currentPlayer = null
        }

        // Csúszka state change listener
        switchGameMode.setOnCheckedChangeListener { _, isChecked ->
            mode = if (isChecked) "drinking" else "normal"
            tvMode.text = getString(
                R.string.game_mode_status,
                if (mode == "normal") getString(R.string.mode_normal)
                else getString(R.string.mode_drinking)
            )
            Toast.makeText(
                this,
                if (isChecked) "Ivós mód aktiválva!" else "Normal mód aktiválva!",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Játékosok
        tvInstruction.text = getString(
            R.string.players_list,
            players.joinToString(", ")
        )

    }

    /**
     * Az üvegpörgetés logikája és a hozzá tartozó animációk.
     */
    private fun spinBottle(players: List<String>) {
        val bottle = findViewById<ImageView>(R.id.ivBottle)
        val ivGlow = findViewById<ImageView>(R.id.ivGlow)

        // Bekapcsolja a neon fényt
        ivGlow.animate().alpha(1f).setDuration(300).start()

        val randomRotation = (360..1800).random().toFloat()

        val anim = RotateAnimation(
            lastRotation,
            lastRotation + randomRotation,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        lastRotation += randomRotation
        anim.duration = 2000
        anim.fillAfter = true

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                // Neon glow kikapcsol
                ivGlow.animate().alpha(0f).setDuration(500).start()

                // Következő játékos kiírása
                val selectedPlayer = players.random()
                showNextPlayer(selectedPlayer)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        bottle.startAnimation(anim)
    }

    @SuppressLint("SetTextI18n")
    private fun showNextPlayer(name: String) {
        currentPlayer = name
        val tvPlayer = findViewById<TextView>(R.id.tvCurrentPlayer)
        tvPlayer.text = getString(R.string.next_player2, name)
    }

}