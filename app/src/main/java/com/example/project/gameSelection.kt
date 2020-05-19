package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_selection.*

class gameSelection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_selection)

        gameselectBTN_quit.setOnClickListener {
            finish()
        }

        gameselectBTN_bingo.setOnClickListener {
//            startActivity(Intent(@gameSekection.this))
            startActivity(Intent(this, bingoPage::class.java))

        }
    }
}
