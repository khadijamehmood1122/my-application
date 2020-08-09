package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.projectworkerpart.DatabaseContract.UsersC.TABLE_NAME_CUSTOMER;

//menu for worker
public class WorkerDashboard extends AppCompatActivity {

    String worker_emailId;
    String Customer_Email_id;
    TextView customerName,customerCity,customerEmail,customerAddress,customerPhone;
    String S_customer_name,S_customer_City,S_customer_email,S_customer_Address,S_customer_phone;
    String formattedDate;
    TextView title;
    TextView jobS;
    Button jobDone,jobCancel;

    String phoneno;
    String message;

    DatabaseHandler objectDatabaseHandler;
    SQLiteDatabase db;

    String jobStatement;


    String workerRating;

    String customerRating;

    TextView t,t2;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_dashboard);



        customerName=findViewById(R.id.customerName);
        customerCity=findViewById(R.id.customerCity);
        customerEmail=findViewById(R.id.customerCity);
        customerCity=findViewById(R.id.customerEmail);
        customerAddress=findViewById(R.id.CustomerAddress);
        customerPhone=findViewById(R.id.customerPhone);
        jobS=findViewById(R.id.job_statement);
        t=findViewById(R.id.tnew);
        t2=findViewById(R.id.tanokha);
        title=findViewById(R.id.titel);



        try {
            objectDatabaseHandler = new DatabaseHandler(this);
            db = objectDatabaseHandler.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        worker_emailId = getIntent().getStringExtra("WorkerEmailId");

        jobDone=findViewById(R.id.btnJobDone);
        jobCancel=findViewById(R.id.JobCancel);

        //we are getting values from activities
        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        SharedPreferences mPrefs2 = getSharedPreferences("IDvalue2", 0);
        SharedPreferences mPrefs3 = getSharedPreferences("IDvalue3", 0);
        //getting customer rating from cusromer_rating popup activity
        customerRating=mPrefs3.getString("Crating","");
        //getting worker rating from ratingPopupActivity
        workerRating =mPrefs2.getString("Wrating","");
        //getting customer id from deatalsActivity to get info that who(customer) has sent request for hiring worker
        Customer_Email_id = mPrefs.getString("CID", "");
        //getting job statement from details activity
        jobStatement = mPrefs.getString("JS","");
        if(!Customer_Email_id.isEmpty())
        {
            customerEmail.setText(Customer_Email_id);
            jobS.setText("Job Statement :" +jobStatement);
            t.setText(workerRating);
            t2.setText(customerRating);
            SearchDetails(Customer_Email_id);
        }
        else
        {
            title.setText("You are not assigned to work yet");
        }

        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();




    }
    public void JobAccepted(View view)
    {
        String message= "You are hired by "+S_customer_name+"\n"+customerAddress+"\n"+customerPhone;
        jobCancel.setVisibility(View.VISIBLE);
        jobDone.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(),"Customer is waiting for you",Toast.LENGTH_SHORT).show();
        sendSMSMessage(message);
    }
    public void addRecord(View view)
    {
        objectDatabaseHandler.InsertJobRecord(new Records(worker_emailId,Customer_Email_id,formattedDate,workerRating,customerRating));
        jobCancel.setVisibility(View.INVISIBLE);
        jobDone.setVisibility(View.INVISIBLE);

    }
    public void JobCancel(View view)
    {
        String message= "Job is Canceled ";
        jobCancel.setVisibility(View.INVISIBLE);
        jobDone.setVisibility(View.INVISIBLE);
        sendSMSMessage(message);

    }
//this function is used for searching customer detail by passing customer email
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

            customerName.setText(S_customer_name);
            customerAddress.setText(S_customer_Address);
            customerCity.setText(S_customer_City);
            customerPhone.setText(S_customer_phone);
            db.close();


        } else {
            Toast.makeText(this, "No value in the Database", Toast.LENGTH_SHORT).show();

        }


    }
    //this function is used for rating customer
    public void openRateWindow(View view)
    {
        Intent i = new Intent(this,customerRatingActivity.class);
        i.putExtra("Wname",S_customer_name);
        startActivity(i);
    }
    public void callCustomer(View view)
    {
        Uri number = Uri.parse("tel:" + S_customer_phone);
        Intent intent1 = new Intent(Intent.ACTION_DIAL,number);
        startActivity(intent1);
    }
    public void messageCustomers(View view)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
        startActivity(intent);
    }

    protected void sendSMSMessage(String message2) {
        phoneno ="03245115292";
        message=message2;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneno, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.id_Account)
        {
            Intent intent1= new Intent(this,WorkerAccountActivity.class);
            intent1.putExtra("WID",worker_emailId);
            startActivity(intent1);
        }
        if(id==R.id.id_records)
        {
            Intent intent2= new Intent(this,Worker_E_Activity.class);
            intent2.putExtra("WID",worker_emailId);
            startActivity(intent2);
        }
        if(id==R.id.id_UpdateInfo)
        {
            Intent intent2= new Intent(this,WorkerUpdateActivity.class);
            intent2.putExtra("WID",worker_emailId);
            startActivity(intent2);
        }
        if(id==R.id.id_log_out)
        {
            Intent intent2= new Intent(this,WorkerLoginActivity.class);
            startActivity(intent2);
        }
        return true;

    }
}
