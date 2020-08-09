package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.projectworkerpart.DatabaseContract.JobRecord.TABLE_NAME_RECORD;
import static com.example.projectworkerpart.DatabaseContract.Users.TABLE_NAME;
import static com.example.projectworkerpart.DatabaseContract.UsersC.TABLE_NAME_CUSTOMER;

//worker Record activity contains worker information and his/her experience record
//this activity is open by admin
public class WorkerRecordActivity extends AppCompatActivity {


    ImageView WorkerDp;
    TextView WorkerName,JobCatogory,workerEmail,WAddress,Wphone,WDes;

    byte[] Wimage;
    String S_worker_name,S_WjobCatogory,S_Wemail,S_WAddress,S_Wphone,S_WDes;
    Bitmap objectBitmap;

    DatabaseHandler objectDatabaseHandler;
    SQLiteDatabase db;

    String dateAndTime;
    RecyclerView objectRecyclerView;
    detail_C_RVAdapter objectC_RvAdapter;

    int total_customers;
    TextView total_C_RV_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_record);

        S_worker_name = getIntent().getStringExtra("workername");
        WorkerDp = findViewById(R.id.Worker_dp);
        WorkerName = findViewById(R.id.workerName);
        JobCatogory = findViewById(R.id.job);
        workerEmail = findViewById(R.id.workerEmail);
        WAddress = findViewById(R.id.CustomerAddress);
        Wphone = findViewById(R.id.customerPhone);
        WDes = findViewById(R.id.Workerdescription);
        total_C_RV_items=findViewById(R.id.totalCustomer);
        objectRecyclerView = findViewById(R.id.small_recyclerview);

        try {
            objectDatabaseHandler = new DatabaseHandler(this);
            db = objectDatabaseHandler.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        WorkerName.setText(S_worker_name);
        SearchDetails(S_worker_name);

        try {

            objectC_RvAdapter = new detail_C_RVAdapter(SearchExpierence(S_Wemail), this);
            objectRecyclerView.setAdapter(objectC_RvAdapter);
            total_customers=objectC_RvAdapter.getItemCount();
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
//      to display total number of items
        total_C_RV_items.setText("Total Customer : "+total_customers);



    }
    //searching info of worker from TABLE_NAME
    public void SearchDetails(String name)
    {
        String[] columns = {DatabaseContract.Users._ID, DatabaseContract.Users.COL_FULLNAME, DatabaseContract.Users.COL_EMAIL, DatabaseContract.Users.COL_PASSWORD, DatabaseContract.Users.COL_CONFIRMPASSWORD,
                DatabaseContract.Users.COL_CNIC, DatabaseContract.Users.COL_GENDER, DatabaseContract.Users.COL_DOB, DatabaseContract.Users.COL_ADDRESS, DatabaseContract.Users.COL_PHONENUMBER, DatabaseContract.Users.COL_CITY
                , DatabaseContract.Users.COL_JOB, DatabaseContract.Users.COL_DESCRIPTION, DatabaseContract.Users.COL_IMAGE};
        String sortOrder = DatabaseContract.Users._ID + " ASC";
        Cursor objectCursor = db.query(TABLE_NAME, columns, DatabaseContract.Users.COL_FULLNAME + "  =?",
                new String[]{name}, null, null, sortOrder);
        if (objectCursor.getCount() != 0) {
            while (objectCursor.moveToNext()) {
                String nameofImg = objectCursor.getString(1);
                S_WjobCatogory=objectCursor.getString(11);
                S_Wemail=objectCursor.getString(2);
                S_WAddress=objectCursor.getString(8);
                S_Wphone=objectCursor.getString(9);
                S_WDes=objectCursor.getString(12);
                Wimage = objectCursor.getBlob(13);
                objectBitmap = BitmapFactory.decodeByteArray(Wimage, 0, Wimage.length);

            }
            JobCatogory.setText(S_WjobCatogory);
            workerEmail.setText(S_Wemail);
            WAddress.setText(S_WAddress);
            Wphone.setText(S_Wphone);
            WDes.setText(S_WDes);
            WorkerDp.setImageBitmap(objectBitmap);
            db.close();


        } else {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();

        }


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
            return objectNCMCList;
        }
        else
            {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();
            return null;

        }

    }
}
