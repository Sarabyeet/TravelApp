package com.sarabyeet.travelapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.data.AttractionsResponse
import com.sarabyeet.travelapp.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {

    val attractionsList: List<Attraction> by lazy { parseAttractions() }
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    /** Added (freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn") for this in app level gradle file */
    @OptIn(ExperimentalStdlibApi::class)
    private fun parseAttractions(): List<Attraction> {
        val attractionsFile =
            resources.openRawResource(R.raw.sites).bufferedReader().use { it.readText() }

        val adapter = moshi.adapter<AttractionsResponse>()
        return adapter.fromJson(attractionsFile)?.attractions ?: emptyList()
    }
}

