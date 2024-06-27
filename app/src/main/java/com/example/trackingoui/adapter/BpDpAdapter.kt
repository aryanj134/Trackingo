package com.example.trackingoui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trackingoui.domain.pojo.response.LocTime
import com.example.trackingoui.databinding.ItemLayoutBinding

class BpDpAdapter(private var itemList: List<LocTime>): RecyclerView.Adapter<BpDpAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        val oddTitleTextView = binding.listTitleOdd
        val oddSubtitleTextView = binding.listSubtitleOdd
        val evenTitleTextView = binding.listTitleEven
        val evenSubtitleTextView = binding.listSubtitleEven
        val oddCardView = binding.layout2
        val evenCardView = binding.layout1
        val smallDividerView = binding.trackingDividerCopy
        val dividerView = binding.trackingDividerMain
        val busIcon = binding.busTracker
        val verticalGuideline = binding.guidelineVertical
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.apply {

            if(position == 0) {
                smallDividerView.visibility = View.INVISIBLE
            } else{
                busIcon.visibility = View.GONE
                verticalGuideline.visibility = View.GONE
                smallDividerView.visibility = View.VISIBLE
            }
            if (position % 2 == 0) {
                oddTitleTextView.text = currentItem.title
                oddSubtitleTextView.text = currentItem.subtitle
                oddCardView.visibility = View.VISIBLE
                evenCardView.visibility = View.INVISIBLE
            }
            else {
                evenTitleTextView.text = currentItem.title
                evenSubtitleTextView.text = currentItem.subtitle
                oddCardView.visibility = View.INVISIBLE
                evenCardView.visibility = View.VISIBLE
            }
            dividerView.visibility = if (position == itemCount - 1) View.INVISIBLE else View.VISIBLE
        }
    }

    override fun getItemCount() = itemList.size

    fun updateData(newItemList: List<LocTime>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}