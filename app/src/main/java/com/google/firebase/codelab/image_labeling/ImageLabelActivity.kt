package com.google.firebase.codelab.image_labeling

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import kotlinx.android.synthetic.main.activity_main.*

class ImageLabelActivity : BaseCameraActivity() {
    //val listLabel = listOf<FirebaseVisionLabel>()
    val listLabel: MutableList<FirebaseVisionCloudLabel> = ArrayList()

    private val itemAdapter: ImageLabelAdapter by lazy {
        ImageLabelAdapter(listOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(message = "AAAAA")
        //Log.d("AAAA", "LLLLL")
        rvLabel.layoutManager = LinearLayoutManager(this)
        rvLabel.adapter = itemAdapter
    }

    private fun runCloudImageLabeling(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseCloudImageDetector
        val detector = FirebaseVision.getInstance().visionCloudLabelDetector

        //Use the detector to detect the labels inside the image
        detector.detectInImage(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    //progressBar.visibility = View.GONE
                    //println(message = it.first().confidence)

                    for (obj in it) {
                        println(obj.label)
                        println(obj.confidence)
                    }

                    //listLabel.add(sortedList.last())
                   // println(sortedList.last().label)
                    println("confidence")
                   // println(sortedList.last().confidence)
                    itemAdapter.setList(listLabel)
                    //itemAdapter.setList(it)
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    progressBar.visibility = View.GONE
                    Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
    }

    private fun runImageLabeling(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseImageDetector
        val detector = FirebaseVision.getInstance().visionLabelDetector

        //Use the detector to detect the labels inside the image
        detector.detectInImage(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    progressBar.visibility = View.GONE
                    println("confidence")
                    //listLabel.add(it.first())
                    itemAdapter.setList(listLabel)
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    progressBar.visibility = View.GONE
                    Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
    }



    override fun onClick(v: View?) {
        progressBar.visibility = View.VISIBLE
        cameraView.captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot
            runCloudImageLabeling(cameraKitImage.bitmap)
            runOnUiThread {
                showPreview()
                imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }
        }
    }

}
