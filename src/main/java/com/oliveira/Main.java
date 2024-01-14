package com.oliveira;

import java.util.Scanner;

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
            showBoard(state);
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
                    showBoard(state);
                    break;
                }

                turn = toggleTurn(turn);
            }

        }
    }

    private static boolean checkForWinner(Character[][] state, Player player) {
        int points = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if(state[i][j] == player.symbol()) {
                    points++;
                    if (points == 3)
                        return true;
                }
            }
            points = 0;
        }

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if(state[j][i] == player.symbol()) {
                    points++;
                    if (points == 3)
                        return true;
                }
            }
            points = 0;
        }

        for (int i = 0; i < state.length; i++) {
            if(state[i][i] == player.symbol()) {
                points++;
                if (points == 3)
                    return true;
            }
        }
        points = 0;
        int lastColumn = state.length - 1;
        for (int i = 0; i < state.length; i++) {
            if(state[i][lastColumn - i] == player.symbol()) {
                points++;
                if (points == 3)
                    return true;
            }
        }

        return false;
    }

    private static Player toggleTurn(Player turn) {
        if(turn.equals(Player.O)) {
            return Player.X;
        }
        return Player.O;
    }

    private static boolean updateState(Character[][] state, int choice, Player turn) {
        if(choiceIsAvailable(choice,state)) {
            int[] indexes = mapChoiceToIndexes(choice);
            state[indexes[0]][indexes[1]] = turn.symbol();
            return true;
        }

        System.out.println("Choice not available, choose other");
        return false;

    }

    private static int[] mapChoiceToIndexes(int choice){
        int line = (choice - 1) / 3;
        int column = (choice - 1) % 3;
        return new int[]{line, column};
    }

    private static boolean choiceIsAvailable(int choice, Character[][] state) {
        int[] indexes = mapChoiceToIndexes(choice);
        return !(state[indexes[0]][indexes[1]].equals(Player.O.symbol()) ||
                    state[indexes[0]][indexes[1]].equals(Player.X.symbol())) ;
    }

    public static void showBoard(Character[][] state){
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){
                System.out.printf(" %c ",state[i][j]);
                if(isNotLastIndexOfArray(j, state[i]))
                    System.out.print("|");
            }
            System.out.println();
            if(isNotLastIndexOfArray(i, state))
                System.out.println("-------------");
        }
    }

    public static <T> boolean isNotLastIndexOfArray(int index, T[] arr){
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