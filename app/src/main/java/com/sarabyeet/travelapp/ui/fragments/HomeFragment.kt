package com.sarabyeet.travelapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sarabyeet.travelapp.adapter.HomeFragmentAdapter
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
        val homeAdapter = HomeFragmentAdapter{ attractionId->
            activityViewModel.getAttraction(attractionId)
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSiteDetailFragment())
        }

        binding.homeRecyclerView.adapter = homeAdapter
        binding.homeRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        // Observing the underlying changes here
        activityViewModel.attractionsListLiveData.observe(viewLifecycleOwner){ attractions ->
            homeAdapter.setData(attractions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    activityViewModel.attractionsListLiveData.observe(viewLifecycleOwner){
//        it.find { attraction ->
//            attraction.id == safeArgs.attractionId
//        } ?:
}