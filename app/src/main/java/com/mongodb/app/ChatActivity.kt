package com.mongodb.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.mongodb.app.ui.tasks.HomeView
import com.mongodb.app.ui.theme.MyApplicationTheme

class ChatActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // Need to check the ID for each user and keep it connected to the message

                val currentUser = FirebaseAuth.getInstance().currentUser

//                if (currentUser != null) {
                    HomeView()
//                } else {
                    // Display a login screen or navigate to it.
                    // You can use the Navigation component for navigation.
//                    Text("Please log in")
                }
            }
        }
    }


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    KotlinTemplateAppTheme {
//        Greeting2("Android")
    }
}

@Composable
fun KotlinTemplateAppTheme(content: () -> Unit) {

}
