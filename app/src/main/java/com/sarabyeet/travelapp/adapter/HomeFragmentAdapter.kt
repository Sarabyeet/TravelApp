package com.sarabyeet.travelapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarabyeet.travelapp.R
import com.sarabyeet.travelapp.data.Attraction
import com.sarabyeet.travelapp.databinding.ViewHolderAttractionBinding
import com.squareup.picasso.Picasso

class HomeFragmentAdapter(
    private val onClickedCallback: (String) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val attractions = ArrayList<Attraction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
    }

    inner class AttractionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
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
    }
}