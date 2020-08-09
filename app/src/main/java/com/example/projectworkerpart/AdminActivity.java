package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
//Admin dashboard
public class AdminActivity extends AppCompatActivity {

    Spinner spinner;
    String[] names;
    Button allworkers;
    Button allcustomers,workerInfo;
    Button jobCatogory;
    Button passCatogory;
    String job_catogory;
    Boolean checkSelection=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //initializing objects
        spinner = findViewById(R.id.jobSpinner);
        names = getResources().getStringArray(R.array.JobNames);
        jobCatogory=findViewById(R.id.Catogory);
        allcustomers=findViewById(R.id.AllCustomer);
        allworkers=findViewById(R.id.Allworkers);
        passCatogory=findViewById(R.id.passCatogory);
        workerInfo=findViewById(R.id.workerInfo);

        //spinner for selecting job catogory specificaly
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                job_catogory = names[position];
                //for set text of button
                passCatogory.setText("Get "+job_catogory+" Records");
            }
                @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                checkSelection=true;
            }
        });

    }
    public void get_Info_by_jobCatogory(View view) {
        Intent intent = new Intent(getApplicationContext(), RecordsActivity.class);
        intent.putExtra("check",job_catogory);
        startActivity(intent);
    }


    public void showSpinner(View view) {
        spinner.setVisibility(View.VISIBLE);
      if(!checkSelection)
      {
          passCatogory.setVisibility(View.VISIBLE);

      }
        allworkers.setVisibility(View.INVISIBLE);
    }


    public void showOptions(View view) {
        allworkers.setVisibility(View.VISIBLE);
        jobCatogory.setVisibility(View.VISIBLE);
    }

    public void showAllWorkersRecord(View view)
    {
        jobCatogory.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getApplicationContext(), RecordsActivity.class);
        intent.putExtra("check","all_worker");
        startActivity(intent);
    }
    public void showAllCustomersRecord(View view)
    {
        jobCatogory.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getApplicationContext(), RecordsActivity.class);
        intent.putExtra("check","all_customers");
        startActivity(intent);
    }
}
//on button click we set other button invisible so that intent will have only one value otherwise user can
//click both button and Record activity will have problem to check multiple values