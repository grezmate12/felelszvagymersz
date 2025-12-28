package com.example.bravo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PlayersActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var etPlayerName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnStart: Button

    private val players = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        listView = findViewById(R.id.lvPlayers)
        etPlayerName = findViewById(R.id.etPlayerName)
        btnAdd = findViewById(R.id.btnAddPlayer)
        btnStart = findViewById(R.id.btnStartGame)

        // Custom adapter: override getView() hogy a delete gomb minden sorban működjön
        adapter = object : ArrayAdapter<String>(this, R.layout.item_player, R.id.tvPlayerName, players) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_player, parent, false)

                val tvName = view.findViewById<TextView>(R.id.tvPlayerName)
                val btnDelete = view.findViewById<Button>(R.id.btnDelete)

                val name = players[position]
                tvName.text = name

                // Törlés gomb
                btnDelete.setOnClickListener {
                    // Animáció betöltése
                    val anim = AnimationUtils.loadAnimation(context, R.anim.player_remove)
                    view.startAnimation(anim)

                    // Késleltetve távolít el, hogy az animáció lefusson
                    Handler(Looper.getMainLooper()).postDelayed({
                        // Ellenőrzés: pozíció még érvényes?
                        if (position >= 0 && position < players.size) {
                            players.removeAt(position)
                            notifyDataSetChanged()
                        }
                    }, 250) // egyezzen az animáció hosszával
                }

                return view
            }
        }

        listView.adapter = adapter

        // Hozzáadás
        btnAdd.setOnClickListener {
            val name = etPlayerName.text.toString().trim()
            if (name.isNotEmpty()) {
                players.add(name)
                adapter.notifyDataSetChanged()
                etPlayerName.text.clear()

                // scroll to bottom
                listView.smoothScrollToPosition(players.size - 1)
            } else {
                Toast.makeText(this, "Adj meg egy nevet!", Toast.LENGTH_SHORT).show()
            }
        }

        // Indítás
        btnStart.setOnClickListener {
            if (players.size < 2) {
                Toast.makeText(this, "Legalább 2 játékos kell!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, GameActivity::class.java)
            intent.putStringArrayListExtra("players", players)
            startActivity(intent)
        }
    }
}
