package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_admin__panel.*

class ADMIN_PANEL : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin__panel)

        admin_BTN_add_animal.setOnClickListener {
            startActivity(Intent(this, ADMIN_ADD_IMAGES::class.java))

        }

        admin_BTN_check_animal.setOnClickListener {
            startActivity(Intent(this, ADMIN_ANIMALS::class.java))

        }



        admin_BTN_quit.setOnClickListener {
            finish()
        }
    }
}
