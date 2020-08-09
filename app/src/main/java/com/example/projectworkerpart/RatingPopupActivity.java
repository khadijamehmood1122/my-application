package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

//this is simply used for rating
//customer rate worker
public class RatingPopupActivity extends AppCompatActivity {
    private RatingBar rBar;
    private TextView tView;
    private ImageView btn;

    String rating=" ";

    String worker_name;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_popup);

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
    //insertion in TABLE_NAME_RECORD is only done by worker when he click on JOB done button
    //so we have to pass rating of worker from this activity to worker dashboard so we use shared preferences for this functionality
    public void submitrating(View view)
    {
        SharedPreferences mPrefs2 = getSharedPreferences("IDvalue2", 0);
        //Give any name for //preference as I have given "IDvalue" and value 0.
        SharedPreferences.Editor editor2 = mPrefs2.edit();
        editor2.putString("Wrating",rating);

       // editor.putString("CID",customerLoginEmailId );
        // give key value as "sound" you mentioned and value what you // to want give as "1" in you mentioned
        editor2.commit();

        Toast.makeText(getApplicationContext(),"Rating submitted",Toast.LENGTH_SHORT).show();
    }
}
