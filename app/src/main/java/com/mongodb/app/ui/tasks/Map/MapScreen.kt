package com.mongodb.app.ui.tasks.Map

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.MapProperties
import com.mongodb.app.domain.Item


@Composable
    fun MapScreen(context: Context) {

        var showMap by remember { mutableStateOf(false) }
        var location = LatLng(47.4733781,19.059865)
        var mapProperties by remember { mutableStateOf(MapProperties()) }
        var changeIcon by remember { mutableStateOf(false) }
        var lineType by remember {
            mutableStateOf<LineType?>(null)
        }


    getCurrentLocation(context) {
            location = it
            showMap = true
        }
        if(showMap){
            MyMap(
                context = context,
                latLng = location,
                changeIcon = changeIcon,
                lineType = lineType,
                mapProperties = mapProperties,
                onChangeMarkerIcon = {
                    changeIcon = !changeIcon
                },
                onChangeMapType = {
                    mapProperties = mapProperties.copy(mapType = it)
                },
                onChangeLineType = {
                    lineType = it
                },
                task = Item(Firebase.auth.currentUser?.uid.toString())

            )
        }
        else{
            Text(text = "Loading the MAp")
        }







    }




