package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class customerRatingActivity extends AppCompatActivity {
    private RatingBar rBar;
    private TextView tView;
    private ImageView btn;

    String rating=" ";

    String worker_name;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rating);
        rBar = (RatingBar) findViewById(R.id.ratingBar1);
        tView = (TextView) findViewById(R.id.textview1);
        heading=findViewById(R.id.t5);

        btn = findViewById(R.id.btnGet);
        worker_name= getIntent().getStringExtra("Wname");

        heading.setText("Rate Your Customer "+worker_name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noofstars = rBar.getNumStars();
                float getrating = rBar.getRating();
                rating="Rating: "+getrating+"/"+noofstars;
                tView.setText(rating);
            }
        });
    }
    public void submitCrating(View view)
    {
        SharedPreferences mPrefs3 = getSharedPreferences("IDvalue3", 0);

        //Give any name for //preference as I have given "IDvalue" and value 0.
        SharedPreferences.Editor editor3 = mPrefs3.edit();

        editor3.putString("Crating",rating);

        // editor.putString("CID",customerLoginEmailId );
        // give key value as "sound" you mentioned and value what you // to want give as "1" in you mentioned
        editor3.commit();

        Toast.makeText(getApplicationContext(),"Rating submitted",Toast.LENGTH_SHORT).show();

        Intent intent =new Intent(this,WorkerDashboard.class);
        startActivity(intent);

    }
}
