package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHandler objectDB;

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    String email, pass, e, p;
    ImageView back_button;
    String emailid="khadijamehmood1122@gmail.com";
    String Input_P="khadija1122";
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        objectDB =new DatabaseHandler(this);
        db = objectDB.getReadableDatabase();
        Name = (EditText) findViewById(R.id.et_Email);
        Password = (EditText) findViewById(R.id.et_pass);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        back_button = (ImageView) findViewById(R.id.back);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = Name.getText().toString();
                pass = Password.getText().toString();

                //checking for login
                if(email.equals(emailid)&& pass.equals(Input_P))
                {
                    Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                    intent.putExtra("CustomerEmailId",email);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext()," Invali username and password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLoginActivity.this, SelectionActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
