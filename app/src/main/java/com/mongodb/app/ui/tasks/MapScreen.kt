package com.mongodb.app.ui.tasks

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties


@Composable
fun MapScreen(context: Context) {

    var showMap by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf(LatLng(47.4734, 19.0599)) }
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
            mapProperties = mapProperties,
            lineType = lineType,
            changeIcon = changeIcon,
            onChangeMarkerIcon = {
                changeIcon = !changeIcon
            },
            onChangeMapType = {
                mapProperties = mapProperties.copy(mapType = it)
            }, onChangeLineType = {
                lineType = it
            })
    }
    else{
        Text(text = "Loading the MAp")
    }







}