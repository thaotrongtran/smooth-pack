package com.google.firebase.codelab.image_labeling
import android.hardware.SensorAdditionalInfo
import com.google.firebase.database.*

class Items(snapshot: DataSnapshot) {
    lateinit var name: String
    lateinit var CarryOn: String
    lateinit var CheckedIn: String
    lateinit var AdditionalInfo: String

    init {
        val data: HashMap<String, Any> = snapshot.value as HashMap<String,Any>
        name = data["Name"] as String
        CarryOn = data["CarryOns"] as String
        CheckedIn = data["CheckedIns"] as String
        AdditionalInfo = data["AdditionalInfo"] as String

    }

}
