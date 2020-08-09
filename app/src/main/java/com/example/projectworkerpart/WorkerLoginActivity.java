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


public class WorkerLoginActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHandler objectDB;

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    String email1, pass, e, p;
    ImageView back_button;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_login);


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

                email1 = Name.getText().toString();
                pass = Password.getText().toString();
                Cursor c = db.query(DatabaseContract.Users.TABLE_NAME, null, null, null, null, null, null);
                while (c.moveToNext()) {
                    e = c.getString(2);
                    p = c.getString(3);
                    if (e.equalsIgnoreCase(email1)) {
                        if (p.equalsIgnoreCase(pass)) {
                            Intent intent = new Intent(WorkerLoginActivity.this, WorkerDashboard.class);
                            intent.putExtra("WorkerEmailId",email1);
                            startActivity(intent);
                            count++;
                            break;
                        }
                    }
                }
                if (count == 0) {
                    Toast.makeText(WorkerLoginActivity.this, "Invalid username and password ", Toast.LENGTH_SHORT).show();
                }
                c.close();

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerLoginActivity.this, SelectionActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}

