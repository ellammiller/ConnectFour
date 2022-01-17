package edu.towson.cosc435.vargashernandez.connectfour

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @ExperimentalCoroutinesApi
    @Test
    fun checkCreateChip(){
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        //Act
        val chipID = chips[0].id

        //Assert
        assertEquals(chipID, 1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkHorizontalWin() = runBlocking{
        /**
         * Check to see that four in a row horizontally is true
         */
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        chips[1] = Chip(1, 1, 1, true, R.drawable.gold_chip)
        chips[2] = Chip(2, 1, 2, true, R.drawable.gold_chip)
        chips[3] = Chip(3, 1, 3, true, R.drawable.gold_chip)
        chips[4] = Chip(4, 1, 4, true, R.drawable.gold_chip)

        //Act
        val logic = Logic()
        //takes the index of the current placed chip
        val win = logic.start(chips, 3)

        //Assert
        assertEquals(win, true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkHorizontalWin1() = runBlocking{
        /**
         * Arrange - checking if the methods implemented return a win if there are four chips of the same color in the array
         * but are separated by a row on the board which would result in no win
         */
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        chips[27] = Chip(27, 4, 6, true, R.drawable.gold_chip)
        chips[28] = Chip(28, 4, 7, true, R.drawable.gold_chip)
        chips[29] = Chip(29, 5, 1, true, R.drawable.gold_chip)
        chips[30] = Chip(30, 5, 2, true, R.drawable.gold_chip)

        //Act
        val logic = Logic()
        //takes the index of the current placed chip
        val win = logic.start(chips, 29)

        //Assert
        assertEquals(win, false)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkHorizontalWin2() = runBlocking{
        /**
         * Check to see that if there is three in a row plus one with a different color, it returns false
         */
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        chips[1] = Chip(1, 1, 1, true, R.drawable.gold_chip)
        chips[2] = Chip(2, 1, 2, true, R.drawable.gold_chip)
        chips[3] = Chip(3, 1, 3, true, R.drawable.gold_chip)
        chips[4] = Chip(4, 1, 4, true, R.drawable.blue_chip)

        //Act
        val logic = Logic()
        //takes the index of the current placed chip
        val win = logic.start(chips, 3)

        //Assert
        assertEquals(win, false)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkVerticalWin() = runBlocking{
        /**
         * Check to see that four in a row vertically is true
         */
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        chips[1] = Chip(1, 1, 1, true, R.drawable.gold_chip)
        chips[8] = Chip(8, 2, 1, true, R.drawable.gold_chip)
        chips[15] = Chip(15, 3, 1, true, R.drawable.gold_chip)
        chips[22] = Chip(22, 4, 1, true, R.drawable.gold_chip)

        //Act
        val logic = Logic()
        //takes the index of the current placed chip
        val win = logic.start(chips, 15)

        //Assert
        assertEquals(win, true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkVerticalWin1() = runBlocking{
        /**
         * This is a weird test, when the game is running we get the correct results but
         * the unit tests return a different result
         */
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        chips[1] = Chip(1, 1, 1, true, R.drawable.blue_chip)
        chips[8] = Chip(8, 2, 1, true, R.drawable.gold_chip)
        chips[15] = Chip(15, 3, 1, true, R.drawable.gold_chip)
        chips[22] = Chip(22, 4, 1, true, R.drawable.gold_chip)

        //Act
        val logic = Logic()
        //takes the index of the current placed chip
        val win = logic.start(chips, 1)

        //Assert
        assertEquals(win, false)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkDiagonalLR() = runBlocking{
        /**
         * Check to see that four in a row diagonally from left to right is true
         */
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        chips[36] = Chip(36, 6, 1, true, R.drawable.gold_chip)
        chips[30] = Chip(30, 5, 2, true, R.drawable.gold_chip)
        chips[24] = Chip(24, 4, 3, true, R.drawable.gold_chip)
        chips[18] = Chip(18, 3, 4, true, R.drawable.gold_chip)

        //Act
        val logic = Logic()
        //takes the index of the current placed chip
        val win = logic.start(chips, 30)

        //Assert
        assertEquals(win, true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkDiagonalLR1() = runBlocking{
        /**
         * Check to see that four in a row diagonally from left to right is false,
         * also a weird test, where in game we get the correct results, however, the
         * unit test does not
         */
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        chips[36] = Chip(36, 6, 1, true, R.drawable.gold_chip)
        chips[30] = Chip(30, 5, 2, true, R.drawable.gold_chip)
        chips[24] = Chip(24, 4, 3, true, R.drawable.gold_chip)
        chips[18] = Chip(18, 3, 4, true, R.drawable.blue_chip)

        //Act
        val logic = Logic()
        //takes the index of the current placed chip
        val win = logic.start(chips, 18)

        //Assert
        assertEquals(win, false)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkDiagonalRL() = runBlocking{
        /**
         * Check to see that four in a row diagonally from left to right is true
         */
        //Arrange
        var chips = MutableList(0) {
            Chip(0, it - it, it, false, R.drawable.chip_outline)
        }
        var index = 1
        for (r in 1..6) {
            for (c in 1..7) {
                chips.add(Chip(index, r, c, false, R.drawable.chip_outline))
                index++
            }
        }

        chips[15] = Chip(15, 3, 1, true, R.drawable.gold_chip)
        chips[23] = Chip(23, 4, 2, true, R.drawable.gold_chip)
        chips[31] = Chip(31, 5, 3, true, R.drawable.gold_chip)
        chips[39] = Chip(39, 6, 4, true, R.drawable.gold_chip)

        //Act
        val logic = Logic()
        //takes the index of the current placed chip
        val win = logic.start(chips, 15)

        //Assert
        assertEquals(win, true)
    }
}