package com.bitlasoft.trackingo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.ItemRatingBinding
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.RatingItem
import com.bitlasoft.trackingo.fragment.FeedbackFragment

class RatingAdapter(private val ratingItems: List<RatingItem>, private val clickListener: FeedbackFragment) :
    RecyclerView.Adapter<RatingAdapter.ViewHolder>() {

    private val ratingMap: MutableMap<String, Int> = mutableMapOf()
    private var selectedRating = 0
    inner class ViewHolder(private val binding: ItemRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RatingItem) {
            binding.ratingTitle.text = item.title

            val selectedDrawable = R.drawable.rated_square_box
            val selectedDrawableLeft = R.drawable.rated_square_left_box
            val selectedDrawableRight = R.drawable.rated_square_right_box

            val unselectedDrawableLeft = R.drawable.rating_square_left_border
            val unselectedDrawableRight = R.drawable.rating_square_right_border
            val unselectedDrawable = R.drawable.rating_square_box

            binding.rating1.apply {
                setOnClickListener{ handleRatingClick(1, item.title) }
                background = if(item.rating == 1)
                    ContextCompat.getDrawable(context, selectedDrawableLeft)
                else
                     ContextCompat.getDrawable(context, unselectedDrawableLeft)
            }

            binding.rating2.apply {
                setOnClickListener{ handleRatingClick(2, item.title) }
                background = if(item.rating == 2)
                    ContextCompat.getDrawable(context, selectedDrawable)
                else
                    ContextCompat.getDrawable(context, unselectedDrawable)
            }

            binding.rating3.apply {
                setOnClickListener{ handleRatingClick(3, item.title) }
                background = if(item.rating == 3)
                    ContextCompat.getDrawable(context, selectedDrawable)
                else
                    ContextCompat.getDrawable(context, unselectedDrawable)
            }

            binding.rating4.apply {
                setOnClickListener{ handleRatingClick(4, item.title) }
                background = if(item.rating == 4)
                    ContextCompat.getDrawable(context, selectedDrawable)
                else
                    ContextCompat.getDrawable(context, unselectedDrawable)
            }

            binding.rating5.apply {
                setOnClickListener{ handleRatingClick(5, item.title) }
                background = if(item.rating == 5)
                    ContextCompat.getDrawable(context, selectedDrawableRight)
                else
                    ContextCompat.getDrawable(context, unselectedDrawableRight)
            }
        }

        private fun handleRatingClick(rating: Int, title: String?) {
            val item = ratingItems[adapterPosition]
            item.rating = rating
            title?.let { ratingMap[it] = rating }
            notifyItemChanged(adapterPosition)
            clickListener.onRatingClicked(rating, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ratingItems[position])
    }

    fun getAllRatings(): Map<String, Int> {
        return ratingMap
    }

    fun clearAllRatings() {
        ratingMap.clear()
        ratingItems.forEach { it.rating = 0 }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = ratingItems.size
}