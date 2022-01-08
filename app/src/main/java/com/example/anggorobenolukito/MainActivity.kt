package com.example.anggorobenolukito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.anggorobenolukito.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        val navController = findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView? = binding?.bottomNavigationView
        navView?.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView?.isVisible = destination.id != R.id.splashFragment

        }
    }
}