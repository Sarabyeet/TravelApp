package com.sarabyeet.travelapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.arch.AttractionsViewModel
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    val viewModel: AttractionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.init(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        /** Handles the task of displaying an Alert Dialog for selected attraction facts*/
        viewModel.factsDialogLiveData.observe(this) { attraction ->
            val message = formatFacts(attraction)

            MaterialAlertDialogBuilder(this, R.style.MyAlertDialogTheme)
                .setTitle("${attraction.title} Facts")
                .setMessage(message)
                .setPositiveButton("Ok"
                ) { dialog, _ -> dialog.dismiss() }
                .setCancelable(false)
                .show()
        }
    }


    /**
     * Formats the available facts in an Attraction instance and returns a string for facts
     * */
    private fun formatFacts(attraction: Attraction): String {
        val stringBuilder = StringBuilder("")
        attraction.facts.forEach {
            stringBuilder.append("\u2022 $it")
            stringBuilder.append("\n\n")
        }

        return stringBuilder.toString()
            .substring(0, stringBuilder.toString().lastIndexOf("\n\n"))
    }

    /**
     * Opens a maps Intent for the selected attraction, (Fragment to Activity communication)
     * */
    fun openOnMaps(attraction: Attraction) {
        val locationUri =
            Uri.parse("geo:${attraction.location.latitude},${attraction.location.longitude}?q=${attraction.title}")
        val mapIntent = Intent(Intent.ACTION_VIEW, locationUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

