package com.anureet.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val fragment = MultiplayerFragment().beginTransaction().add(R.id.fragment_holder, fragment) .commit()
//        //removes fragment from the activity
//        supportFragmentManager
//            .beginTransaction()
//            .remove(fragment)
//            .commit()

    }
}