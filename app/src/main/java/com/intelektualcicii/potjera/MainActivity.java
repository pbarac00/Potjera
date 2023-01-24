package com.intelektualcicii.potjera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView appName, signUp;
    EditText email, password;
    Button logIn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appName=findViewById(R.id.tv_appName);
        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_password);
        logIn=findViewById(R.id.bt_login);
        signUp=findViewById(R.id.tv_signUp);

        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_signUp:
                startActivity(new Intent(this, SignUp.class));
                break;
        }
    }
}