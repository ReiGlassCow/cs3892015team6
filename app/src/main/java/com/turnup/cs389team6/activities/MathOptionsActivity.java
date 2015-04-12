package com.turnup.cs389team6.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.turnup.cs389team6.R;
import com.turnup.cs389team6.constants.Difficulty;

/**
 * Created by Taylor White on 3/26/2015.
 */
public class MathOptionsActivity extends BaseActivity {
    private static final String EXTRA_DIFFICULTY = "extra_difficulty";

    private Button startGame;
    Difficulty selectedDifficulty;

    @Override


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);
        startGame = (Button) findViewById(R.id.startgame);
    }

    public void mathButtons(View v) {

        switch (v.getId()) {
            case R.id.level_one:
                selectedDifficulty = Difficulty.LEVEL_ONE;
                break;
            case R.id.level_two:
                selectedDifficulty = Difficulty.LEVEL_TWO;
                break;
            case R.id.level_three:
                selectedDifficulty = Difficulty.LEVEL_THREE;
                break;
        }
    }

    public void playGame(View v) {
        //Check to make sure that user has selected an option.
        if (selectedDifficulty == null) {
            Toast.makeText(this, "Please selected a difficulty", Toast.LENGTH_LONG).show();
        }
        //Start game based off level selection
        else {
            Intent playGame = new Intent(this, GameActivity.class);
            playGame.putExtra(EXTRA_DIFFICULTY, selectedDifficulty);
            startActivity(playGame);
        }
    }
}