package com.intelektualcicii.potjera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import java.util.List;

public class Game extends AppCompatActivity {

    TextView questionText_tv;
    Button ans1_bt, ans2_bt, ans3_bt, ans4_bt;
    List<Question> questionsLists= new ArrayList<>();

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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("QuestionList");





        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                  Question question= dataSnapshot.getValue(Question.class);
                    questionsLists.add(question);
                }

//                Question question= new Question(getQuestion,getAns,getAns1,getAns2,getAns3,getAns4);
                    Log.d("PROVJERA", "onDataChange: "+ questionsLists.get(0));
                questionText_tv.setText(questionsLists.get(0).questionText);
                ans1_bt.setText(questionsLists.get(0).answ1);
                ans2_bt.setText(questionsLists.get(0).answ2);
                ans3_bt.setText(questionsLists.get(0).answ3);
                ans4_bt.setText(questionsLists.get(0).answ4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Game.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });
    }
}