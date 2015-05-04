package com.turnup.cs389team6.activities;

import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.view.ViewGroup.LayoutParams;
import com.turnup.cs389team6.R;
import com.turnup.cs389team6.constants.Difficulty;
import com.turnup.cs389team6.constants.Operation;

import java.util.Random;

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
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedDifficulty = (Difficulty) getIntent().getSerializableExtra(MathOptionsActivity.EXTRA_DIFFICULTY);
        setContentView(R.layout.activity_game_layout);


        stats = (ImageView) findViewById(R.id.statistics);



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


        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p != null){
                    showPopup(GameActivity.this,p);

                }
            }
        });



       // timerText.startAnimation(AnimationUtils.loadAnimation(GameActivity.this, android.R.anim.slide_in_left));

        timer = new CountDownTimer(totalTime,1000){

            public void onTick(long millisUntilFinished){
                totalTime = millisUntilFinished;

                timerText.setText("Time: " + millisUntilFinished/1000);
            }
            public void onFinish(){
                sad.start();
                Toast.makeText(GameActivity.this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();

                scoreCounter -= 25;
                score.setText("Score: "+scoreCounter);
                totalTime = 31000;
                sad.stop();
                generateProblem();
            }
        };
        generateProblem();
    }




    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];


        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        stats.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }



    private void showPopup(GameActivity gameActivity, Point p) {

        int popUpWidth = 400;
        int popUpHeight = 350;

        LinearLayout viewGroup = (LinearLayout) gameActivity.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) gameActivity
                .getSystemService(gameActivity.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.stats, viewGroup);

        final PopupWindow popup = new PopupWindow(gameActivity);
        popup.setContentView(layout);
        popup.setWidth(popUpWidth);
        popup.setHeight(popUpHeight);
        popup.setFocusable(true);

        int OFFSET_X = 50;
        int OFFSET_Y = 50;

        popup.showAtLocation(layout, Gravity.CENTER, p.x + OFFSET_X, p.y + OFFSET_Y);




    }

    @Override
    protected void onStop(){
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onResume(){
        super.onResume();

        timer = new CountDownTimer(totalTime, 1000) {
            public void onTick(long millisUntilFinished) {
                totalTime = millisUntilFinished;
                timerText.setTextColor(Color.GREEN);

                timerText.setText("Time: " + (millisUntilFinished / 1000));
                if((millisUntilFinished/1000)<=7){
                    timerText.setTextColor(Color.RED);
                    countdown.start();
                }
            }

            public void onFinish() {
                Toast.makeText(GameActivity.this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();
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

                    woo.start();
                    Toast.makeText(GameActivity.this, R.string.correct_answer, Toast.LENGTH_SHORT).show();
                    view.setBackgroundColor(Color.GREEN);
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
                    scoreCounter -=25;
                    score.setText("Score: " +scoreCounter);
                    totalTime = 31000;
                }
                generateProblem();
            }
        });
    }



}





