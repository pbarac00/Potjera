package com.intelektualcicii.potjera;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Game_SaveResult extends AppCompatActivity implements View.OnClickListener {

    TextView scoreValue_tv;
    Button saveResult_bt, dismissResult_bt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_save_result);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

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
                Toast.makeText(this, "kliknuto save", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.bt_dismissResult_Game_SaveResult:
                Toast.makeText(this, "kliknuto dismiss", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserProfile.class));
                this.finish();
                break;
        }

    }
}