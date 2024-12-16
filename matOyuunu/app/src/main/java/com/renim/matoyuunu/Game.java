package com.renim.matoyuunu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    TextView score, life, time, question;
    EditText answer;
    Button okay, next;
    Random random = new Random();
    int number1, number2;
    int userAnswrer, realAnswer;
    int userScore = 0;
    int userLife = 3;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS = 600000;
    Boolean timer_running;
    long time_left_in_milis = START_TIMER_IN_MILIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.textViewScor2);
        life = findViewById(R.id.textViewLife);
        time = findViewById(R.id.textViewTime);
        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextTextAnswre);
        okay = findViewById(R.id.buttonOkey);
        next = findViewById(R.id.buttonNext);

        gameContuniue();

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswrer = Integer.valueOf(answer.getText().toString());

                pauseTimer();

                if(userAnswrer==realAnswer){
                    userScore += 10;
                    score.setText("" + userScore);
                    question.setText("Congratulations, Your answer is true!");

                } else{
                    --userLife;
                    life.setText("" + userLife);
                    question.setText("Sorry, Your answer is wrong!");

                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answer.setText("");
                resetTimer();

                if(userLife==0){
                    Toast.makeText(getApplicationContext(),"Game Over",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Game.this,resultActivity.class);
                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();

                } else{
                    gameContuniue();

                }
                
            }
        });

    }

    public void gameContuniue(){
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);

        realAnswer = number1 + number2;

        question.setText(number1 + " + " + number2);
        startTimer();
    }

    public void startTimer(){
        timer = new CountDownTimer(time_left_in_milis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_milis = millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateTimer();
                --userLife;
                question.setText("Sorry! Time is up!");

            }
        }.start();

        timer_running = true;
    }

    public void pauseTimer(){
        timer.cancel();
        timer_running = false;

    }
    public void updateTimer(){
        int second = (int)(time_left_in_milis / 1000) % 60;
        String time_left = String.format(Locale.getDefault(), "%02d",second);
        time.setText(time_left);

    }
    public void resetTimer(){
        time_left_in_milis = START_TIMER_IN_MILIS;
        updateTimer();

    }
}