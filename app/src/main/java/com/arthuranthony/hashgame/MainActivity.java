package com.arthuranthony.hashgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int [] plays = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private int [][] winner_plays = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    private Button [] positions = new Button[9];

    private Button restart_game;
    private Button undo_play;

    private List<Integer> history_positions;

    private int number_of_plays;
    private boolean player_of_the_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restart_game = findViewById(R.id.restart_game);
        undo_play = findViewById(R.id.undo_play);

        for(int i=0;i < positions.length; i++){
            int identifier_position = getResources().getIdentifier("btn"+i, "id", getPackageName());
            positions[i] = findViewById(identifier_position);
            positions[i].setOnClickListener(this);
        }

        number_of_plays = 1;
        player_of_the_time = true;
        history_positions = new ArrayList<>();

        restart_game.setOnClickListener(view -> restart()); // Restart the game after press the restart button
        undo_play.setOnClickListener(view -> undo());
    }

    private boolean won(){
        for(int [] play : winner_plays){
            if(plays[play[0]]==plays[play[1]] &&
                    plays[play[1]] == plays[play[2]] &&
                    plays[play[0]]!= -1
            ){
                return true;
            }
        }
        return false;
    }

    private void restart(){
        number_of_plays = 1;
        player_of_the_time = true;
        for(int i=0;i < positions.length; i++){
            positions[i].setText("");
            plays[i] = -1;
        }
        history_positions.clear();
    }

    private void undo(){
        if(history_positions.size() != 0){
            number_of_plays--;
            player_of_the_time = !player_of_the_time;
            positions[history_positions.get(history_positions.size() - 1)].setText("");
            plays[history_positions.get(history_positions.size() - 1)] = -1;
            history_positions.remove(history_positions.size() - 1);
        }
    }

    @Override
    public void onClick(View view){
        if(((Button)view).getText().toString().equals("")) { // If the position has not been selected
            int position_btn = Integer.parseInt(view.getResources().getResourceEntryName(view.getId()).substring(3,4));
            history_positions.add(position_btn);
            if(player_of_the_time){ // If the X player of the time, mark in the button the text X
                ((Button)view).setText("X");
                plays[position_btn] = 0;
            } else {
                ((Button)view).setText("O");
                plays[position_btn] = 1;
            }

            if(won()){
                if(player_of_the_time){
                    Toast.makeText(view.getContext(), "Play X won the game", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(view.getContext(), "Play O won the game", Toast.LENGTH_SHORT).show();
                }
            } else if(number_of_plays == 9){
                    Toast.makeText(view.getContext(), "The game is tied", Toast.LENGTH_SHORT).show();
                } else {
                number_of_plays++;
                player_of_the_time = !player_of_the_time;
            }
        }
    }
}









