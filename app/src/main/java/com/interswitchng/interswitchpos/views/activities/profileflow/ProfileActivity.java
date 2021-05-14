package com.interswitchng.interswitchpos.views.activities.profileflow;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.interswitchng.interswitchpos.R;

public class ProfileActivity extends AppCompatActivity {

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_landing);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}