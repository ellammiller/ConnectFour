package edu.towson.cosc435.vargashernandez.connectfour

import android.util.Log

class Logic {
    private var _chips = MutableList(0) {
        Chip(0, it - it, it, false, R.drawable.chip_outline)
    }
    private var currentChipIdH = 0
    private var currentChipIdV = 0
    private var horizontalChipsInARow = 0
    private var verticalChipsInARow = 0

    private var currentChipIdDLR = 0
    private var currentChipIdDRL = 0
    private var diagonalLRChipsInARow = 0
    private var diagonalRLChipsInARow = 0

    private var invalidDiagonalLRIndex = listOf(5,6,7,12,13,14,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41)

    fun start(chips: MutableList<Chip>, currentMarker: Int): Boolean{
        horizontalChipsInARow = 1
        verticalChipsInARow = 1
        diagonalLRChipsInARow = 1
        diagonalRLChipsInARow = 1

        currentChipIdH = currentMarker
        currentChipIdV = currentMarker
        currentChipIdDLR = currentMarker
        currentChipIdDRL = currentMarker

        _chips = chips
        //Log.d("TAG-LOGIC-CHECK", "Current Marker: $currentMarker CC: ${_chips[currentMarker - 1].r}, Next Marker: ${_chips[currentMarker].id}  NC:${_chips[currentMarker].r}")

        val horizontalWin: Boolean = checkHorizontalWin()
        //Log.d("TAG-LOGIC-CHECKHZ", "BOOL: $horizontalWin")

        val verticalWin : Boolean = checkVerticalWin()
        //Log.d("TAG-LOGIC-CHECKVT", "BOOL: $verticalWin")

        val diagonalLRWin: Boolean = checkDiagonalLRWin()
        //Log.d("TAG-LOGIC-CHECKDLR", "BOOL: $diagonalLRWin")

        val diagonalRLWin : Boolean = checkDiagonalRLWin()
        //Log.d("TAG-LOGIC-CHECKDRL", "BOOL: $diagonalRLWin")

        return horizontalWin || verticalWin || diagonalLRWin || diagonalRLWin
    }

    private fun checkHorizontalWin(): Boolean{
        /**
         * We want to check if there are previous chips before this one.
         * if yes set the marker to that chip
         * if no, check for win horizontally
         */
        currentChipIdH = checkPreviousHChip(currentChipIdH)
        //Log.d("TAG-PREVIOUS-CHIP", "ID: $currentChipIdH")
        return checkHorizontal(currentChipIdH)
    }

    private fun checkPreviousHChip(ccid: Int): Int {
        /**
         * Check if there is no previous chip
         */
        if (ccid == 1) return ccid else
        if(_chips[ccid - 1].id % 7 == 0 ||  _chips[ccid - 1].r != _chips[ccid - 2].r){
            return ccid
        }

        return checkPreviousHChip(ccid - 1)
    }

    private fun checkHorizontal(ccid: Int): Boolean {
        //Log.d("TAG-CHIPS-IN-A-ROW", "Number: $horizontalChipsInARow")
        //Log.d("TAG-CCID", "CCID: $ccid")
        if (horizontalChipsInARow == 4) {
            return true
        } else {
            if (_chips[ccid - 1].id + 1 == 43 || _chips[ccid - 1].r != _chips[ccid].r) {
                return false
            } else {
                horizontalChipsInARow++
            }
        }
        return checkHorizontal(ccid + 1)
    }

    private fun checkVerticalWin(): Boolean{
        currentChipIdV = checkPreviousVChip(currentChipIdV)
        //Log.d("TAG-PREVIOUS-CHIPV", "ID: $currentChipIdV")
        return checkVertical(currentChipIdV)
    }

    private fun checkPreviousVChip(ccid: Int): Int{
        if(_chips[ccid - 1].rowIndex == 6 || _chips[ccid + 6].r != _chips[ccid - 1].r){
            return ccid
        }

        return checkPreviousVChip(ccid + 7)
    }

    private fun checkVertical(ccid: Int): Boolean{
        //Log.d("TAG-CHIPS-IN-A-ROW", "Number: $verticalChipsInARow")
        //Log.d("TAG-CCID", "CCID: $ccid")
        if(verticalChipsInARow == 4){
            return true
        }else {
            //Log.d("TAG-CC", "ID: ${_chips[ccid - 1].id}, RI: ${_chips[ccid - 1].rowIndex}")
            if (_chips[ccid - 1].rowIndex == 1 || _chips[ccid - 1].r != _chips[(ccid - 1) - 7].r) {
                return false
            } else {
                verticalChipsInARow++
            }
        }
        return checkVertical((ccid - 1) - 6)
    }

    private fun checkDiagonalLRWin(): Boolean{
        currentChipIdDLR = checkPreviousDLRChip(currentChipIdDLR)
        //Log.d("TAG-PREVIOUS-CHIPDLR", "ID: $currentChipIdDLR")
        return checkDiagonalLR(currentChipIdDLR)
    }

    private fun checkPreviousDLRChip(ccid: Int): Int{
        if(_chips[ccid - 1].rowIndex == 6 || _chips[ccid + 5].r != _chips[ccid - 1].r){
            return ccid
        }
        return checkPreviousDLRChip(ccid + 6)
    }

    private fun checkDiagonalLR(ccid: Int): Boolean{
        //Log.d("TAG-CHIPS-IN-A-ROW", "Number: $diagonalLRChipsInARow")
        //Log.d("TAG-CCID", "CCID: $ccid")
        if(diagonalLRChipsInARow == 4){
            return true
        }else{
            //Log.d("TAG-CC", "ID: ${_chips[ccid - 1].id}, RI: ${_chips[ccid - 1].rowIndex}")
            if(_chips[ccid - 1].rowIndex == 1 ||_chips[ccid - 1].r != _chips[(ccid - 1) - 6].r){
                return false
            }else{
                diagonalLRChipsInARow++
            }
        }

        return checkDiagonalLR((ccid - 1) - 5)
    }

    private fun checkDiagonalRLWin(): Boolean{
        currentChipIdDRL = checkPreviousDRLChip(currentChipIdDRL)
        return if(invalidDiagonalLRIndex.contains(currentChipIdDRL)){
            false
        }else{
            checkDiagonalRL(currentChipIdDRL)
        }
        //Log.d("TAG-PREVIOUS-CHIPDRL", "ID: $currentChipIdDRL")
        //return checkDiagonalRL(currentChipIdDRL)
    }

    private fun checkPreviousDRLChip(ccid: Int): Int{
        if(_chips[ccid - 1].rowIndex == 1 ||_chips[ccid - 1].columnIndex == 1 || _chips[(ccid - 1) - 8].r != _chips[ccid - 1].r){
            return ccid
        }
        return checkPreviousDRLChip(ccid - 8)
    }

    private fun checkDiagonalRL(ccid: Int): Boolean{
        //Log.d("TAG-CHIPS-IN-A-ROW", "Number: $diagonalRLChipsInARow")
        //Log.d("TAG-CCID", "CCID: $ccid")

        //This might be needed to change to 41
        if(diagonalRLChipsInARow == 4){
            return true
        }else{
            if(_chips[ccid - 1].r != _chips[(ccid - 1) + 8].r){
                return false
            }else{
                diagonalRLChipsInARow++
            }
        }
        return checkDiagonalRL((ccid - 1) + 9)
    }
}