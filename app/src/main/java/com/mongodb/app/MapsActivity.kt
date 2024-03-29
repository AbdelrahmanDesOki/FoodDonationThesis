package com.mongodb.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mongodb.app.presentation.tasks.AddItemViewModel
import com.mongodb.app.ui.tasks.Map.CheckForPermission
import com.mongodb.app.ui.tasks.Map.LocationPermissionScreen
import com.mongodb.app.ui.tasks.Map.MapScreen
import com.mongodb.app.ui.theme.GoogleMapsTheme
import com.mongodb.app.ui.theme.MyApplicationTheme

class MapsActivity() : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme



                GoogleMapsTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background

                    ) {
                        var hasLocationPermission by remember {
                            mutableStateOf(CheckForPermission(this))
                        }

                        if (hasLocationPermission) {

                            MapScreen(context = this)


                        } else {
                            LocationPermissionScreen {
                                hasLocationPermission = true
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}



@Composable
fun drawMap(context: Context, viewModel: AddItemViewModel){

}


//@Composable
//fun getViewModel(context: Context,viewModel: AddItemViewModel){
//    MapScreen(context = context, viewModel = viewModel)
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}