package com.interswitchng.interswitchpos.views.activities.loginflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.interswitchng.interswitchpos.R;
import com.interswitchng.interswitchpos.views.activities.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView forgotPass;
    private EditText username, password;
    //int roleId;
    Button login;

    //input validator
    //private InputValidation inputValidation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_landing);
        initViews();
        initListeners();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                loginUser();
                break;
            case R.id.forgotTxt:
                startActivity(new Intent(getApplicationContext(),ForgotPinActivity.class));
                finish();
                break;
        }
    }

    private void initViews() {
        forgotPass = findViewById(R.id.forgotTxt);
        login = findViewById(R.id.loginBtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    private void loginUser(){
        if(username == null || password == null){
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra("RoleID", roleId);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Login Failed! Enter Credentials to continue", Toast.LENGTH_LONG).show();
        }
    }

    private void initListeners(){
        login.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
    }
}