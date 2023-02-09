package com.intelektualcicii.potjera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView questionText_tv, timer_tv, score_tv;
    Button ans1_bt, ans2_bt, ans3_bt, ans4_bt;
    boolean timerIsNotRuning;
    List<Question> questionsLists = new ArrayList<>();
    int questionCounter, questionCountTotal,score, answeredButton;
    private static final long COUNTDOWN_IN_MS = 30020; // 30 sec and 20 ms
    private long timeLeftInMs;
    private CountDownTimer countDownTimer;


    Question currentQuestion;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questionText_tv = findViewById(R.id.tv_questionText_Game);
        ans1_bt = findViewById(R.id.bt_ans1_Game);
        ans2_bt = findViewById(R.id.bt_ans2_Game);
        ans3_bt = findViewById(R.id.bt_ans3_Game);
        ans4_bt = findViewById(R.id.bt_ans4_Game);
        timer_tv = findViewById(R.id.tv_timer_Game);
        score_tv=findViewById(R.id.tv_score_Game);



        {
            // dohvaca pitanja iz baze i stavlja ih u listu <Qestions> questionLiists
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("QuestionList");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Question question = dataSnapshot.getValue(Question.class);
                        questionsLists.add(question);
                    }
                    questionCountTotal = questionsLists.size();
                    questionCounter = 0;
                    score=0;
                    Collections.shuffle(questionsLists);
                    showNextQuestion();
                    startCountDown();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(GameActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showNextQuestion() {
        ans1_bt.setBackgroundColor(Color.GRAY);
        ans2_bt.setBackgroundColor(Color.GRAY);
        ans3_bt.setBackgroundColor(Color.GRAY);
        ans4_bt.setBackgroundColor(Color.GRAY);

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionsLists.get(questionCounter);

            score_tv.setText("Score: "+score);
            questionText_tv.setText(currentQuestion.questionText);
            ans1_bt.setText(currentQuestion.answ1);
            ans2_bt.setText(currentQuestion.answ2);
            ans3_bt.setText(currentQuestion.answ3);
            ans4_bt.setText(currentQuestion.answ4);

            ans1_bt.setOnClickListener(this);
            ans2_bt.setOnClickListener(this);
            ans3_bt.setOnClickListener(this);
            ans4_bt.setOnClickListener(this);

            timeLeftInMs=COUNTDOWN_IN_MS;

        } else {
            finishGame();
        }
    }

    private void startCountDown() {
        timerIsNotRuning=false;
        countDownTimer = new CountDownTimer(timeLeftInMs,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMs=millisUntilFinished;
                updateCoundownTimerTextView();
            }
            @Override
            public void onFinish() {
                timeLeftInMs=0;
                finishGame();
            }
        }.start();
        }

    private void updateCoundownTimerTextView() {
        int minutes= (int)(timeLeftInMs/1000)/60;
        int seconds= (int) (timeLeftInMs/1000)%60;
        String timeLeft= String.format(Locale.getDefault(), "%02d:%02d", minutes,seconds);
        timer_tv.setText(timeLeft);
    }

    private void finishGame() {
        Intent i =new Intent(GameActivity.this, Game_SaveResultActivity.class);
        i.putExtra("score", ""+score);
        startActivity(i);
        this.finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ans1_Game:
                showSolution(currentQuestion.answ1);
                break;
            case R.id.bt_ans2_Game:
                showSolution(currentQuestion.answ2);
                break;
            case R.id.bt_ans3_Game:
                showSolution(currentQuestion.answ3);
                break;
            case R.id.bt_ans4_Game:
                showSolution(currentQuestion.answ4);
                break;
        }
    }

    private void showSolution(String chosedQuestion) {

        if (chosedQuestion.equals(currentQuestion.correctAnsw))
        {
            //prikazi zeleni text i uvecaj counter;
            score+=500;
            questionCounter++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showNextQuestion();
                }
            }, 250);

        }
            else {
            //tocan odgovor postaje zelen, promini boje
               if (currentQuestion.correctAnsw.equals(currentQuestion.answ1) )
                   ans1_bt.setBackgroundColor(Color.GREEN);
               else if (currentQuestion.correctAnsw.equals(currentQuestion.answ2))
                   ans2_bt.setBackgroundColor(Color.GREEN);
               else if (currentQuestion.correctAnsw.equals(currentQuestion.answ3))
                   ans3_bt.setBackgroundColor(Color.GREEN);
               else if (currentQuestion.correctAnsw.equals(currentQuestion.answ4))
                   ans4_bt.setBackgroundColor(Color.GREEN);

            questionCounter++;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showNextQuestion();
                }
            }, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        timerIsNotRuning=true;
        countDownTimer.cancel();

        Log.d("onPauseCalled","onPauseCalled");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (timerIsNotRuning == true)
        {
            startCountDown();
        }

    }


}