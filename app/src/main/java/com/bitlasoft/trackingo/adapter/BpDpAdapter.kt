package com.bitlasoft.trackingo.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.ItemLayoutBinding
import com.bitlasoft.trackingo.domain.pojo.location_data.response.LocTime

class BpDpAdapter(private var itemList: List<LocTime>): RecyclerView.Adapter<BpDpAdapter.ViewHolder>() {

    private var curr_sp_id = -1
    private var is_passed = false

    inner class ViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        val oddTitleTextView = binding.listTitleOdd
        val oddSubtitleTextView = binding.listSubtitleOdd
        val evenTitleTextView = binding.listTitleEven
        val evenSubtitleTextView = binding.listSubtitleEven
        val oddCardView = binding.belowBpDpLayout
        val evenCardView = binding.aboveBpDpLayout
        val smallDividerView = binding.trackingDividerCopy
        val dividerView = binding.trackingDividerMain
        val busTracker = binding.busTracker
        val busTrackerMid = binding.busTrackerMid
        val checkLogo = binding.circleView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setSpId(curr_sp_id: Int?) {
        this.curr_sp_id = curr_sp_id?:-1
    }

    fun setIsPassed(isPassed: Boolean?) {
        this.is_passed = isPassed?:false
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.apply {
            val context = itemView.context
            if(position == 0) {
                currentItem.isBusIconVisible = true
                smallDividerView.visibility = View.INVISIBLE
                oddTitleTextView.text = currentItem.title
                oddSubtitleTextView.text = "First Boarding point\nScheduled at ${currentItem.scheduledTime}"
                oddCardView.visibility = View.VISIBLE
                evenCardView.visibility = View.INVISIBLE
            } else{
                currentItem.isBusIconVisible = false
                smallDividerView.visibility = View.VISIBLE
                if (position % 2 == 0) {
                    oddTitleTextView.text = currentItem.title
                    oddSubtitleTextView.text = "Scheduled at ${currentItem.scheduledTime}"
                    oddCardView.visibility = View.VISIBLE
                    evenCardView.visibility = View.INVISIBLE
                }
                else {
                    evenTitleTextView.text = currentItem.title
                    evenSubtitleTextView.text = "Scheduled at ${currentItem.scheduledTime}"
                    oddCardView.visibility = View.INVISIBLE
                    evenCardView.visibility = View.VISIBLE
                }
            }
            busTracker.visibility = if(currentItem.isBusIconVisible!!) View.VISIBLE else View.INVISIBLE

            dividerView.visibility = if (position == itemCount - 1) View.INVISIBLE else View.VISIBLE

            val arrivalTime = currentItem.arrivalTime ?: 0
            val runningStatus = currentItem.runningStatus
            val iconState = currentItem.iconState?: "color_gray"

            if (currentItem.isCross){
                val delayTime = currentItem.delayTime ?: 0
                val deptTime = currentItem.deptTime ?: 0

                val color = when(iconState){
                    "color_green" -> R.color.green
                    "color_yellow" -> R.color.yellow
                    "color_red" -> R.color.red
                    "color_gray" -> R.color.gray
                    else -> R.color.gray
                }
                var text: List<String>?
                var subtitleText : String?
                 if (currentItem.skippedStatus==true){
                     subtitleText = "Skipped"
                     checkLogo.setImageResource(R.drawable.ic_skipped)
                }else{
                     if(runningStatus == null){
                         subtitleText = "Skipped"
                     } else {
                         if(delayTime > 60) {
                             text = runningStatus.split(" ")
                             subtitleText = "Left at $deptTime Delayed by " + (text[2].toInt() - (text[2].toInt()/60)*60).toString() + " hr " + (text[2].toInt()%60).toString() + " mins "
                         }
                         else
                            subtitleText = "Left at $deptTime $runningStatus"
                     }
                     checkLogo.setImageResource(R.drawable.check_circle_24)
                }
                oddSubtitleTextView.text = subtitleText
                evenSubtitleTextView.text = subtitleText
                checkLogo.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
            }
            else {
                checkLogo.setColorFilter(ContextCompat.getColor(context, R.color.gray), PorterDuff.Mode.SRC_IN)
            }
                if (curr_sp_id == currentItem.id) {
                    if (is_passed) {
                        busTracker.visibility = View.INVISIBLE
                        busTrackerMid.visibility = View.VISIBLE
                    } else {
                        busTrackerMid.visibility = View.INVISIBLE
                        busTracker.visibility = View.VISIBLE
                        if(position != 0) {
                            if (position % 2 == 0) {
                                oddSubtitleTextView.text = "Arrived at $arrivalTime"
                            } else {
                                evenSubtitleTextView.text = "Arrived at $arrivalTime"
                            }
                        } else {
                            oddSubtitleTextView.text = "First Boarding point\nScheduled at ${currentItem.scheduledTime}"
                        }
                    }
                } else {
                    busTracker.visibility = View.INVISIBLE
                    busTrackerMid.visibility = View.INVISIBLE
                }
        }
    }

    override fun getItemCount() = itemList.size

    fun updateData(newItemList: List<LocTime>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}