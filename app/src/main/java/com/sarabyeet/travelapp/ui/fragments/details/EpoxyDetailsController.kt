package com.sarabyeet.travelapp.ui.fragments.details

import com.airbnb.epoxy.EpoxyController
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.databinding.ModelDescriptionEpoxyBinding
import com.sarabyeet.travelapp.databinding.ModelDetailHeaderEpoxyBinding
import com.sarabyeet.travelapp.databinding.ModelFactEpoxyBinding
import com.sarabyeet.travelapp.databinding.ModelMonthsEpoxyBinding
import com.sarabyeet.travelapp.ui.epoxy.ViewBindingKotlinModel

class EpoxyDetailsController(
    private val attraction: Attraction,

): EpoxyController() {
    var isGridOn: Boolean = false
    lateinit var layoutCallback: ()-> Unit

    override fun buildModels() {

        EpoxyDescriptionModel(attraction.description).id("description_epoxy").addTo(this)

        //val facts = Resources.getSystem().getString(R.string.facts_palce_holder, attraction.facts.size)

        EpoxyHeaderModel("${attraction.facts.size} Facts", isGridOn, layoutCallback).id("facts_epoxy").addTo(this)

        attraction.facts.forEachIndexed { index, fact ->
            EpoxyFactModel(fact).id("fact_$index").addTo(this)
        }
        EpoxyMonthsModel(attraction.months_to_visit).id("months_to_visit_epoxy").addTo(this)

    }

    data class EpoxyHeaderModel(
        val totalFacts: String,
        val isGridOn: Boolean,
        val onClickLayoutIcon: () -> Unit
        ): ViewBindingKotlinModel<ModelDetailHeaderEpoxyBinding>(R.layout.model_detail_header_epoxy){
        override fun ModelDetailHeaderEpoxyBinding.bind() {
            titleTextView.text = totalFacts
            layoutOptionsIcon.setOnClickListener {
                onClickLayoutIcon.invoke()
            }
            layoutOptionsIcon.setImageResource(if (isGridOn) R.drawable.ic_round_view_list_24 else R.drawable.ic_round_grid_view_24)
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
    data class EpoxyDescriptionModel(
        val description: String
    ): ViewBindingKotlinModel<ModelDescriptionEpoxyBinding>(R.layout.model_description_epoxy){

        override fun ModelDescriptionEpoxyBinding.bind() {
            descriptionTextView.text = description
        }
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class EpoxyMonthsModel (
        val monthsToVisit: String
    ): ViewBindingKotlinModel<ModelMonthsEpoxyBinding>(R.layout.model_months_epoxy){
        override fun ModelMonthsEpoxyBinding.bind() {
            monthsToVisitTextView.text = monthsToVisit
        }
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class EpoxyFactModel (
        val fact: String
        ): ViewBindingKotlinModel<ModelFactEpoxyBinding>(R.layout.model_fact_epoxy) {
        override fun ModelFactEpoxyBinding.bind() {
            factTextView.text = fact
        }
    }
}