package com.mongodb.app

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mongodb.app.ui.tasks.SinglePhotoPicker
import com.mongodb.app.ui.theme.MyApplicationTheme
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.util.UUID


class PhotoActivity : ComponentActivity() {

//    private lateinit var auth: FirebaseAuth
//    var uploadBitmap: Bitmap? = null
//    companion object {
//        private const val PERMISSION_REQUEST_CODE = 1001
//        private const val CAMERA_REQUEST_CODE = 1002
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme  {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SinglePhotoPicker()
                }
            }
        }
    }
}


@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
//@Throws(Exception::class)
//private fun uploadImage() {
//    var uploadBitmap: Bitmap? = null
//    var context:Context? = null
//    //converting image to bytes to be able to upload it.
//    val bias = ByteArrayOutputStream()
//    uploadBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bias)
//    val imageInBytes = bias.toByteArray()
//    val storageRef = Firebase.storage.reference
//    val newImage = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8") + ".jpg"
//    val newImagesRef = storageRef.child("Images/$newImage")
//    newImagesRef.putBytes(imageInBytes)
//        .addOnFailureListener { exception ->
//            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT)
//                .show()
//        }.addOnSuccessListener { taskSnapshot ->
//            newImagesRef.downloadUrl.addOnCompleteListener (
//                object : OnCompleteListener<Uri> {
//                override fun onComplete(p0: Task<Uri>) {
////                    sendItem(p0.result.toString())
//                }
//            })
//        }
//}


@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    KotlinTemplateAppTheme {
//        Greeting3("Android")
    }
}