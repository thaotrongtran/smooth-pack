package com.google.firebase.codelab.image_labeling

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ImageView
import com.google.firebase.ml.vision.label.FirebaseVisionLabel


class ImageLabelActivity : BaseCameraActivity() {
    lateinit var Checkmark: ImageView
    lateinit var ClearButton: ImageView
    //lateinit var xMark : ImageView
    private val itemAdapter: ImageLabelAdapter by lazy {
        ImageLabelAdapter(listOf())
    }

    val listLabel: MutableList<FirebaseVisionLabel> = ArrayList();

    val carryOn = 0
    val checked = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvLabel.layoutManager = LinearLayoutManager(this)
        rvLabel.adapter = itemAdapter

        Checkmark = findViewById(R.id.Checkmark)
        ClearButton = findViewById(R.id.ClearButton)





      //  xMark = findViewById(R.id.xMark)
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
                    Checkmark.setOnClickListener{
                       listLabel.add(temp.first())
                        println("Just clicked")
                        itemAdapter.setList(listLabel)
                        hidePreview()
                        Checkmark.visibility = View.GONE
                        ClearButton.visibility = View.GONE
                    }
                    ClearButton.setOnClickListener{
//                        listLabel.add(temp.first())
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
                    // Task completed successfully
                    progressBar.visibility = View.GONE
                    itemAdapter.setList(it)
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    progressBar.visibility = View.GONE
                    Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
    }

    override fun onClick(v: View?) {

//                if(v?.id == R.id.Checkmark){
//            println("CHECKMARK")
//        }else if(v?.id == R.id.ClearButton){
//            println("XMARK")
//        }
        progressBar.visibility = View.VISIBLE
        Checkmark.visibility = View.VISIBLE
        ClearButton.visibility = View.VISIBLE
        //xMark.visibility = View.VISIBLE
        cameraView.captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot
            runImageLabeling(cameraKitImage.bitmap)
            runOnUiThread {
                showPreview()
                imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }

        }
    }

}
