package com.turnup.cs389team6.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.turnup.cs389team6.R;
import com.turnup.cs389team6.constants.Difficulty;
import com.turnup.cs389team6.constants.Operation;

import java.util.Random;

public class GameActivity extends BaseActivity {

    //Equation numbers
    private Difficulty selectedDifficulty;

    private Random rand;
    private TextView problem, chosenLevel, score;
    private int problemAnswer;
    private Integer[] possibleAnswers = new Integer[6];
    MediaPlayer cheer,woo,wrong_answer;
    private GridView mGridView;
    private int questionCounter = 0;
    private int scoreCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDifficulty = (Difficulty) getIntent().getSerializableExtra(MathOptionsActivity.EXTRA_DIFFICULTY);
        setContentView(R.layout.activity_game_layout);
        //Sounds
        cheer = MediaPlayer.create(GameActivity.this,R.raw.cheer);
        woo = MediaPlayer.create(GameActivity.this,R.raw.woo);
        wrong_answer = MediaPlayer.create(GameActivity.this,R.raw.wrong_answer);
        mGridView = (GridView) findViewById(R.id.grid_view);
        score = (TextView) findViewById(R.id.score);
        chosenLevel = (TextView) findViewById(R.id.difficulty_textview);
        chosenLevel.setText(getString(R.string.level_title, selectedDifficulty.ordinal() + 1));
        problem = (TextView) findViewById(R.id.problem_textview);
        rand = new Random();
        generateProblem();
    }

    private boolean isEndlessMode = false;

    private void generateProblem(){
        //Check score to determine difficulty.
        if(scoreCounter >= 400){
            //Play cheer
            cheer.start();
            switch (selectedDifficulty) {
                case LEVEL_ONE:


                    Toast.makeText(GameActivity.this, "You are moving on to LEVEL 2!", Toast.LENGTH_LONG).show();
                    selectedDifficulty = Difficulty.LEVEL_TWO;
                    chosenLevel.setText(getString(R.string.level_title, selectedDifficulty.ordinal() + 1));
                    scoreCounter = 0;
                    score.setText("Score: "+ scoreCounter);
                    break;
                case LEVEL_TWO:
                    selectedDifficulty = Difficulty.LEVEL_THREE;
                    Toast.makeText(GameActivity.this, "You are moving on to LEVEL 3!", Toast.LENGTH_SHORT).show();
                    chosenLevel.setText(getString(R.string.level_title, selectedDifficulty.ordinal() + 1));
                    scoreCounter = 0;
                    score.setText("Score: "+ scoreCounter);
                    break;
                case LEVEL_THREE:
                    if(!isEndlessMode){
                        isEndlessMode = true;
                        Toast.makeText(GameActivity.this, R.string.endless_mode, Toast.LENGTH_SHORT).show();
                    }


            }
            //selectedDifficulty = (Difficulty) getIntent().getSerializableExtra(MathOptionsActivity.EXTRA_DIFFICULTY+1);
        }


        int selectedOp = rand.nextInt(selectedDifficulty.getSupportedOperations().length);
        Operation op = selectedDifficulty.getSupportedOperations()[selectedOp];
        int firstOperand = rand.nextInt(selectedDifficulty.getMaxValue() + Math.abs(selectedDifficulty.getMinValue()));
        firstOperand -= Math.abs(selectedDifficulty.getMinValue());
        int secondOperand = rand.nextInt(selectedDifficulty.getMaxValue());
        while(op == Operation.DIVISION && secondOperand == 0){
            secondOperand = rand.nextInt(selectedDifficulty.getMaxValue());
        }
        String operationSign = "";
        if(secondOperand > firstOperand){
            int temp = secondOperand;
            secondOperand = firstOperand;
            firstOperand = secondOperand;
        }
        switch (op){
            case ADD:
                problemAnswer = firstOperand + secondOperand;
                operationSign = " + ";
                break;
            case SUBTRACT:
                problemAnswer = firstOperand - secondOperand;
                operationSign = " - ";
                break;
            case MULTIPLICATION:
                problemAnswer = firstOperand * secondOperand;
                operationSign = " x ";
                break;
            case DIVISION:
                if(firstOperand % secondOperand != 0){
                    secondOperand += firstOperand % secondOperand;
                }
                operationSign = " \u00F7 ";
                problemAnswer = firstOperand / secondOperand;
                break;
        }

        problem.setText(firstOperand + operationSign + secondOperand + " = ?");
        generateArrayOfPossibleAnswers();
    }

    private void generateArrayOfPossibleAnswers(){
        for(int i = 0; i < possibleAnswers.length; i++){
            possibleAnswers[i] = Integer.MAX_VALUE;
        }
        int answerPos = rand.nextInt(possibleAnswers.length);
        possibleAnswers[answerPos] = problemAnswer;
        for(int i = 0; i < possibleAnswers.length; i++){
            if(possibleAnswers[i] == Integer.MAX_VALUE){
               int possibleAnswer = problemAnswer;
               do{
                   possibleAnswer = rand.nextInt(selectedDifficulty.getMaxValue() * 3);
               } while(possibleAnswer == problemAnswer);
               possibleAnswers[i] =  possibleAnswer;
               //possibleAnswers[i] = (generatedWrongAnswer)
            }
        }
        setupGridAdapter();
    }

    private void setupGridAdapter(){
        final ArrayAdapter<Integer> mGridAdapter = new ArrayAdapter<Integer>(this, R.layout.item_answer_choice, R.id.answer_item_textview, possibleAnswers);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                questionCounter++;
                //edit Questions Left Value;
                //animate text of score.
                if(mGridAdapter.getItem(position).intValue() == problemAnswer){
                    woo.start();
                    Toast.makeText(GameActivity.this, R.string.correct_answer, Toast.LENGTH_SHORT).show();
                    scoreCounter += 50;
                    score.setText("Score: "+scoreCounter);
                    //correct. animate score up
                } else {
                    wrong_answer.start();
                    Toast.makeText(GameActivity.this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();
                    //wrong animate score down
                    scoreCounter -=25;
                    score.setText("Score: " +scoreCounter);
                }
                generateProblem();
            }
        });
    }



}





