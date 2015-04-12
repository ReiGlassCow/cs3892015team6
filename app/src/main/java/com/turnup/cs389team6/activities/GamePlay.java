package com.turnup.cs389team6.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.turnup.cs389team6.R;

import java.util.Random;

public class GamePlay extends ActionBarActivity {

    //Equation numbers
    int levelOne = 10;


    int valueOne=0;
    int valueTwo=0;
    int answer = 0;

    Random rand;
    TextView problem;
    TextView chosenLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getintent = getIntent();
        int level = getintent.getIntExtra("LEVEL",0);

        setContentView(R.layout.game_layout);
        chosenLevel = (TextView) findViewById(R.id.Difficulty);
        chosenLevel.setText("LEVEL"+level);

        //Problem textview
        problem = (TextView) findViewById(R.id.Problem);

        rand = new Random();

        switch (level){
            case 1:
                new MyTask().execute(levelOne);
        }


    }

    public class MyTask extends AsyncTask<Integer ,Void, Void>{

        @Override
        protected Void doInBackground(Integer... params) {

             valueOne = rand.nextInt(params[0]);
             valueTwo = rand.nextInt(params[0]);

            answer = valueOne+valueTwo;




            return null;
        }

        @Override
        protected void onPreExecute() {

            try{
                Thread.sleep(0000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            problem.setText(valueOne+ "  +  " +valueTwo);
        }
    }







    }





