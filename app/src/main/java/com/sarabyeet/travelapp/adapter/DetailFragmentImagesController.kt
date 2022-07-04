package com.sarabyeet.travelapp.adapter

import com.airbnb.epoxy.EpoxyController
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.databinding.EpoxyHeaderImagesViewBinding
import com.sarabyeet.travelapp.ui.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class DetailFragmentImagesController(
    val imageUrls: List<String>
): EpoxyController()  {

    override fun buildModels() {
        imageUrls.forEachIndexed() {index, url ->
            EpoxyHeaderImage(url).id(index).addTo(this)
        }
    }

    inner class EpoxyHeaderImage(
        private val imageUrl: String ): ViewBindingKotlinModel<EpoxyHeaderImagesViewBinding>(R.layout.epoxy_header_images_view){
        override fun EpoxyHeaderImagesViewBinding.bind() {
            Picasso.get().load(imageUrl).into(headerImageView)
        }
    }
}