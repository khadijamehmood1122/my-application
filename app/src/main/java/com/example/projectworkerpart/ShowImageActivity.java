package com.example.projectworkerpart;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.projectworkerpart.DatabaseContract.Users.TABLE_NAME;

//this class is used for displaying workers with same job

public class ShowImageActivity extends AppCompatActivity {

    DatabaseHandler objectDatabaseHandler;
    RecyclerView objectRv;
    RVAdapter objectRvAdapter;
    ImageView imageView;
    TextView textView;
    ItemsModel itemsModel;
    SQLiteDatabase db;
    String jobTitle;

    String customerId;
    String Activityname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        textView = findViewById(R.id.titel);
        imageView = findViewById(R.id.jobIcon);


        customerId=getIntent().getStringExtra("CustomerID");
        Activityname="showimageactivity";
        //we are retrieving job title of which customer has clicked on item of gridview
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            itemsModel = (ItemsModel) intent.getSerializableExtra("items");
            imageView.setImageResource(itemsModel.getImages());
            jobTitle = itemsModel.getName();
            textView.setText("List of Nearby " + jobTitle);

        }
        try {
            objectRv = findViewById(R.id.RecycleView);
            objectDatabaseHandler = new DatabaseHandler(this);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            //RVAdapter is used for list layout
            objectRvAdapter = new RVAdapter(SearchByJob(jobTitle),this,customerId,Activityname);
            objectRv.setHasFixedSize(true);
            objectRv.setLayoutManager(new LinearLayoutManager(this));
            objectRv.setAdapter(objectRvAdapter);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
// this function is used for selecting workers by job make arraylist
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


