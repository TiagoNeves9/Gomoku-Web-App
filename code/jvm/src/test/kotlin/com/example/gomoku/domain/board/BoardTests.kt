package com.example.gomoku.domain.board

import com.example.gomoku.domain.Player
import com.example.gomoku.domain.Turn
import com.example.gomoku.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*


class BoardTests {
    @Test
    fun `test serialize BoardRun positions and type`() {
        val map = mapOf(
            "1A".toCell() to Turn.BLACK_PIECE,
            "5C".toCell() to Turn.WHITE_PIECE,
            "11A".toCell() to Turn.BLACK_PIECE,
            "14E".toCell() to Turn.WHITE_PIECE
        )
        val board = BoardRun(map, Turn.BLACK_PIECE)
        val serializedPos = board.positionsToString()
        val serializedTpe = board.typeToString()

        assertEquals("01AB05CW11AB14EW", serializedPos)
        assertEquals("RUNNING", serializedTpe)
    }

    @Test
    fun `test deserialize BoardRun positions and type`() {
        val map = mapOf(
            "1A".toCell() to Turn.BLACK_PIECE,
            "5C".toCell() to Turn.WHITE_PIECE,
            "11A".toCell() to Turn.BLACK_PIECE,
            "14E".toCell() to Turn.WHITE_PIECE
        )
        val lastTurn = map.values.last()
        val lastBoard = BoardRun(map, lastTurn)
        val serializedPos = lastBoard.positionsToString()
        val serializedType = lastBoard.typeToString()

        val deSerializedPos = serializedPos.stringToPositions()
        assertEquals(map, deSerializedPos)

        val deSerializedBoard = serializedType.stringToType(
            lastBoard,
            Player(
                User(UUID.randomUUID(), "TestUsername", "TestPassword"),
                lastTurn
            )
        )
        assertEquals(lastBoard.positions, deSerializedBoard.positions)
        assertTrue(deSerializedBoard is BoardRun)
    }

    @Test
    fun `test deserialize BoardDraw and BoardWin`() {
        val testPlayer = Player(
            User(UUID.randomUUID(), "TestUsername", "TestPassword"),
            Turn.BLACK_PIECE
        )

        /**     BoardDraw tests     */
        val boardDraw = BoardDraw(mapOf())
        val serializedType1 = boardDraw.typeToString()
        assertEquals("DRAW", serializedType1)

        val deserializedBoard1 = serializedType1.stringToType(boardDraw, testPlayer)
        assertTrue(deserializedBoard1 is BoardDraw)

        /**     BoardWin tests      */
        val boardWin = BoardWin(mapOf(), testPlayer)
        val serializedType2 = boardWin.typeToString()
        assertEquals("BLACK_WON", serializedType2)

        val deserializedBoard2 = serializedType2.stringToType(boardWin, boardWin.winner)
        assertTrue(deserializedBoard2 is BoardWin)
    }
}