package com.intelektualcicii.potjera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView appName, signUp;
    EditText email_et, password_et;
    Button logIn;

    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appName=findViewById(R.id.tv_appName);
        email_et=findViewById(R.id.et_email);
        password_et=findViewById(R.id.et_password);
        logIn=findViewById(R.id.bt_login);
        signUp=findViewById(R.id.tv_signUp);

        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_signUp:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.bt_login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email=email_et.getText().toString().trim();
        String password =password_et.getText().toString().trim();

        if (email.isEmpty()){
            email_et.setError("Email is required");
            email_et.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            password_et.setError("Password is required");
            password_et.requestFocus();
            return;
        }

        if(password.length()<8)
        {
            password_et.setError("Password must contain minimum 8 characters");
            password_et.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //check is user verified his email
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, UserProfile.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "You need to verify your email to start using application", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}