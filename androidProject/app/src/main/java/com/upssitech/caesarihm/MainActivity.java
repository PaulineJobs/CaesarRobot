package com.upssitech.caesarihm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonUp = (Button) findViewById(R.id.buttonUp);
        Button buttonDown = (Button) findViewById(R.id.buttonDown);
        Button buttonLeft = (Button) findViewById(R.id.buttonLeft);
        Button buttonRight= (Button) findViewById(R.id.buttonRight);
        buttonUp.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //TODO Auto-generated method stub
        switch(v.getId())
        {
            case R.id.buttonUp:
                Toast.makeText(MainActivity.this,"avant", 1000).show();
                break;
            case R.id.buttonDown:
                Toast.makeText(MainActivity.this,"arriere", 1000).show();
                break;
            case R.id.buttonLeft:
                Toast.makeText(MainActivity.this,"gauche", 1000).show();
                break;
            case R.id.buttonRight:
                Toast.makeText(MainActivity.this,"droite", 1000).show();
                break;
        }

    }
}