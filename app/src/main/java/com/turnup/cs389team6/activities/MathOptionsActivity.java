package com.turnup.cs389team6.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.turnup.cs389team6.R;

/**
 * Created by Taylor White on 3/26/2015.
 */
public class MathOptionsActivity extends Activity {

    Button startGame;
    int selectedLevel=0;
    @Override


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);
        startGame = (Button) findViewById(R.id.startgame);
    }
    public void mathButtons(View v){
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()){
            case R.id.Level1:
                Toast.makeText(this,"You have selected difficulty 1",Toast.LENGTH_SHORT).show();
                selectedLevel = 1;
                break;
            case R.id.Level2:
                Toast.makeText(this,"You have selected difficulty 2",Toast.LENGTH_SHORT).show();
                break;



        }



    }
    public void playGame(View v){

        if(selectedLevel == 0){
            Toast.makeText(this,"Please selected a difficulty",Toast.LENGTH_LONG).show();
        }
        else{
            Intent playGame = new Intent(this,GamePlay.class);
            playGame.putExtra("LEVEL",selectedLevel);
            startActivity(playGame);
        }
    }
}