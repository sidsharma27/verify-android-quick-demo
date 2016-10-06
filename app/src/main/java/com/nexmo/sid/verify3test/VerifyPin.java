package com.nexmo.sid.verify3test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class VerifyPin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_pin);

        Intent intent = getIntent();
        String pin = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Verified!");

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_verify_pin);
        layout.addView(textView);
    }
}
