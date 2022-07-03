package com.sarabyeet.travelapp.adapter

import com.airbnb.epoxy.EpoxyController
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.databinding.EpoxyHeaderViewBinding
import com.sarabyeet.travelapp.databinding.ViewHolderAttractionBinding
import com.sarabyeet.travelapp.ui.epoxy.LoadingEpoxyModel
import com.sarabyeet.travelapp.ui.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class HomeFragmentController(
    private val onClickedCallback: (String) -> Unit,
) : /* RecyclerView.Adapter<RecyclerView.ViewHolder>() */ EpoxyController() {

    var isLoading = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var attractions : List<Attraction> = emptyList()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }


    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }
        if (attractions.isEmpty()) {
            //todo show empty state
            return
        }

        val localAttractions = attractions.filter { attraction ->
            attraction.title.startsWith("ladakh", ignoreCase = true)
                    || attraction.title.startsWith("Valley", ignoreCase = true)
                    || attraction.title.startsWith("shimla", true )
                    || attraction.title.startsWith("Darjeeling", ignoreCase = true)
        }

        HeaderEpoxyModel("Local Sites", R.drawable.ic_round_location_on_24).id("header_view_local").addTo(this)
        localAttractions.forEach { attraction ->
            AttractionEpoxyModel(attraction, onClickedCallback)
                .id(attraction.id)
                .addTo(this)
            // Giving id ^ so that epoxy can perform all the diffing tasks easily
        }

        HeaderEpoxyModel("All sites").id("header_view_all_sites").addTo(this)
        attractions.forEach { attraction ->
            AttractionEpoxyModel(attraction, onClickedCallback)
                .id(attraction.id)
                .addTo(this)
            // Giving id ^ so that epoxy can perform all the diffing tasks easily
        }
    }

    /* Epoxy Recycler view implementation */
    data class AttractionEpoxyModel(
        val attraction: Attraction,
        val onClick: (String) -> Unit,
    ) : ViewBindingKotlinModel<ViewHolderAttractionBinding>(R.layout.view_holder_attraction) {
        override fun ViewHolderAttractionBinding.bind() {
            titleTextView.text = attraction.title
            monthsToVisitTextView.text = attraction.months_to_visit
            Picasso.get().load(attraction.image_urls[0]).into(headerImageView)

            root.setOnClickListener {
                onClick(attraction.id)
            }
        }
    }

    /* Epoxy Header */
    data class HeaderEpoxyModel(
        val title: String,
        val drawable: Int = R.drawable.ic_baseline_language_24

    ): ViewBindingKotlinModel<EpoxyHeaderViewBinding>(R.layout.epoxy_header_view){
        override fun EpoxyHeaderViewBinding.bind() {
            headerTextView.text = title
            headerTextView.setCompoundDrawablesWithIntrinsicBounds( drawable, 0, 0, 0)
        }
    }

    /* inner class AttractionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_attraction, parent, false)
    ) {
        private val binding = ViewHolderAttractionBinding.bind(itemView)

        fun onBind(attraction: Attraction, onClick: (String) -> Unit) {
            binding.titleTextView.text = attraction.title
            binding.monthsToVisitTextView.text = attraction.months_to_visit
            Picasso.get().load(attraction.image_urls[0]).into(binding.headerImageView)

            binding.root.setOnClickListener {
                onClick(attraction.id)
            }
        }
    } */

    /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttractionViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AttractionViewHolder).onBind(attractions[position], onClickedCallback)
    }

    override fun getItemCount(): Int {
        return attractions.size
    }

    fun setData(attractions: List<Attraction>) {
        this.attractions.clear()
        this.attractions.addAll(attractions)
        notifyDataSetChanged()
    } */

}