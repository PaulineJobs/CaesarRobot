package com.upssitech.caesarihm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements  View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonUp = (Button) findViewById(R.id.buttonUp);
        Button buttonDown = (Button) findViewById(R.id.buttonDown);
        Button buttonLeft = (Button) findViewById(R.id.buttonLeft);
        Button buttonRight= (Button) findViewById(R.id.buttonRight);
        buttonUp.setOnTouchListener(this);
        buttonDown.setOnTouchListener(this);
        buttonLeft.setOnTouchListener(this);
        buttonRight.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.buttonUp:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    Toast.makeText(MainActivity.this,"Up Pushed", Toast.LENGTH_SHORT).show();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    Toast.makeText(MainActivity.this,"Up Released", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.buttonDown:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    Toast.makeText(MainActivity.this,"Down Pushed", Toast.LENGTH_SHORT).show();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    Toast.makeText(MainActivity.this,"Down Released", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.buttonRight:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    Toast.makeText(MainActivity.this,"Right Pushed", Toast.LENGTH_SHORT).show();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    Toast.makeText(MainActivity.this,"Right Released", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.buttonLeft:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    Toast.makeText(MainActivity.this,"Left Pushed", Toast.LENGTH_SHORT).show();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    Toast.makeText(MainActivity.this,"Left Released", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return false;
    }
}