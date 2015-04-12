package com.turnup.cs389team6.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.turnup.cs389team6.R;
import com.turnup.cs389team6.constants.Difficulty;
import com.turnup.cs389team6.constants.Operation;

import java.util.Random;

public class GameActivity extends BaseActivity {

    //Equation numbers
    private Difficulty selectedDifficulty;

    private Random rand;
    private TextView problem, chosenLevel;
    private int problemAnswer;
    private int[] possibleAnswers = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDifficulty = (Difficulty) getIntent().getSerializableExtra(MathOptionsActivity.EXTRA_DIFFICULTY);
        setContentView(R.layout.activity_game_layout);
        chosenLevel = (TextView) findViewById(R.id.difficulty_textview);
        chosenLevel.setText(getString(R.string.level_title, selectedDifficulty.ordinal() + 1));
        problem = (TextView) findViewById(R.id.problem_textview);
        rand = new Random();
        generateProblem();
    }

    private void generateProblem(){
        int selectedOp = rand.nextInt(selectedDifficulty.getSupportedOperations().length);
        Operation op = selectedDifficulty.getSupportedOperations()[selectedOp];
        int firstOperand = rand.nextInt(selectedDifficulty.getMaxValue() + Math.abs(selectedDifficulty.getMinValue()));
        firstOperand -= Math.abs(selectedDifficulty.getMinValue());
        int secondOperand = rand.nextInt(selectedDifficulty.getMaxValue());
        while(op == Operation.DIVISION && secondOperand == 0){
            secondOperand = rand.nextInt(selectedDifficulty.getMaxValue());
        }
        String operationSign = "";
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
                if(secondOperand > firstOperand){
                    int temp = secondOperand;
                    secondOperand = firstOperand;
                    firstOperand = secondOperand;
                }
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
                //generate answer randomly with w/e u want
                //possibleAnswers[i] = (generatedWrongAnswer)
            }
        }
        //setupui
    }



}





