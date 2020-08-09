package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.projectworkerpart.DatabaseContract.JobRecord.TABLE_NAME_RECORD;
import static com.example.projectworkerpart.DatabaseContract.Users.TABLE_NAME;
import static com.example.projectworkerpart.DatabaseContract.UsersC.TABLE_NAME_CUSTOMER;

public class CustomerRecordActivity extends AppCompatActivity {

    TextView customerName,customerCity,customerEmail,customerAddress,customerPhone;
    String S_customer_name,S_customer_City,S_customer_email,S_customer_Address,S_customer_phone;


    DatabaseHandler objectDatabaseHandler;
    SQLiteDatabase db;

    String dateAndTime;
    RecyclerView objectRecyclerView;
    detail_W_RVAdapter objectW_RvAdapter;

    int total_workers;
    TextView total_RV_items;

    byte[] Wimage;
    Bitmap objectBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_record);

        S_customer_name = getIntent().getStringExtra("customerName");

        customerName=findViewById(R.id.customerName);
        customerCity=findViewById(R.id.customerCity);
        customerEmail=findViewById(R.id.customerCity);
        customerCity=findViewById(R.id.customerEmail);
        customerAddress=findViewById(R.id.CustomerAddress);
        customerPhone=findViewById(R.id.customerPhone);

        objectRecyclerView = findViewById(R.id.small_recyclerview2);
        total_RV_items=findViewById(R.id.totalWorkers);



        try {
            objectDatabaseHandler = new DatabaseHandler(this);
            db = objectDatabaseHandler.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        customerName.setText(S_customer_name);
        SearchDetails(S_customer_name);

        try {

            objectW_RvAdapter = new detail_W_RVAdapter(SearchExpierence(S_customer_email),this);
            objectRecyclerView.setAdapter(objectW_RvAdapter);
            total_workers=objectW_RvAdapter.getItemCount();
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        total_RV_items.setText("Total Workers : "+total_workers);
    }

    public void SearchDetails(String name)
    {
        String[] columns = {DatabaseContract.UsersC._ID, DatabaseContract.UsersC.COL_FULLNAME_C, DatabaseContract.UsersC.COL_EMAIL_C, DatabaseContract.UsersC.COL_PASSWORD_C, DatabaseContract.UsersC.COL_CONFIRMPASSWORD_C,
                DatabaseContract.UsersC.COL_ADDRESS_C, DatabaseContract.UsersC.COL_PHONENUMBER_C, DatabaseContract.UsersC.COL_CITY_C};
        String sortOrder = DatabaseContract.UsersC._ID + " ASC";
        Cursor objectCursor = db.query(TABLE_NAME_CUSTOMER, columns, DatabaseContract.UsersC.COL_FULLNAME_C + "  =?",
                new String[]{name}, null, null, sortOrder);
        if (objectCursor.getCount() != 0) {
            while (objectCursor.moveToNext()) {

                S_customer_City=objectCursor.getString(7);
                S_customer_email=objectCursor.getString(2);
                S_customer_Address=objectCursor.getString(5);
                S_customer_phone=objectCursor.getString(6);

            }
            customerEmail.setText(S_customer_email);
            customerAddress.setText(S_customer_Address);
            customerCity.setText(S_customer_City);
            customerPhone.setText(S_customer_phone);
            db.close();


        } else {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();

        }


    }
    //search  customer email from table TABLE_NAME_RECORD first to check that whether customer hire any worker or not if hire then
    // retrieve worker id , date/time and rating on that cursor count .
    public ArrayList SearchExpierence(String email)
    {

        db = objectDatabaseHandler.getWritableDatabase();
        // we have make class object arraylist to save record for customer
        ArrayList<NewWorkerModelList> objectNMCList = new ArrayList<>();
        String workerEmailId;
        String workerRating;

        //search customer ids by worker id from table TABLE_NAME_RECORD

        String[] columns = {DatabaseContract.JobRecord._ID, DatabaseContract.JobRecord.COL_WORKER_ID, DatabaseContract.JobRecord.COL_CUSTOMER_ID, DatabaseContract.JobRecord.COL_DATE_TIME
        ,DatabaseContract.JobRecord.COL_WORKER_RATING,DatabaseContract.JobRecord.COL_CUSTOMER_RATING};
        String sortOrder = DatabaseContract.JobRecord._ID + " ASC";
        Cursor objectCursor = db.query(TABLE_NAME_RECORD, columns, DatabaseContract.JobRecord.COL_CUSTOMER_ID + "  =?",
                new String[]{email}, null, null, sortOrder);
        if (objectCursor.getCount() != 0) {
            while (objectCursor.moveToNext()) {
                workerEmailId = objectCursor.getString(1);
                workerRating = objectCursor.getString(4);
                dateAndTime = objectCursor.getString(3);


                //search  name and job name by worker id from table TABLE_NAME_CUSTOMER
                String[] columnsWorker = {DatabaseContract.Users._ID, DatabaseContract.Users.COL_FULLNAME, DatabaseContract.Users.COL_EMAIL, DatabaseContract.Users.COL_PASSWORD, DatabaseContract.Users.COL_CONFIRMPASSWORD,
                        DatabaseContract.Users.COL_CNIC, DatabaseContract.Users.COL_GENDER, DatabaseContract.Users.COL_DOB, DatabaseContract.Users.COL_ADDRESS, DatabaseContract.Users.COL_PHONENUMBER, DatabaseContract.Users.COL_CITY
                        , DatabaseContract.Users.COL_JOB, DatabaseContract.Users.COL_DESCRIPTION, DatabaseContract.Users.COL_IMAGE};
                String sortOrderW = DatabaseContract.Users._ID + " ASC";
                Cursor objectCursorW = db.query(TABLE_NAME, columnsWorker, DatabaseContract.Users.COL_EMAIL + "  =?",
                        new String[]{workerEmailId}, null, null, sortOrderW);
                if (objectCursorW.getCount() != 0) {
                    while (objectCursorW.moveToNext()) {
                        String nameoofWorker = objectCursorW.getString(1);
                        String jobCatogory = objectCursorW.getString(11);
                        Wimage = objectCursorW.getBlob(13);
                        objectBitmap = BitmapFactory.decodeByteArray(Wimage, 0, Wimage.length);
                        objectNMCList.add(new NewWorkerModelList(nameoofWorker,jobCatogory, dateAndTime,objectBitmap,workerRating));
                    }
                } else {
                    Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            db.close();
            return objectNMCList;
        }
        else
        {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();
            return null;

        }
    }
}
