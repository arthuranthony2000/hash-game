package com.arthuranthony.hashgame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.arthuranthony.hashgame.R;
import com.arthuranthony.hashgame.model.Game;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Model entities
    private Game hash_game;

    // View Components
    private Button [] positions = new Button[9];
    private Button restart_game;
    private Button undo_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0;i < positions.length; i++){
            int identifier_position = getResources().getIdentifier("btn"+i, "id", getPackageName());
            positions[i] = findViewById(identifier_position);
            positions[i].setOnClickListener(this);
        }

        hash_game = new Game();

        // Define view components
        restart_game = findViewById(R.id.restart_game);
        undo_play = findViewById(R.id.undo_play);

        // Observer comportament
        restart_game.setOnClickListener(view -> restart()); // Restart the game after press the restart button
        undo_play.setOnClickListener(view -> undo());
    }

    private boolean won(){
        for(int [] play : Game.getWinnerPlays()){
            if(hash_game.getPlays()[play[0]]==hash_game.getPlays()[play[1]] &&
                    hash_game.getPlays()[play[1]] == hash_game.getPlays()[play[2]] &&
                    hash_game.getPlays()[play[0]]!= -1
            ){
                return true;
            }
        }
        return false;
    }

    private void restart(){
        hash_game.setNumber_of_plays(1);
        hash_game.setPlayer_of_the_time(true);
        for(int i=0;i < positions.length; i++){
            positions[i].setText("");
            hash_game.setPlay(i,-1);
        }
        hash_game.clearHistoryPositions();
        hash_game.setFinished_game(false);
    }

    private void undo(){
        if(hash_game.getHistory_positions().size() != 0){
            hash_game.setNumber_of_plays(hash_game.getNumber_of_plays() - 1);
            hash_game.setPlayer_of_the_time(!hash_game.isPlayer_of_the_time());
            positions[hash_game.getHistory_positions().get(hash_game.getHistory_positions().size() - 1)].setText("");
            hash_game.setPlay(hash_game.getHistory_positions().get(hash_game.getHistory_positions().size() - 1), -1);
            hash_game.removePosition(hash_game.getHistory_positions().size() - 1);
            if(hash_game.isFinished_game()){
                hash_game.setFinished_game(false);
            }
        }
    }

    @Override
    public void onClick(View view){
        if(((Button)view).getText().toString().equals("") && !hash_game.isFinished_game()) { // If the position has not been selected
            int position_btn = Integer.parseInt(view.getResources().getResourceEntryName(view.getId()).substring(3,4));
            hash_game.addPosition(position_btn);
            if(hash_game.isPlayer_of_the_time()){ // If the X player of the time, mark in the button the text X
                ((Button)view).setText("X");
                hash_game.setPlay(position_btn, 0);
            } else {
                ((Button)view).setText("O");
                hash_game.setPlay(position_btn, 1);
            }

            if(won()){
                hash_game.setFinished_game(true);
                if(hash_game.isPlayer_of_the_time()){
                    Toast.makeText(view.getContext(), "Play X won the game", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(view.getContext(), "Play O won the game", Toast.LENGTH_SHORT).show();
                }
            } else if(hash_game.getNumber_of_plays() == 9){
                    Toast.makeText(view.getContext(), "The game is tied", Toast.LENGTH_SHORT).show();
                }
            hash_game.setNumber_of_plays(hash_game.getNumber_of_plays() + 1);
            hash_game.setPlayer_of_the_time(!hash_game.isPlayer_of_the_time());
        }
    }
}









