package com.sarabyeet.travelapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.databinding.FragmentSiteDetailBinding
import com.squareup.picasso.Picasso

class SiteDetailFragment : BaseFragment() {
    private var _binding: FragmentSiteDetailBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: SiteDetailFragmentArgs by navArgs()

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
        val attraction = attractions.find {
            it.id == safeArgs.attractionId
        } ?: Attraction()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}