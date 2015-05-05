package com.turnup.cs389team6.activities;

import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.view.ViewGroup.LayoutParams;
import com.turnup.cs389team6.R;
import com.turnup.cs389team6.constants.Difficulty;
import com.turnup.cs389team6.constants.Operation;

import java.util.Random;

import static android.graphics.Color.*;

public class GameActivity extends BaseActivity {

    //Equation numbers
    private Difficulty selectedDifficulty;

    private Random rand;
    private TextView problem, chosenLevel, score, timerText;
    private int problemAnswer;
    private Integer[] possibleAnswers = new Integer[6];
    private long totalTime = 31000;
    private CountDownTimer timer;
    private MediaPlayer woo,cheer,wrong_answer,sad,sad_one,countdown;
    private GridView mGridView;
    private int questionCounter = 0;
    private int scoreCounter= 0;
    private boolean isEndlessMode = false;
    private ImageView stats;
    Point p;
    private int[] correct = new int[3];
    private int[] wrong = new int[3];
    private TextView l1,l2;


    LinearLayout layoutOfPopup;
    PopupWindow popupStats;

    TextView statistics;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedDifficulty = (Difficulty) getIntent().getSerializableExtra(MathOptionsActivity.EXTRA_DIFFICULTY);
        setContentView(R.layout.activity_game_layout);


        stats = (ImageView) findViewById(R.id.statistics);



        init();
        popupInit();


        //Media sounds
        countdown = MediaPlayer.create(GameActivity.this,R.raw.countdown);
        woo = MediaPlayer.create(GameActivity.this,R.raw.woo);
        cheer = MediaPlayer.create(GameActivity.this,R.raw.cheer);
        wrong_answer = MediaPlayer.create(GameActivity.this,R.raw.no_torture);
        sad = MediaPlayer.create(GameActivity.this,R.raw.sad_trombone);
        sad_one = MediaPlayer.create(GameActivity.this,R.raw.sad_one);
        mGridView = (GridView) findViewById(R.id.grid_view);
        score = (TextView) findViewById(R.id.score);
        timerText = (TextView) findViewById(R.id.timer);
        chosenLevel = (TextView) findViewById(R.id.difficulty_textview);
        chosenLevel.setText(getString(R.string.level_title, selectedDifficulty.ordinal() + 1));
        problem = (TextView) findViewById(R.id.problem_textview);
        rand = new Random();

        //Statistics

        correct[0]=0; correct[1]=0; correct[2]=0;
        wrong[0]=0; wrong[1]=0; wrong[2]=0;


        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p != null && !popupStats.isShowing()){
                    statistics.setText("Correct : " + correct[0] + "\nWrong: " + wrong[0]);

                    showPopup(GameActivity.this, p);

                }
                else{
                    popupStats.dismiss();
                }
            }
        });

        timer = new CountDownTimer(totalTime,1000){

            public void onTick(long millisUntilFinished){
                totalTime = millisUntilFinished;

                timerText.setText("Time: " + millisUntilFinished/1000);
            }
            public void onFinish(){
                if(activityStopped)
                    return;
                sad.start();
                Toast.makeText(GameActivity.this, R.string.time_up, Toast.LENGTH_SHORT).show();

                scoreCounter -= 25;
                score.setText("Score: "+scoreCounter);
                totalTime = 31000;
                sad.stop();
                generateProblem();
            }
        };
        generateProblem();
    }

    public void init() {
        statistics = new TextView(this);
        layoutOfPopup = new LinearLayout(this);
        layoutOfPopup.setBackgroundColor(getResources().getColor(R.color.logo_yellow));
        statistics.setText("Level 1:\n"+correct[0]);
        statistics.setGravity(1);
        statistics.setPadding(0, 0, 0, 20);
        layoutOfPopup.addView(statistics);
    }

    public void popupInit() {


        popupStats = new PopupWindow(layoutOfPopup, LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        popupStats.setContentView(layoutOfPopup);
    }


    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];



        stats.getLocationOnScreen(location);


        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }



    private void showPopup(GameActivity gameActivity, Point p) {

        int OFFSET_X = 0;
        int OFFSET_Y = 250;

        popupStats.setWidth(300);
        popupStats.setHeight(300);
        popupStats.showAtLocation(layoutOfPopup, Gravity.CENTER, OFFSET_X, OFFSET_Y);


    }

    private boolean activityStopped;

    @Override
    protected void onStart() {
        activityStopped = false;
        super.onStart();
    }

    @Override
    protected void onStop(){
        timer.cancel();
        activityStopped = true;
        super.onStop();
    }



    @Override
    protected void onResume(){
        super.onResume();
        timer = new CountDownTimer(totalTime, 1000) {
            public void onTick(long millisUntilFinished) {
                totalTime = millisUntilFinished;
                timerText.setTextColor(GREEN);

                timerText.setText("Time: " + (millisUntilFinished / 1000));
                if((millisUntilFinished/1000)<=5){
                    timerText.setTextColor(RED);
                    countdown.start();
                }
            }

            public void onFinish() {
                if(activityStopped)
                    return;
                Toast.makeText(GameActivity.this, R.string.time_up, Toast.LENGTH_SHORT).show();
                scoreCounter -= 25;
                score.setText("Score: "+scoreCounter);
                totalTime = 31000;
                generateProblem();
            }
        };
        timer.start();

    }

    private void generateProblem(){
        //Check score to determine difficulty.
        if(scoreCounter >= 400 && !isEndlessMode){
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

        problem.startAnimation(AnimationUtils.loadAnimation(GameActivity.this, android.R.anim.fade_in));
        problem.setText(firstOperand + operationSign + secondOperand + " = ?");
        generateArrayOfPossibleAnswers();
        //Starts the timer
        timer.start();
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


                    Toast.makeText(GameActivity.this, R.string.correct_answer, Toast.LENGTH_SHORT).show();
                    woo.start();
                    correct[0]++;

                    scoreCounter += 50;
                    score.setText("Score: "+scoreCounter);
                    totalTime = 31000;
                    //correct. animate score up
                } else {
                    int sound = rand.nextInt(2);
                    switch (sound){
                        case 0:
                            wrong_answer.start();
                            break;
                        case 1:
                            sad_one.start();
                            break;


                    }

                    Toast.makeText(GameActivity.this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();
                    //wrong animate score down
                    wrong[0]++;
                    scoreCounter -=25;
                    score.setText("Score: " +scoreCounter);
                    totalTime = 31000;
                }
                generateProblem();
            }
        });
    }



}





