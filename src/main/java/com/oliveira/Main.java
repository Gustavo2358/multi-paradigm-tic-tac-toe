package com.oliveira;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Character[][] state = {
                {'1','2', '3'},
                {'4','5', '6'},
                {'7','8', '9'},
        };
        game(state);
    }

    private static void game(Character[][] state) {
        Player turn = Player.O;
        int choice;
        while (true) {
            System.out.printf("It's %s turn:\n\n", turn);
            System.out.println(showBoard(state));
            Scanner sc = new Scanner(System.in);

            try {
                choice = sc.nextInt();
                if(choice < 1 || choice > 9)
                    throw new IllegalStateException();
            }
            catch (Exception e){
                System.out.println("Ops! You need to enter an available number in the board!");
                continue;
            }

            if(updateState(state, choice, turn)) {

                boolean win = checkForWinner(state, turn);
                if (win) {
                    System.out.printf("Play %s wins!\n", turn.symbol());
                    System.out.println(showBoard(state));
                    break;
                }

                turn = toggleTurn(turn);
            }
            else{
                System.out.println("Choice is not available, choose other");
            }

        }
    }

    static boolean checkForWinner(Character[][] state, Player player) {
        return IntStream.range(0, state.length).anyMatch(i ->
                    checkRow(state, i, player.symbol()) ||
                    checkColumn(state, i, player.symbol())
                ) ||
                checkDiagonal(state, player.symbol()) ||
                checkAntiDiagonal(state, player.symbol()
                );
    }

    private static boolean checkAntiDiagonal(Character[][] state, Character symbol) {
        return IntStream.range(0, state.length).allMatch(i -> state[i][state.length - 1 - i] == symbol);
    }

    private static boolean checkDiagonal(Character[][] state, Character symbol) {
        return IntStream.range(0, state.length).allMatch(i -> state[i][i] == symbol);
    }

    private static boolean checkColumn(Character[][] state, int col, Character symbol) {
        return IntStream.range(0, state[col].length).allMatch(row -> state[row][col] == symbol);
    }

    private static boolean checkRow(Character[][] state, int row, Character symbol) {
        return IntStream.range(0, state[row].length).allMatch(col -> state[row][col] == symbol);
    }

    private static Player toggleTurn(final Player turn) {
        if(turn.equals(Player.O)) {
            return Player.X;
        }
        return Player.O;
    }

    private static boolean updateState(final Character[][] state, final int choice, final Player turn) {
        if(choiceIsAvailable(state, choice)) {
            int[] indexes = mapChoiceToIndexes(choice);
            state[indexes[0]][indexes[1]] = turn.symbol();
            return true;
        }
        return false;
    }

    private static int[] mapChoiceToIndexes(final int choice){
        int line = (choice - 1) / 3;
        int column = (choice - 1) % 3;
        return new int[]{line, column};
    }

    private static boolean choiceIsAvailable(final Character[][] state, final int choice) {
        int[] indexes = mapChoiceToIndexes(choice);
        return !(state[indexes[0]][indexes[1]].equals(Player.O.symbol()) ||
                    state[indexes[0]][indexes[1]].equals(Player.X.symbol())) ;
    }

    public static String showBoard(final Character[][] state){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){
                sb.append(String.format(" %c ",state[i][j]));
                if(isNotLastIndexOfArray(j, state[i]))
                    sb.append("|");
            }
            sb.append("\n");
            if(isNotLastIndexOfArray(i, state))
                sb.append("-------------\n");
        }
        return sb.toString();
    }

    public static <T> boolean isNotLastIndexOfArray(final int index, final T[] arr){
        return index != arr.length - 1;
    }
}

enum Player{
    X('X'),
    O('O');

    private final Character symbol;
    Player(Character symbol) {
        this.symbol = symbol;
    }

    public Character symbol(){
        return this.symbol;
    }
}