package com.google.firebase.codelab.image_labeling

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import kotlinx.android.synthetic.main.item_row.view.*
import com.google.firebase.codelab.image_labeling.ImageLabelActivity

class ImageLabelAdapter(private var firebaseVisionList: List<Any>) : RecyclerView.Adapter<ImageLabelAdapter.ItemHolder>() {
    val c = ImageLabelActivity::class
    lateinit var context: Context
    val temp = ImageLabelActivity();

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindCloud(currentItem: FirebaseVisionCloudLabel) {
            itemView.itemName.text = currentItem.label
            //itemView.itemAccuracy.text = "Probability : ${(currentItem.confidence * 100).toInt()}%"
            itemView.switch5.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // The toggle is enabled
                    //ImageLabelActivity.carryOn += 5
                    println("TOOGLE")
                    println(currentItem.label)

                    temp.carryOn += 10;
                    println(temp.carryOn)
                } else {
                    // The toggle is disabled
                    temp.carryOn -= 10;
                    println(temp.carryOn)
                }
            }
        }

        fun bindDevice(currentItem: FirebaseVisionLabel) {
            itemView.itemName.text = currentItem.label
            //itemView.itemAccuracy.text = "Probability : ${(currentItem.confidence * 100).toInt()}%"
            itemView.switch5.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // The toggle is enabled
                    //ImageLabelActivity.carryOn += 5
                    println("TOOGLE")
                    println(currentItem.label)

                    temp.carryOn += 10;
                    println(temp.carryOn)
                } else {
                    // The toggle is disabled
                    temp.carryOn -= 10;
                    println(temp.carryOn)
                }
            }
        }

    }

    fun setList(visionList: List<Any>) {

            firebaseVisionList = visionList
            notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val currentItem = firebaseVisionList[position]
        if (currentItem is FirebaseVisionCloudLabel)
            holder.bindCloud(currentItem)
        else
            holder.bindDevice(currentItem as FirebaseVisionLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_row, parent, false))
    }

    override fun getItemCount() = firebaseVisionList.size
}