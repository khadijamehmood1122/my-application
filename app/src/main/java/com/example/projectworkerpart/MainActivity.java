package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

//Splash activity with app name and logo
public class MainActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img=findViewById(R.id.imageView13);
        //img.animate().alpha(000).setDuration(5);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp=new Intent(MainActivity.this,SelectionActivity.class);
                startActivity(dsp);

            }
        }, 3000);
    }
}
