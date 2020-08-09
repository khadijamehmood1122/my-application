package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.projectworkerpart.DatabaseContract.Users.TABLE_NAME;
//worker account activity is simply used for check portfolio of worker and it is called by option menu item "my account" shown in worker dashboard
public class WorkerAccountActivity extends AppCompatActivity {

    ImageView WorkerDp;
    TextView WorkerName,JobCatogory,email,WAddress,Wphone,WDes;
    Bitmap objectBitmap;

    byte[] Wimage;
    String S_worker_name,S_WjobCatogory,S_Wemail,S_WAddress,S_Wphone,S_WDes;
    String workerLoginEmailId;

    DatabaseHandler objectDatabaseHandler;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_account);

        WorkerDp=findViewById(R.id.Worker_dp);
        WorkerName=findViewById(R.id.workerName);
        JobCatogory=findViewById(R.id.job);
        email=findViewById(R.id.workerEmail);
        WAddress=findViewById(R.id.CustomerAddress);
        Wphone=findViewById(R.id.customerPhone);
        WDes=findViewById(R.id.Workerdescription);

        try {
            //initializing object
            objectDatabaseHandler = new DatabaseHandler(this);
            db = objectDatabaseHandler.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        workerLoginEmailId=getIntent().getStringExtra("WID");

        SearchDetails(workerLoginEmailId);
        email.setText(workerLoginEmailId);



    }

    // this function is simply used for retrieving worker info by passing email
    public void SearchDetails(String email)
    {

        String[] columns = {DatabaseContract.Users._ID, DatabaseContract.Users.COL_FULLNAME, DatabaseContract.Users.COL_EMAIL, DatabaseContract.Users.COL_PASSWORD, DatabaseContract.Users.COL_CONFIRMPASSWORD,
                DatabaseContract.Users.COL_CNIC, DatabaseContract.Users.COL_GENDER, DatabaseContract.Users.COL_DOB, DatabaseContract.Users.COL_ADDRESS, DatabaseContract.Users.COL_PHONENUMBER, DatabaseContract.Users.COL_CITY
                , DatabaseContract.Users.COL_JOB, DatabaseContract.Users.COL_DESCRIPTION, DatabaseContract.Users.COL_IMAGE};
        String sortOrder = DatabaseContract.Users._ID + " ASC";
        Cursor objectCursor = db.query(TABLE_NAME, columns, DatabaseContract.Users.COL_EMAIL + "  =?",
                new String[]{email}, null, null, sortOrder);
        if (objectCursor.getCount() != 0) {
            while (objectCursor.moveToNext()) {
                String nameofImg = objectCursor.getString(1);
                S_WjobCatogory=objectCursor.getString(11);
                S_worker_name=objectCursor.getString(1);
                S_WAddress=objectCursor.getString(8);
                S_Wphone=objectCursor.getString(9);
                S_WDes=objectCursor.getString(12);
                Wimage = objectCursor.getBlob(13);
                objectBitmap = BitmapFactory.decodeByteArray(Wimage, 0, Wimage.length);



            }
            //setting  required info in textviews
            JobCatogory.setText(S_WjobCatogory);
            WorkerName.setText(S_worker_name);
            WAddress.setText(S_WAddress);
            Wphone.setText(S_Wphone);
            WDes.setText(S_WDes);
            WorkerDp.setImageBitmap(objectBitmap);
            db.close();


        } else {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();

        }
    }
}
