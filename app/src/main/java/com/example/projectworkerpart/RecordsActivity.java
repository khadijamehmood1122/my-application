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

import static com.example.projectworkerpart.DatabaseContract.Users.TABLE_NAME;

//RecordsActivity is called by admin activity
public class RecordsActivity extends AppCompatActivity {

    DatabaseHandler objectDatabaseHandler;
    RecyclerView objectRv;
    RVAdapter objectRvAdapter;
    C_RVAdapter objectC_RVAdapter;
    SQLiteDatabase db;
    String customerId="ab";
    String Activityname;
    String checkValue;
    TextView titems;
    String dateAndTime=" ";
    int total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);


        //we are using one activity for "All customers" ,"all workers"and for job category instead of
        // making new activities for every button so here we apply if else statements to check

        Activityname="recordsactivity";
        //for checking which value is coming fro admin activity
        checkValue= getIntent().getStringExtra("check");
        titems=findViewById(R.id.totalitems);

        try {
            objectRv = findViewById(R.id.RecycleView);
            objectDatabaseHandler = new DatabaseHandler(this);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            if(checkValue.equals("all_worker"))
            {
                objectRvAdapter = new RVAdapter(objectDatabaseHandler.getAllWorkersData(), this, customerId, Activityname);
                total=objectRvAdapter.getItemCount();
                titems.setText("Total Workers"+total);
                objectRv.setAdapter(objectRvAdapter);
            }
            else if(checkValue.equals("all_customers"))
            {
                objectC_RVAdapter = new C_RVAdapter(objectDatabaseHandler.getAllCustomersData(), this,dateAndTime);
                total=objectC_RVAdapter.getItemCount();
                titems.setText("Total Customers : "+total);
                objectRv.setAdapter(objectC_RVAdapter);
            }

           else
            {
                objectRvAdapter = new RVAdapter(SearchByJob(checkValue), this, customerId, Activityname);
                total=objectRvAdapter.getItemCount();
                titems.setText("Total "+ checkValue+ " :"+total);
                objectRv.setAdapter(objectRvAdapter);
            }
            objectRv.setHasFixedSize(true);

            objectRv.setLayoutManager(new LinearLayoutManager(this));


        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public ArrayList<NewModelClass> SearchByJob(String jobtitle) {
        db = objectDatabaseHandler.getWritableDatabase();
        ArrayList<NewModelClass> objectModelClassList = new ArrayList<>();
        String[] columns = {DatabaseContract.Users._ID, DatabaseContract.Users.COL_FULLNAME, DatabaseContract.Users.COL_EMAIL, DatabaseContract.Users.COL_PASSWORD, DatabaseContract.Users.COL_CONFIRMPASSWORD,
                DatabaseContract.Users.COL_CNIC, DatabaseContract.Users.COL_GENDER, DatabaseContract.Users.COL_DOB, DatabaseContract.Users.COL_ADDRESS, DatabaseContract.Users.COL_PHONENUMBER, DatabaseContract.Users.COL_CITY
                , DatabaseContract.Users.COL_JOB, DatabaseContract.Users.COL_DESCRIPTION, DatabaseContract.Users.COL_IMAGE};
        String sortOrder = DatabaseContract.Users._ID + " ASC";
        Cursor objectCursor = db.query(TABLE_NAME, columns, DatabaseContract.Users.COL_JOB + " LIKE ?",
                new String[]{jobtitle}, null, null, sortOrder);

        if (objectCursor.getCount() != 0) {
            while (objectCursor.moveToNext()) {
                String nameofImg = objectCursor.getString(1);
                String jobname = objectCursor.getString(11);
                byte[] imgageBytes = objectCursor.getBlob(13);

                Bitmap objectBitmap = BitmapFactory.decodeByteArray(imgageBytes, 0, imgageBytes.length);
                objectModelClassList.add(new NewModelClass(nameofImg,jobname,objectBitmap));

            }
            db.close();
            return objectModelClassList;

        } else {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();
            return null;
        }


    }
}

