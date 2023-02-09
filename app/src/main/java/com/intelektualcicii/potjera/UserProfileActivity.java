package com.intelektualcicii.potjera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private String id;
    TextView fullName_tv;
    Button top_10, my_result,startNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();

        fullName_tv=findViewById(R.id.tv_fullName_UP);
        top_10=findViewById(R.id.bt_top10_UP);
        my_result=findViewById(R.id.bt_myResult_UP);
        startNewGame=findViewById(R.id.br_startNewGame_UP);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile= snapshot.getValue(User.class);
                if (userProfile != null){
                    String fullName= userProfile.fullName;
                    fullName_tv.setText(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        top_10.setOnClickListener(this);
        my_result.setOnClickListener(this);
        startNewGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_top10_UP:
                startActivity(new Intent(this, Top10Activity.class));
                break;
            case R.id.br_startNewGame_UP:
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.bt_myResult_UP:
                startActivity(new Intent(this, UserGameResultsActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}