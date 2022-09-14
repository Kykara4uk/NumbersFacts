package com.example.numbersfacts.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import com.example.numbersfacts.R
import com.example.numbersfacts.databinding.ActivityMainBinding
import com.example.numbersfacts.presentation.detail.DetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(view)
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { onBackPressed() }
    }

    fun showBackButton(isShow: Boolean) = supportActionBar?.setDisplayHomeAsUpEnabled(isShow)


}