package com.intelektualcicii.potjera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Game_SaveResultActivity extends AppCompatActivity implements View.OnClickListener {

    TextView scoreValue_tv;
    Button saveResult_bt, dismissResult_bt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_save_result);



        scoreValue_tv=findViewById(R.id.tv_score_value_Game_SaveResult);
        String score = getIntent().getExtras().getString("score","0");
        scoreValue_tv.setText(score);

        saveResult_bt=findViewById(R.id.bt_saveResult_Game_SaveResult);
        dismissResult_bt=findViewById(R.id.bt_dismissResult_Game_SaveResult);

        saveResult_bt.setOnClickListener(this);
        dismissResult_bt.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_saveResult_Game_SaveResult:
                saveGameResult();
                startActivity(new Intent(this, UserProfileActivity.class));
                this.finish();
                break;
            case  R.id.bt_dismissResult_Game_SaveResult:
                startActivity(new Intent(this, UserProfileActivity.class));
                this.finish();
                break;
        }

    }

    private void saveGameResult() {



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();


        User user2=new User();
        String score = getIntent().getExtras().getString("score","0");

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile= snapshot.getValue(User.class);
                if (userProfile != null){
                    //ako je vec igrao kviz, postoji vec neki rezultat koji se dohvaca, sprema se na kraj niza rezultat nove igre te
                    //se sprema nova lista
                    if (userProfile.gamesList!= null)
                    {
                        user2.gamesList=userProfile.gamesList;
                        user2.addResultOnEndOfGameList(score);
                        reference.child(userID).child("gamesList").setValue(user2.gamesList);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Game_SaveResultActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


        }





}