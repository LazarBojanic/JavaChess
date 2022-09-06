package com.chess.engine;

import com.chess.engine.board.Board;

public class JavaChess {
    public static void main(String[] args) {
        System.out.println("test1");
        Board board = Board.createStandardBoard();
        System.out.println("test2");
        System.out.println(board);
        System.out.println("test3");
    }
}
