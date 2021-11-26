package uz.gita.newdrinkwater.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import uz.gita.newdrinkwater.R
import uz.gita.newdrinkwater.database.SharedPref

class MainActivity : AppCompatActivity() {
    private val pref = SharedPref.getSharedPref()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHost.navController.navInflater.inflate(R.navigation.nav_graph)
        val isStart = pref.isStart
        if (isStart) navController.startDestination = R.id.menuFragment
        else navController.startDestination = R.id.introductionFragment
        navHost.navController.graph = navController

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}