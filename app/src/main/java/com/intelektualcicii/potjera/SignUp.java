package com.intelektualcicii.potjera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    TextView activityName, logIn;
    EditText fullName_et, email_et, password_et;
    Button createAccount;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        activityName=findViewById(R.id.tv_signUp_SignUp);
        logIn=findViewById(R.id.tv_logIn_SignUp);
        fullName_et=findViewById(R.id.et_fullName_SignUp);
        email_et=findViewById(R.id.et_email_SignUp);
        password_et=findViewById(R.id.et_password_SignUp);
        createAccount=findViewById(R.id.bt_signUp_SignUp);
        progressBar=findViewById(R.id.pb_progressBar_signUp);

        mAuth = FirebaseAuth.getInstance();

        logIn.setOnClickListener(this);
        createAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_signUp_SignUp:
                signUpUser();
                break;
            case R.id.tv_logIn_SignUp:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void signUpUser() {
        String fullName= fullName_et.getText().toString().trim();
        String email=email_et.getText().toString().trim();
        String password=password_et.getText().toString();

        if (fullName.isEmpty())
        {
            fullName_et.setError("Full name is required");
            fullName_et.requestFocus();
            return;
        }

        if (email.isEmpty()){
            email_et.setError("Email is required");
            email_et.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_et.setError("Please enter valid email");
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
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                User user= new User(fullName,email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "User has been register successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(SignUp.this, "User has not been register successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(SignUp.this, "User has not been register successfully", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                }
        });



        }
    }
