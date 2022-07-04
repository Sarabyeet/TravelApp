package com.sarabyeet.travelapp.ui.fragments.details

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.adapter.DetailFragmentImagesController
import com.sarabyeet.travelapp.databinding.FragmentSiteDetailBinding
import com.sarabyeet.travelapp.ui.MainActivity
import com.sarabyeet.travelapp.ui.fragments.BaseFragment

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
                headerRecyclerView.setControllerAndBuildModels(
                        DetailFragmentImagesController(attraction.image_urls)
                    )
                LinearSnapHelper().attachToRecyclerView(headerRecyclerView)

                if (attraction.image_urls.size > 1){
                    indicator.attachToRecyclerView(headerRecyclerView, LinearSnapHelper())
                }

                var isGridMode: Boolean = binding.contentDetailRecyclerView.layoutManager is GridLayoutManager
                val contentEpoxyController = EpoxyDetailsController(attraction)
                contentEpoxyController.isGridOn = isGridMode
                contentEpoxyController.layoutCallback = {
                    if (isGridMode) {
                        binding.contentDetailRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    } else {
                        binding.contentDetailRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                    }

                    isGridMode = !isGridMode
                    contentEpoxyController.isGridOn = isGridMode
                    contentEpoxyController.requestModelBuild()
                }

                contentDetailRecyclerView.setControllerAndBuildModels(contentEpoxyController)

//                descriptionTextView.text = attraction.description
//                monthsToVisitTextView.text = attraction.months_to_visit
//                factsTextView.text = getString(R.string.facts_palce_holder, attraction.facts.size)
//
//                factsTextView.setOnClickListener {
//                    activityViewModel.factsDialogLiveData.postValue(attraction)
//                }
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