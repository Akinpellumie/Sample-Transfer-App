package com.interswitchng.interswitchpos.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.utils.hide
import com.interswitchng.interswitchpos.views.activities.MainActivity.StaticSettings.isLoggedIn
import com.interswitchng.smartpos.shared.utilities.reveal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    object StaticSettings {
        var isLoggedIn : Boolean = false
    }
    //val isLoggedIn: Boolean = false   // defining a variable
    //var navHostFragment : supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        NavigationUI.setupWithNavController(bottomNav, navHostFragment!!.findNavController())
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.setNewPin || destination.id == R.id.transactionReceiptFragment || destination.id == R.id.astraTransactionReceiptFragment || destination.id == R.id.login || destination.id == R.id.forgot) {
                bottomNav.hide()
            } else {
                bottomNav.reveal()
            }
        }

    }
}