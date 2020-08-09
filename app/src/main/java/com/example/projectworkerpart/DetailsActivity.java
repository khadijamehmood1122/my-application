package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.AlphabeticIndex;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.projectworkerpart.DatabaseContract.Users.TABLE_NAME;

public class DetailsActivity extends AppCompatActivity {

    ImageView WorkerDp;
    TextView WorkerName,JobCatogory,email,WAddress,Wphone,WDes;
    Bitmap objectBitmap;

    byte[] Wimage;
    String S_worker_name,S_WjobCatogory,S_Wemail,S_WAddress,S_Wphone,S_WDes;
    String customerLoginEmailId;

    DatabaseHandler objectDatabaseHandler;
    SQLiteDatabase db;

    TextView CId;
    EditText Statement;

    Boolean Jobstatus=false;
    String JobStatement;



    ArrayList<CurrentHirings> currentHiringslist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        WorkerDp=findViewById(R.id.Worker_dp);
        WorkerName=findViewById(R.id.workerName);
        JobCatogory=findViewById(R.id.job);
        email=findViewById(R.id.workerEmail);
        WAddress=findViewById(R.id.CustomerAddress);
        Wphone=findViewById(R.id.customerPhone);
        WDes=findViewById(R.id.Workerdescription);
        Statement=findViewById(R.id.job_statement);

        CId=findViewById(R.id.Cid);


        S_worker_name= getIntent().getStringExtra("imagename");


        try {
            objectDatabaseHandler = new DatabaseHandler(this);
            db = objectDatabaseHandler.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        WorkerName.setText(S_worker_name);
        SearchDetails(S_worker_name);


        customerLoginEmailId=getIntent().getStringExtra("LoginEmailID");
//        CId.setText(customerLoginEmailId);

        currentHiringslist= new ArrayList<>();

    }
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
            email.setText(S_Wemail);
            WAddress.setText(S_WAddress);
            Wphone.setText(S_Wphone);
            WDes.setText(S_WDes);
            WorkerDp.setImageBitmap(objectBitmap);
            db.close();


        } else {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();

        }


    }

    public void HireWorker(View view)
    {
        JobStatement=Statement.getText().toString();

        SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
        //Give any name for //preference as I have given "IDvalue" and value 0.
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("CID",customerLoginEmailId );
        editor.putString("JS",JobStatement);
        // give key value as "sound" you mentioned and value what you // to want give as "1" in you mentioned
        editor.commit();
        Toast.makeText(getApplicationContext(),"You Hire "+S_worker_name,Toast.LENGTH_SHORT).show();
    }
    public void popupWindow(View view)
    {
        Intent i = new Intent(this,RatingPopupActivity.class);
        i.putExtra("Wname",S_worker_name);
        startActivity(i);
    }

    public void callWorker(View view)
    {
        Uri number = Uri.parse("tel:" + S_Wphone);
        Intent intent1 = new Intent(Intent.ACTION_DIAL,number);
        startActivity(intent1);
    }
    public void messageworker(View view)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
        startActivity(intent);
    }
}
