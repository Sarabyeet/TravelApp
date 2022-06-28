package com.sarabyeet.travelapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.databinding.FragmentSiteDetailBinding
import com.squareup.picasso.Picasso

class SiteDetailFragment : BaseFragment() {
    private var _binding: FragmentSiteDetailBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: SiteDetailFragmentArgs by navArgs()
    private val attraction: Attraction by lazy {
        attractions.find {
            it.id == safeArgs.attractionId
        } ?: Attraction()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSiteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.apply {
            titleTextView.text = attraction.title
            descriptionTextView.text = attraction.description
            Picasso.get().load(attraction.image_urls[0]).into(headerImageView)
            monthsToVisitTextView.text = attraction.months_to_visit
            factsTextView.text = getString(R.string.facts_palce_holder, attraction.facts.size)
            factsTextView.setOnClickListener {
                // TODO:  
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_site_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menuItemLocation -> {
                val locationUri = Uri.parse("geo:${attraction.location.latitude},${attraction.location.longitude}?q=${attraction.title}")
                val mapIntent = Intent(Intent.ACTION_VIEW, locationUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}