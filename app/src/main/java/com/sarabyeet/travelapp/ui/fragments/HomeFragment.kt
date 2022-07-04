package com.sarabyeet.travelapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sarabyeet.travelapp.adapter.HomeFragmentController
import com.sarabyeet.travelapp.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeController = HomeFragmentController{ attractionId->
            activityViewModel.getAttraction(attractionId)
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSiteDetailFragment())
        }

        binding.homeRecyclerView.setController(homeController)
        // binding.homeRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        homeController.isLoading = true
        // Observing the underlying changes here
        activityViewModel.attractionsListLiveData.observe(viewLifecycleOwner){ attractions ->
            homeController.attractions = attractions
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}