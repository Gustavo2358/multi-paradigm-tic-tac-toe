package com.oliveira;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {

    @Test
    public void testNoWinnerEmptyBoard() {
        Character[][] emptyBoard = {
                {'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '9'}
        };
        assertFalse(Main.checkForWinner(emptyBoard, Player.X));
        assertFalse(Main.checkForWinner(emptyBoard, Player.O));
    }

    @Test
    public void testWinnerInRow() {
        Character[][] rowWinBoard = {
                {'X', 'X', 'X'},
                {'4', '5', '6'},
                {'7', '8', '9'}
        };
        assertTrue(Main.checkForWinner(rowWinBoard, Player.X));
    }

    @Test
    public void testWinnerInColumn() {
        Character[][] columnWinBoard = {
                {'O', '2', '3'},
                {'O', '5', '6'},
                {'O', '8', '9'}
        };
        assertTrue(Main.checkForWinner(columnWinBoard, Player.O));
    }

    @Test
    public void testWinnerInMainDiagonal() {
        Character[][] diagonalWinBoard = {
                {'X', '2', '3'},
                {'4', 'X', '6'},
                {'7', '8', 'X'}
        };
        assertTrue(Main.checkForWinner(diagonalWinBoard, Player.X));
    }

    @Test
    public void testWinnerInAntiDiagonal() {
        Character[][] antiDiagonalWinBoard = {
                {'1', '2', 'O'},
                {'4', 'O', '6'},
                {'O', '8', '9'}
        };
        assertTrue(Main.checkForWinner(antiDiagonalWinBoard, Player.O));
    }

    @Test
    public void testDrawNoWinner() {
        Character[][] drawBoard = {
                {'X', 'X', 'O'},
                {'O', 'O', 'X'},
                {'X', 'O', 'X'}
        };
        assertFalse(Main.checkForWinner(drawBoard, Player.X));
        assertFalse(Main.checkForWinner(drawBoard, Player.O));
    }
}