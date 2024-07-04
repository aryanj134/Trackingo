package com.bitlasoft.trackingo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.ItemRatingBinding
import com.bitlasoft.trackingo.utils.FeedbackRatingClickListener

class RatingFeedbackReviewAdapter(private val clickListener: FeedbackRatingClickListener) :
    RecyclerView.Adapter<RatingFeedbackReviewAdapter.ViewHolder>() {

    private var selectedRating = 0
    private var ratingMap: MutableMap<String, Int> = mutableMapOf()

    inner class ViewHolder(private val binding: ItemRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.ratingTitle.text = "How do you rate Trackingo service?"

            val selectedDrawableLeft = R.drawable.rated_square_left_box
            val selectedDrawable = R.drawable.rated_square_box
            val selectedDrawableRight = R.drawable.rated_square_right_box

            val unselectedDrawableLeft = R.drawable.rating_square_left_border
            val unselectedDrawable = R.drawable.rating_square_box
            val unselectedDrawableRight = R.drawable.rating_square_right_border

            binding.rating1.apply {
                setOnClickListener{ handleRatingClick(1) }
                background = if(selectedRating == 1)
                    ContextCompat.getDrawable(context, selectedDrawableLeft)
                else
                    ContextCompat.getDrawable(context, unselectedDrawableLeft)
            }

            binding.rating2.apply {
                setOnClickListener{ handleRatingClick(2) }
                background = if(selectedRating == 2)
                    ContextCompat.getDrawable(context, selectedDrawable)
                else
                    ContextCompat.getDrawable(context, unselectedDrawable)
            }

            binding.rating3.apply {
                setOnClickListener{ handleRatingClick(3) }
                background = if(selectedRating == 3)
                    ContextCompat.getDrawable(context, selectedDrawable)
                else
                    ContextCompat.getDrawable(context, unselectedDrawable)
            }

            binding.rating4.apply {
                setOnClickListener{ handleRatingClick(4) }
                background = if(selectedRating == 4)
                    ContextCompat.getDrawable(context, selectedDrawable)
                else
                    ContextCompat.getDrawable(context, unselectedDrawable)
            }

            binding.rating5.apply {
                setOnClickListener{ handleRatingClick(5) }
                background = if(selectedRating == 5)
                    ContextCompat.getDrawable(context, selectedDrawableRight)
                else
                    ContextCompat.getDrawable(context, unselectedDrawableRight)
            }
        }

        private fun handleRatingClick(rating: Int) {
            selectedRating = rating
            ratingMap["Rating"] = selectedRating
            clickListener.onRatingClicked(rating)
            bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    fun getAllRatings(): Map<String, Int> {
        return ratingMap
    }

    fun clearAllRatings() {
        selectedRating = 0
        ratingMap.clear()
        notifyDataSetChanged()
    }

    fun setRating(rating: Int) {
        selectedRating = rating
        notifyDataSetChanged()
    }
}