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
        Button boutonUp = (Button) findViewById(R.id.buttonUp);
        Button boutonDown = (Button) findViewById(R.id.buttonDown);
        Button boutonLeft = (Button) findViewById(R.id.buttonLeft);
        Button boutonRight= (Button) findViewById(R.id.buttonRight);
        boutonUp.setOnClickListener(this);
        boutonDown.setOnClickListener(this);
        boutonLeft.setOnClickListener(this);
        boutonRight.setOnClickListener(this);

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
    /*public class MainActivity extends AppCompatActivity {
    //attributs
    boolean clicUp = false;
    boolean clicDown = false;
    boolean clicLeft = false;
    boolean clicRight = false;

    //methode
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boutonUp = (Button) findViewById(R.id.buttonUp);
        boutonUp.set

                (v -> {
            clicUp = true;
        });
        boutonUp.setOnClickListener(v -> {
            clicUp = false;
        });




        Button boutonDown = (Button) findViewById(R.id.buttonDown);
        boutonDown.setOnClickListener(v -> {
            clicDown = true;
        });
        boutonUp.setOnClickListener(v -> {
            clicDown = false;
        });
        Button boutonLeft = (Button) findViewById(R.id.buttonLeft);
        boutonLeft.setOnClickListener(v -> {
            clicLeft = true;
        });
        boutonUp.setOnClickListener(v -> {
            clicLeft = false;
        });
        Button boutonRight= (Button) findViewById(R.id.buttonRight);
        boutonRight.setOnClickListener(v -> {
            clicRight = true;
        });
        boutonRight.setOnClickListener(v -> {
            clicRight = false;
        });


    }

}*/
}