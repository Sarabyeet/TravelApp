package com.sarabyeet.travelapp.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.databinding.FragmentSiteDetailBinding
import com.sarabyeet.travelapp.ui.MainActivity
import com.squareup.picasso.Picasso

class SiteDetailFragment : BaseFragment() {
    private var _binding: FragmentSiteDetailBinding? = null
    private val binding get() = _binding!!

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

        activityViewModel.selectedAttraction.observe(viewLifecycleOwner) { attraction ->
            binding.apply {
                titleTextView.text = attraction.title
                descriptionTextView.text = attraction.description
                Picasso.get().load(attraction.image_urls[0]).into(headerImageView)
                monthsToVisitTextView.text = attraction.months_to_visit
                factsTextView.text = getString(R.string.facts_palce_holder, attraction.facts.size)

                factsTextView.setOnClickListener {
                    activityViewModel.factsDialogLiveData.postValue(attraction)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_site_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemLocation -> {
                /** Calling the function to open maps intent with the selected Attraction */
                val attraction = activityViewModel.selectedAttraction.value ?: return true
                (activity as MainActivity).openOnMaps(attraction)
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