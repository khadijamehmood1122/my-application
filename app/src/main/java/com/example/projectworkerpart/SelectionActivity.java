package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//selection activity is basic activity
public class SelectionActivity extends AppCompatActivity {

    Button WSignIn;
    Button CSignIn;
    TextView tWRegister;
    TextView tCRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);


        WSignIn= (Button)  findViewById(R.id.bSignIn2);
        CSignIn= (Button)  findViewById(R.id.bSignIn);
        tWRegister =(TextView) findViewById(R.id.tvWorkerRegister);
        tCRegister =(TextView) findViewById(R.id.tvCustomerRegister);
    }

    public void WSignIn(View v)
    {
        Intent intent = new Intent(SelectionActivity.this, WorkerLoginActivity.class);
        startActivity(intent);
        finish();

    }

    public void CSignIn(View v)
    {
        Intent intent = new Intent(SelectionActivity.this, CustomerLoginActivity.class);
        startActivity(intent);
        finish();

    }

    public void workerRegister(View v)
    {
        Intent intent2 = new Intent(SelectionActivity.this, WorkerRegisterActivity.class);
        startActivity(intent2);
        finish();

    }
    public void customerRegister(View v)
    {
        Intent intent2 = new Intent(SelectionActivity.this, CustomerRegisterActivity.class);
        startActivity(intent2);
        finish();

    }
    public void moveToAdminActivity(View v)
    {
        Intent intent3 = new Intent(SelectionActivity.this, AdminLoginActivity.class);
        startActivity(intent3);
        finish();

    }


}