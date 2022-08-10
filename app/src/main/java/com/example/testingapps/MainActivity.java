package com.example.testingapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.testingapps.view.MenuUtamaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(){
            public void run (){
                try {
                    sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(MainActivity.this, MenuUtamaActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}