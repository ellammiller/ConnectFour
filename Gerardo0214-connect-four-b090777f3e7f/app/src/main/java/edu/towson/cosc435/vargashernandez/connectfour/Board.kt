package edu.towson.cosc435.vargashernandez.connectfour

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Board {
    private var chips = MutableList(0) {
        Chip(0, it - it, it, false, R.drawable.chip_outline)
    }
    private var gameWon = false
    private var player1Turn = true
    private var checkingWin = false
    private var color = 0//R.drawable.gold_chip
    private var column1ChipsEmpty = 5
    private var column2ChipsEmpty = 5
    private var column3ChipsEmpty = 5
    private var column4ChipsEmpty = 5
    private var column5ChipsEmpty = 5
    private var column6ChipsEmpty = 5
    private var column7ChipsEmpty = 5
    private var winner = ""
    private var player1Name = ""

    init {
//        Log.d("TAG-COLOR-ID", "Empty: ${R.drawable.chip_outline}")
//        Log.d("TAG-COLOR-ID", "Gold: ${R.drawable.gold_chip}")
//        Log.d("TAG-COLOR-ID", "Blue: ${R.drawable.blue_chip}")
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun draw(player1Color: Int, p1Name: String) {
        player1Name = if(p1Name == ""){
            "Player 1"
        }else{
            p1Name
        }

        //gameWon = false
        if (!gameWon) {
            color = if (!player1Turn) {
                R.drawable.blue_chip
            } else {
                player1Color
            }

            var selected by remember { mutableStateOf(1) }
            var invalid by remember { mutableStateOf(" ") }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = invalid, color = Color.Red)
                }

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    RadioButton(
                        selected = selected == 1,
                        onClick = {
                            invalid = ""
                            selected = 0
                            selected = 1
                            if(!checkingWin){
                                val temp = chips.filter { chip ->
                                    chip.columnIndex == 1
                                }

                                if (column1ChipsEmpty >= 0) {
                                    val i = temp[column1ChipsEmpty].id
                                    chips[i - 1] = Chip(chips[i - 1].id, chips[i - 1].rowIndex, chips[i - 1].columnIndex, true, color)
                                    Log.d("TAG-CHECKING","Current Marker: ${i} CC: ${chips[i - 1].r}, Next Marker: ${chips[i].id} NC: ${chips[i].r}")
                                    player1Turn = !player1Turn

                                    checkingWin = true

                                    gameWon = checkWin(i)
                                    if(!gameWon){
                                        checkingWin = false
                                    }
                                } else {
                                    invalid = "Invalid Column Please Pick Again"
                                }
                            }
                            column1ChipsEmpty--
                        },
                        modifier = Modifier.scale(2f, 2f)
                    )

                    RadioButton(
                        selected = selected == 2,
                        onClick = {
                            invalid = ""
                            selected = 0
                            selected = 2
                            if(!checkingWin){
                                val temp = chips.filter { chip ->
                                    chip.columnIndex == 2
                                }
                                if (column2ChipsEmpty >= 0) {
                                    val i = temp[column2ChipsEmpty].id
                                    //Log.d("TAG-COL-2CE" ,"Col2CE: $column2ChipsEmpty")
                                    chips[i - 1] = Chip(chips[i - 1].id, chips[i - 1].rowIndex, chips[i - 1].columnIndex, true, color)
                                    Log.d("TAG-CHECKING","Current Marker: ${i} CC: ${chips[i - 1].r}, Next Marker: ${chips[i].id} NC: ${chips[i].r}")
                                    player1Turn = !player1Turn

                                    checkingWin = true
                                    gameWon = checkWin(i)
                                    if(!gameWon){
                                        checkingWin = false
                                    }
                                } else {
                                    invalid = "Invalid Column Please Pick Again"
                                }
                                column2ChipsEmpty--
                            }
                        },
                        modifier = Modifier.scale(2f, 2f)
                    )

                    RadioButton(
                        selected = selected == 3,
                        onClick = {
                            invalid = ""
                            selected = 0
                            selected = 3
                            if(!checkingWin){
                                val temp = chips.filter { chip ->
                                    chip.columnIndex == 3
                                }

                                if (column3ChipsEmpty >= 0) {
                                    val i = temp[column3ChipsEmpty].id
                                    chips[i - 1] = Chip(chips[i - 1].id, chips[i - 1].rowIndex, chips[i - 1].columnIndex, true, color)
                                    //Log.d("TAG-CHECKING","Current Marker: ${i} CC: ${chips[i - 1].r}, Next Marker: ${chips[i].id} NC: ${chips[i].r}")
                                    player1Turn = !player1Turn

                                    checkingWin = true
                                    gameWon = checkWin(i)
                                    if(!gameWon){
                                        checkingWin = false
                                    }
                                } else {
                                    invalid = "Invalid Column Please Pick Again"
                                }
                                column3ChipsEmpty--
                            }
                        },
                        modifier = Modifier.scale(2f, 2f)
                    )

                    RadioButton(
                        selected = selected == 4,
                        onClick = {
                            invalid = ""
                            selected = 0
                            selected = 4

                            if(!checkingWin){
                                val temp = chips.filter { chip ->
                                    chip.columnIndex == 4
                                }



                                if (column4ChipsEmpty >= 0) {
                                    val i = temp[column4ChipsEmpty].id
                                    chips[i - 1] = Chip(chips[i - 1].id, chips[i - 1].rowIndex, chips[i - 1].columnIndex, true, color)
                                    //Log.d("TAG-CHECKING","Current Marker: ${i} CC: ${chips[i - 1].r}, Next Marker: ${chips[i].id} NC: ${chips[i].r}")
                                    player1Turn = !player1Turn

                                    checkingWin = true
                                    gameWon = checkWin(i)
                                    if(!gameWon){
                                        checkingWin = false
                                    }
                                } else {
                                    invalid = "Invalid Column Please Pick Again"
                                }
                                column4ChipsEmpty--
                            }
                        },
                        modifier = Modifier.scale(2f, 2f)
                    )

                    RadioButton(
                        selected = selected == 5,
                        onClick = {
                            invalid = ""
                            selected = 0
                            selected = 5

                            if(!checkingWin){
                                val temp = chips.filter { chip ->
                                    chip.columnIndex == 5
                                }



                                if (column5ChipsEmpty >= 0) {
                                    val i = temp[column5ChipsEmpty].id
                                    chips[i - 1] = Chip(chips[i - 1].id, chips[i - 1].rowIndex, chips[i - 1].columnIndex, true, color)
                                    Log.d("TAG-CHECKING","Current Marker: ${i} CC: ${chips[i - 1].r}, Next Marker: ${chips[i].id} NC: ${chips[i].r}")
                                    player1Turn = !player1Turn

                                    checkingWin = true
                                    gameWon = checkWin(i)
                                    if(!gameWon){
                                        checkingWin = false
                                    }
                                } else {
                                    invalid = "Invalid Column Please Pick Again"
                                }
                                column5ChipsEmpty--
                            }
                        },
                        modifier = Modifier.scale(2f, 2f)
                    )

                    RadioButton(
                        selected = selected == 6,
                        onClick = {
                            invalid = ""
                            selected = 0
                            selected = 6

                            if(!checkingWin){
                                val temp = chips.filter { chip ->
                                    chip.columnIndex == 6
                                }

                                if (column6ChipsEmpty >= 0) {
                                    val i = temp[column6ChipsEmpty].id
                                    chips[i - 1] = Chip(chips[i - 1].id, chips[i - 1].rowIndex, chips[i - 1].columnIndex, true, color)
                                    //Log.d("TAG-CHECKING","Current Marker: ${i} CC: ${chips[i - 1].r}, Next Marker: ${chips[i].id} NC: ${chips[i].r}")
                                    Log.d("TAG", "ID NEEDED: ${chips[i - 1].id}, value of i: $i")
                                    player1Turn = !player1Turn

                                    checkingWin = true
                                    gameWon = checkWin(i)
                                    if(!gameWon){
                                        checkingWin = false
                                    }
                                } else {
                                    invalid = "Invalid Column Please Pick Again"
                                }
                                column6ChipsEmpty--
                            }
                        },
                        modifier = Modifier.scale(2f, 2f)
                    )

                    RadioButton(
                        selected = selected == 7,
                        onClick = {
                            invalid = ""
                            selected = 0
                            selected = 7

                            if(!checkingWin){
                                val temp = chips.filter { chip ->
                                    chip.columnIndex == 7
                                }

                                if (column7ChipsEmpty >= 0) {
                                    val i = temp[column7ChipsEmpty].id
                                    chips[i - 1] = Chip(chips[i - 1].id, chips[i - 1].rowIndex, chips[i - 1].columnIndex, true, color)
                                    //Log.d("TAG-CHECKING","Current Marker: ${i} CC: ${chips[i - 1].r}, Next Marker: ${chips[i].id} NC: ${chips[i].r}")
                                    player1Turn = !player1Turn

                                    checkingWin = true
                                    if(chips[i - 1].id != 42){
                                        gameWon = checkWin(i)
                                    }else{
                                        if(chips[i - 1].id == 42){
                                            gameWon = (chips[i - 4].r == chips [i - 1].r && chips[i - 3].r == chips [i - 1].r && chips[i - 2].r == chips [i - 1].r ) ||
                                                    (chips[i  - 9].r == chips[i - 1].r && chips[i-17].r == chips[i - 1].r && chips[i - 25].r == chips[i-1].r)
                                        }
                                    }
                                    if(!gameWon){
                                        checkingWin = false
                                    }
                                } else {
                                    invalid = "Invalid Column Please Pick Again"
                                }
                                column7ChipsEmpty--
                            }
                        },
                        modifier = Modifier.scale(2f, 2f)
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                LazyVerticalGrid(
                    cells = GridCells.Fixed(7),
                ) {
                    items(chips) { chip ->
                        chip.drawChip()
                        //chip.logData()
                        //Text("Row: ${c.rowIndex}, Col: ${c.columnIndex}, Val: ${chips.indexOf(c)}")
                    }
                }
            }
        }else{
            Box(
                modifier = Modifier.fillMaxSize(),

            ){
                Column(

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    winner = if(color == player1Color){
                        player1Name
                    }else{
                        "Player 2"
                    }
                    Text("$winner, Congratulations!", fontSize = 32.sp )
                    Text("YOU WIN!!!!", fontSize = 32.sp )
                }

            }
        }
    }

    private fun checkWin(index: Int): Boolean{
        //Log.d("TAG-checkWin", "Current Marker $index, CC: ${chips[index - 1].r}")
        return Logic().start(chips, index)
    }
}




