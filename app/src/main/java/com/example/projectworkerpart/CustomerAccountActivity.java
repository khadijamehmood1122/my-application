package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.projectworkerpart.DatabaseContract.UsersC.TABLE_NAME_CUSTOMER;
//customer account activity is simply used for check portfolio of customer and it is called by option menu item "my account" shown in customer dashboard
public class CustomerAccountActivity extends AppCompatActivity {


    String Customer_Email_id;
    TextView customerName,customerCity,customerEmail,customerAddress,customerPhone;
    String S_customer_name,S_customer_City,S_customer_email,S_customer_Address,S_customer_phone;

    DatabaseHandler objectDatabaseHandler;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account);
        customerName=findViewById(R.id.customerName);
        customerCity=findViewById(R.id.customerCity);
        customerEmail=findViewById(R.id.customerCity);
        customerCity=findViewById(R.id.customerEmail);
        customerAddress=findViewById(R.id.CustomerAddress);
        customerPhone=findViewById(R.id.customerPhone);


        try {
            //initializing object
            objectDatabaseHandler = new DatabaseHandler(this);
            db = objectDatabaseHandler.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Customer_Email_id = getIntent().getStringExtra("cId");
        SearchDetails(Customer_Email_id);
    }
    // this function is simply used for retrieving customer info by passing email
    public void SearchDetails(String email)
    {
        String[] columns = {DatabaseContract.UsersC._ID, DatabaseContract.UsersC.COL_FULLNAME_C, DatabaseContract.UsersC.COL_EMAIL_C, DatabaseContract.UsersC.COL_PASSWORD_C, DatabaseContract.UsersC.COL_CONFIRMPASSWORD_C,
                DatabaseContract.UsersC.COL_ADDRESS_C, DatabaseContract.UsersC.COL_PHONENUMBER_C, DatabaseContract.UsersC.COL_CITY_C};
        String sortOrder = DatabaseContract.UsersC._ID + " ASC";
        Cursor objectCursor = db.query(TABLE_NAME_CUSTOMER, columns, DatabaseContract.UsersC.COL_EMAIL_C + "  =?",
                new String[]{email}, null, null, sortOrder);
        if (objectCursor.getCount() != 0) {
            while (objectCursor.moveToNext()) {

                S_customer_City=objectCursor.getString(7);
                S_customer_name=objectCursor.getString(1);
                S_customer_Address=objectCursor.getString(5);
                S_customer_phone=objectCursor.getString(6);

            }

            //setting  required info in textviews
            customerName.setText(S_customer_name);
            customerAddress.setText(S_customer_Address);
            customerCity.setText(S_customer_City);
            customerPhone.setText(S_customer_phone);
            db.close();


        } else {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();

        }


    }
}
