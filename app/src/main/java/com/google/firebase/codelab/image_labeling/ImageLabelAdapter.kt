package com.google.firebase.codelab.image_labeling

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import kotlinx.android.synthetic.main.item_row.view.*
import com.google.firebase.codelab.image_labeling.ImageLabelActivity
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class ImageLabelAdapter(private var firebaseVisionList: List<Any>) : RecyclerView.Adapter<ImageLabelAdapter.ItemHolder>() {
    val c = ImageLabelActivity::class
    lateinit var context: Context

    val temp = ImageLabelActivity();

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindCloud(currentItem: FirebaseVisionCloudLabel) {
            itemView.itemName.text = currentItem.label
            if (temp.checkedLuggage.contains(currentItem.label)) {
                //TOGGLE VIS FOR CHECKED LUGGAGE ICON
                itemView.carryonlug.visibility = View.GONE

                itemView.switch5.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        // The toggle is enable
                        println("TOOGLE")
                        println(currentItem.label)
                        temp.checked += 10;
                        Toast.makeText(context, "Your checked luggage weight is " + temp.checked + " lbs", Toast.LENGTH_LONG).show()
                    } else {
                        // The toggle is disabled
                        temp.checked -= 10;
                        Toast.makeText(context, "Your checked luggage weight is " + temp.checked + " lbs", Toast.LENGTH_LONG).show()
                    }
                }


            } else if (temp.carryLuggage.contains(currentItem.label)) {
                //TOGGLE VIS FOR CARRY ON LUGGAGE
                itemView.checkedlug.visibility = View.GONE
                itemView.switch5.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        println("TOOGLE")
                        println(currentItem.label)
                        temp.carryOn += 10;
                        Toast.makeText(context, "Your carry-on weight is " + temp.carryOn + " lbs", Toast.LENGTH_LONG).show()
                    } else {
                        // The toggle is disabled
                        temp.carryOn -= 10;
                        Toast.makeText(context, "Your carry-on weight is " + temp.carryOn + " lbs", Toast.LENGTH_LONG).show()
                    }
                }
            }


        }

        fun bindDevice(currentItem: FirebaseVisionLabel) {
            itemView.itemName.text = currentItem.label
            itemView.switch5.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // The toggle is enabled
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