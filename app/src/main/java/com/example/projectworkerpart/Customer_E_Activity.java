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

//this class is for customer Record (opened by clicking on option menu item " my record" shown in customer dashboard)
public class Customer_E_Activity extends AppCompatActivity {

    String customerId;

    DatabaseHandler objectDatabaseHandler;
    SQLiteDatabase db;

    String dateAndTime;
    RecyclerView objectRecyclerView;
    //detail_W_RVAdapter is for when customer  want to see that how many workers he/she has hired with date/time and with rating
    detail_W_RVAdapter objectW_RvAdapter;

    int total_workers;
    TextView total_RV_items;

    byte[] Wimage;
    Bitmap objectBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__e_);

        customerId=getIntent().getStringExtra("CustomerID");
        objectRecyclerView = findViewById(R.id.RecycleViewNew);
        total_RV_items=findViewById(R.id.totalitems);



        try {
            objectDatabaseHandler = new DatabaseHandler(this);
            db = objectDatabaseHandler.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        try {
//          SearchExpierence(customerId) have arraylist and then we passed it in detail_W_RVAdapter Constructor
            objectW_RvAdapter = new detail_W_RVAdapter(SearchExpierence(customerId),this);
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

    //search  customer email from table TABLE_NAME_RECORD first to check that whether customer hire any worker or not if hire then
    // retrieve worker id , date/time and rating on that cursor count .
    public ArrayList SearchExpierence(String email)
    {

        db = objectDatabaseHandler.getWritableDatabase();
       // we have make class object arraylist to save record for customer
        ArrayList<NewWorkerModelList> objectNMCList = new ArrayList<>();
        String workerEmailId;
        String workerRating;

        //search worker ids by customer id from table TABLE_NAME_RECORD

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


                //search  name and job name by worker id from  worker info table' TABLE_NAME'
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
                        //we saved required  data on unique index of array list
                        objectNMCList.add(new NewWorkerModelList(nameoofWorker,jobCatogory, dateAndTime,objectBitmap,workerRating));
                    }
                } else {
                    Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            db.close();
            //returning arraylist
            return objectNMCList;
        }
        else
        {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();
            return null;

        }
    }
}
