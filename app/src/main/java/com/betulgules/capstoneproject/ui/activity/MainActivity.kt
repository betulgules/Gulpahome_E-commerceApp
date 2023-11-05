package com.betulgules.capstoneproject.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.betulgules.capstoneproject.MainApplication
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.databinding.ActivityMainBinding



class MainActivity() : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MainApplication.provideRetrofit(this)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navController
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.searchFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.cartFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.favoritesFragment -> binding.bottomNavigationView.visibility = View.VISIBLE

                else -> { binding.bottomNavigationView.visibility = View.GONE
                }
            }
        }

    }
}


