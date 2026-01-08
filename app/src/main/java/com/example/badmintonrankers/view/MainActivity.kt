package com.example.badmintonrankers.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.badmintonrankers.R
import com.example.badmintonrankers.databinding.ActivityMainBinding
import com.example.badmintonrankers.view.fragments.History
import com.example.badmintonrankers.view.fragments.Leaderboard
import com.example.badmintonrankers.view.fragments.Member

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homeFrameLayout, Leaderboard())
        transaction.commit()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.history -> replaceFragment(History())
                R.id.member -> replaceFragment(Member())
                 else->{}
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homeFrameLayout,fragment)
        transaction.commit()
    }
}