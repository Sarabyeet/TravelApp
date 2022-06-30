package com.sarabyeet.travelapp.arch

import android.content.Context
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.data.AttractionsResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class AttractionsRepository {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /** Added (freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn") for this in app level gradle file */
    @OptIn(ExperimentalStdlibApi::class)
    fun parseAttractions(context: Context): List<Attraction> {
        val attractionsFile =
            context.resources.openRawResource(R.raw.sites).bufferedReader().use { it.readText() }

        val adapter = moshi.adapter<AttractionsResponse>()
        return adapter.fromJson(attractionsFile)?.attractions ?: emptyList()
    }
}