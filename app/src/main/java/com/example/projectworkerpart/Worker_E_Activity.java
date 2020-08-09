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

//this class is for worker Record (opened by clicking on option menu item " my record" shown in worker dashboard)
public class Worker_E_Activity extends AppCompatActivity {

    String workerLoginEmailId;

    DatabaseHandler objectDatabaseHandler;
    SQLiteDatabase db;

    String dateAndTime;
    RecyclerView objectRecyclerView;
    //detail_C_RVAdapter is for when worker want to see that how many customers he/she has hired by with date/time and with rating
    detail_C_RVAdapter objectW_RvAdapter;

    int total_workers;
    TextView total_RV_items;

    byte[] Wimage;
    Bitmap objectBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker__e_);

        workerLoginEmailId=getIntent().getStringExtra("WID");
        objectRecyclerView = findViewById(R.id.RecycleView);
        total_RV_items=findViewById(R.id.totalitems);

        try {
            objectDatabaseHandler = new DatabaseHandler(this);
            db = objectDatabaseHandler.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
//SearchExpierence(workerLoginEmailId) have arraylist and then we passed it in detail_C_RVAdapter Constructor
            objectW_RvAdapter = new detail_C_RVAdapter(SearchExpierence(workerLoginEmailId),this);
            objectRecyclerView.setAdapter(objectW_RvAdapter);
            total_workers=objectW_RvAdapter.getItemCount();
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        total_RV_items.setText("Total Customers : "+total_workers);
    }
    //search  worker email from table TABLE_NAME_RECORD first to check that whether worker is hire by any customer or not if hired then
    // retrieve customer id , date/time and rating on that cursor count .
    public ArrayList SearchExpierence(String email) {
        db = objectDatabaseHandler.getWritableDatabase();
        ArrayList<detailWorkerRecord> objectNCMCList = new ArrayList<>();
        String customerEmailId;
        String customerRating;

        //search customer ids by worker id from table TABLE_NAME_RECORD
        String[] columns = {DatabaseContract.JobRecord._ID, DatabaseContract.JobRecord.COL_WORKER_ID, DatabaseContract.JobRecord.COL_CUSTOMER_ID, DatabaseContract.JobRecord.COL_DATE_TIME
                ,DatabaseContract.JobRecord.COL_WORKER_RATING,DatabaseContract.JobRecord.COL_CUSTOMER_RATING};
        String sortOrder = DatabaseContract.JobRecord._ID + " ASC";
        Cursor objectCursor = db.query(TABLE_NAME_RECORD, columns, DatabaseContract.JobRecord.COL_WORKER_ID + "  =?",
                new String[]{email}, null, null, sortOrder);

        if (objectCursor.getCount() != 0) {
            while (objectCursor.moveToNext()) {
                customerEmailId = objectCursor.getString(2);
                dateAndTime = objectCursor.getString(3);
                customerRating= objectCursor.getString(5);

                //search  name and city name by customer id from table TABLE_NAME_CUSTOMER
                String[] columnsCustomer = {DatabaseContract.UsersC._ID, DatabaseContract.UsersC.COL_FULLNAME_C, DatabaseContract.UsersC.COL_EMAIL_C, DatabaseContract.UsersC.COL_PASSWORD_C, DatabaseContract.UsersC.COL_CONFIRMPASSWORD_C,
                        DatabaseContract.UsersC.COL_ADDRESS_C, DatabaseContract.UsersC.COL_PHONENUMBER_C, DatabaseContract.UsersC.COL_CITY_C};
                String sortOrderC = DatabaseContract.UsersC._ID + " ASC";
                Cursor objectCursorC = db.query(TABLE_NAME_CUSTOMER, columnsCustomer, DatabaseContract.UsersC.COL_EMAIL_C + "  =?",
                        new String[]{customerEmailId}, null, null, sortOrderC);

                if (objectCursorC.getCount() != 0) {
                    while (objectCursorC.moveToNext()) {
                        String nameoofCustomer = objectCursorC.getString(1);
                        String nameofCity = objectCursorC.getString(7);

                        objectNCMCList.add(new detailWorkerRecord(nameoofCustomer, nameofCity, dateAndTime,customerRating));
                    }


                } else {
                    Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();
                    return null;
                }

            }
            db.close();
            //returning arraylist
            return objectNCMCList;
        }
        else
        {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();
            return null;

        }

    }

}
