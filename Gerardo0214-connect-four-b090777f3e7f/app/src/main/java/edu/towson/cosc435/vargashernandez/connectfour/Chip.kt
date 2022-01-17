package edu.towson.cosc435.vargashernandez.connectfour

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

class Chip (var id: Int ,var rowIndex: Int, var columnIndex: Int, var isActive: Boolean, var r: Int) {
    @Composable
    fun drawChip(){
        Image(painter = painterResource(id = r), contentDescription = "", contentScale = ContentScale.FillBounds)
    }

    fun logData(){
        Log.d("CHIP-INFO", "ID: $id Col: $columnIndex Row: $rowIndex Filled: $isActive")
    }
}
