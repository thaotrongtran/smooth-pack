package com.google.firebase.codelab.image_labeling


import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import org.w3c.dom.Text


class ImageLabelActivity : BaseCameraActivity() {
    lateinit var Checkmark: ImageView
    lateinit var ClearButton: ImageView

    private val itemAdapter: ImageLabelAdapter by lazy {
        ImageLabelAdapter(listOf())
    }

    val listLabel: MutableList<FirebaseVisionCloudLabel> = ArrayList()
    var carryOn = 0
    var checked = 0

    var checkedLabels: Array<String> = arrayOf("can", "soda can", "mobile phone", "water", "laptop", "cell phone", "aluminum can", "soda can","water","bottle","book", "backpack","shirt","hat","watch", "technology", "water bottle", "weapon", "knife", "scissor", "extension cord")
    var checkedLuggage: HashSet<String> = checkedLabels.toHashSet()

    var carryLabels: Array<String> = arrayOf("can", "soda can", "mobile phone", "baked goods", "dish", "laptop", "cell phone", "fruit", "chips", "chip", "backpack", "book", "shirt", "hat", "watch", "technology", "headphone", "wallet", "tablet", "pen", "paper", "hand", "leg")
    var carryLuggage: HashSet<String> = carryLabels.toHashSet()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvLabel.layoutManager = LinearLayoutManager(this)
        rvLabel.adapter = itemAdapter
        Checkmark = findViewById(R.id.Checkmark)
        ClearButton = findViewById(R.id.ClearButton)
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
                    val temp = it
                    Checkmark.setOnClickListener {
                        println("Just clicked")
                        itemAdapter.setList(listLabel)
                        hidePreview()
                        Checkmark.visibility = View.GONE
                        ClearButton.visibility = View.GONE
                    }
                    ClearButton.setOnClickListener {
                        println("Just clicked")
                        itemAdapter.setList(listLabel)
                        hidePreview()
                        Checkmark.visibility = View.GONE
                        ClearButton.visibility = View.GONE
                    }
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    progressBar.visibility = View.GONE
                    Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
    }

    private fun runCloudImageLabeling(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseCloudImageDetector
        val detector = FirebaseVision.getInstance().visionCloudLabelDetector

        //Use the detector to detect the labels inside the image
        detector.detectInImage(image)
                .addOnSuccessListener {
                    var temp = it.first()
                    progressBar.visibility = View.GONE
                    Checkmark.setOnClickListener {
                        listLabel.add(temp)
                        println("Just clicked")
                        itemAdapter.setList(listLabel)
                        hidePreview()
                        Checkmark.visibility = View.GONE
                        ClearButton.visibility = View.GONE
                    }
                    ClearButton.setOnClickListener {
                        println("Just clicked")
                        itemAdapter.setList(listLabel)
                        hidePreview()
                        Checkmark.visibility = View.GONE
                        ClearButton.visibility = View.GONE
                    }
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
        Checkmark.visibility = View.VISIBLE
        ClearButton.visibility = View.VISIBLE
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
