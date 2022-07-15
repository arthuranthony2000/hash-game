package com.arthuranthony.hashgame.model;

import java.util.ArrayList;
import java.util.List;

// Observable
public class Game {
    private int [] plays = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private static int [][] winner_plays = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    private List<Integer> history_positions;
    private int number_of_plays;
    private boolean player_of_the_time;
    private boolean finished_game;

    public Game(){
        this.setNumber_of_plays(1);
        this.setPlayer_of_the_time(true);
        this.setHistory_positions(new ArrayList<>());
        this.setFinished_game(false);
    }

    public boolean won(){
        for(int [] play : Game.getWinnerPlays()){
            if(this.getPlays()[play[0]]==this.getPlays()[play[1]] &&
                    this.getPlays()[play[1]] == this.getPlays()[play[2]] &&
                    this.getPlays()[play[0]]!= -1
            ){
                return true;
            }
        }
        return false;
    }

    public boolean isFinished_game() {
        return finished_game;
    }

    public void setFinished_game(boolean finished_game) {
        this.finished_game = finished_game;
    }

    public void addPosition(int position){
        this.history_positions.add(position);
    }

    public void removePosition(int position){
        this.history_positions.remove(position);
    }

    public void clearHistoryPositions(){
        this.history_positions.clear();
    }

    public void setPlay(int i, int play){
        this.plays[i] = play;
    }

    public static int [][] getWinnerPlays(){
        return winner_plays;
    }

    public int[] getPlays() {
        return plays;
    }

    public void setPlays(int[] plays) {
        this.plays = plays;
    }

    public static int[][] getWinner_plays() {
        return winner_plays;
    }

    public static void setWinner_plays(int[][] winner_plays) {
        Game.winner_plays = winner_plays;
    }

    public List<Integer> getHistory_positions() {
        return history_positions;
    }

    public void setHistory_positions(List<Integer> history_positions) {
        this.history_positions = history_positions;
    }

    public int getNumber_of_plays() {
        return number_of_plays;
    }

    public void setNumber_of_plays(int number_of_plays) {
        this.number_of_plays = number_of_plays;
    }

    public boolean isPlayer_of_the_time() {
        return player_of_the_time;
    }

    public void setPlayer_of_the_time(boolean player_of_the_time) {
        this.player_of_the_time = player_of_the_time;
    }
}
