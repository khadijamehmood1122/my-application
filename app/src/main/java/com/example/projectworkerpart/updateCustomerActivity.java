package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_ADDRESS_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_CITY_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_CONFIRMPASSWORD_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_FULLNAME_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_PASSWORD_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_PHONENUMBER_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.TABLE_NAME_CUSTOMER;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import static com.example.projectworkerpart.DatabaseContract.Users.COL_PHONENUMBER;
import static com.example.projectworkerpart.DatabaseContract.Users.TABLE_NAME;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_ADDRESS_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_CITY_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_CONFIRMPASSWORD_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_FULLNAME_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_PASSWORD_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.COL_PHONENUMBER_C;
import static com.example.projectworkerpart.DatabaseContract.UsersC.TABLE_NAME_CUSTOMER;

//this activity is used for updating customer inf
//this activity is opened by clicking on option menu item"update info " shown in "customerdashboard" activity
public class updateCustomerActivity extends AppCompatActivity {


        private TextInputLayout textInputEmail2;
        private TextInputLayout textInputUsername2;
        private TextInputLayout textInputPassword2;
        private TextInputLayout textInputCPassword2;
        private TextInputLayout textInputCity2;
        private TextInputLayout textInputAddress2;
        private TextInputLayout textInputNumber2;
        SQLiteDatabase db;
        DatabaseHandler myDb;
        String name, phone, pass, cpass, city, address;

        DatabaseHandler objectDB;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_customer);
            objectDB = new DatabaseHandler(this);
            textInputEmail2 = findViewById(R.id.text_input_email2);
            textInputUsername2 = findViewById(R.id.text_input_username2);
            textInputPassword2 = findViewById(R.id.text_input_password2);
            textInputCPassword2 = findViewById(R.id.text_input_Confirmpassword2);
            textInputCity2 = findViewById(R.id.text_input_city2);
            textInputAddress2 = findViewById(R.id.text_input_address2);
            textInputNumber2 = findViewById(R.id.text_input_Phone2);
        }

        public void Update(View v) {
            myDb = new DatabaseHandler(this);
            db = myDb.getWritableDatabase();
            name = textInputUsername2.getEditText().getText().toString().trim();
            phone = textInputNumber2.getEditText().getText().toString().trim();
            pass = textInputPassword2.getEditText().getText().toString().trim();
            cpass = textInputCPassword2.getEditText().getText().toString().trim();
            city = textInputCity2.getEditText().getText().toString().trim();
            address = textInputAddress2.getEditText().getText().toString().trim();

            ContentValues args = new ContentValues();
            args.put(COL_PHONENUMBER_C, phone);
            args.put(COL_ADDRESS_C, address);
            args.put(COL_CITY_C, city);
            args.put(COL_PASSWORD_C, pass);
            args.put(COL_CONFIRMPASSWORD_C, cpass);
            String[] wherearg = {name};
            Integer count = db.update(TABLE_NAME_CUSTOMER, args, COL_FULLNAME_C + " = ?", wherearg);
            if (count > 0) {
                Toast.makeText(this, count + "  Records updated: ", Toast.LENGTH_SHORT).show();

            }

        }
    }
