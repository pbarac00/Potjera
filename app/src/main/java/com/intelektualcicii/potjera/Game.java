package com.intelektualcicii.potjera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {

    TextView questionText_tv,timer_tv;
    Button ans1_bt, ans2_bt, ans3_bt, ans4_bt;
    ColorStateList textColorDefaultBt;
    List<Question> questionsLists= new ArrayList<>();
    int questionCounter,questionCountTotal ,score, answered;

    Question currentQuestion;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questionText_tv=findViewById(R.id.tv_questionText_Game);
        ans1_bt=findViewById(R.id.bt_ans1_Game);
        ans2_bt=findViewById(R.id.bt_ans2_Game);
        ans3_bt=findViewById(R.id.bt_ans3_Game);
        ans4_bt=findViewById(R.id.bt_ans4_Game);
        timer_tv=findViewById(R.id.tv_timer_Game);

        textColorDefaultBt=ans1_bt.getTextColors();



        {
            // dohvaca pitanja iz baze i stavlja ih u listu <Qestions> questionLiists
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("QuestionList");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        Question question= dataSnapshot.getValue(Question.class);
                        questionsLists.add(question);
                    }

                    questionCountTotal= questionsLists.size();
                    questionCounter=0;

                    Collections.shuffle(questionsLists);
                    showNextQuestion();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Game.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showNextQuestion() {
        ans1_bt.setTextColor(textColorDefaultBt);
        ans2_bt.setTextColor(textColorDefaultBt);
        ans3_bt.setTextColor(textColorDefaultBt);
        ans4_bt.setTextColor(textColorDefaultBt);

        Log.d("valueOfquestionCounter", "showNextQuestion: "+ questionCounter);
        Log.d("ccc", "showNextQuestion: "+ questionCountTotal);

        if (questionCounter<questionCountTotal)
        {
            currentQuestion=questionsLists.get(questionCounter);
            Log.d("valueOfcurrentQuestion", "showNextQuestion: "+ currentQuestion);
            Log.d("valueOfquestionsLists", "showNextQuestion: "+ questionsLists.get(questionCounter) );
            Log.d("valueOfcurrentQuestion.questionText", "showNextQuestion: "+ currentQuestion.questionText);

            questionText_tv.setText(currentQuestion.questionText);
            ans1_bt.setText(currentQuestion.answ1);
            ans2_bt.setText(currentQuestion.answ2);
            ans3_bt.setText(currentQuestion.answ3);
            ans4_bt.setText(currentQuestion.answ4);

            questionCounter++;
        }
        else{
            finishQuiz();
        }
    }

    private void finishQuiz() {
       finish();
    }

}