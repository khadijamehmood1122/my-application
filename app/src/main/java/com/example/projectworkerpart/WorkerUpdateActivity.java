package com.example.projectworkerpart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.projectworkerpart.DatabaseContract.Users.COL_ADDRESS;
import static com.example.projectworkerpart.DatabaseContract.Users.COL_CITY;
import static com.example.projectworkerpart.DatabaseContract.Users.COL_CNIC;
import static com.example.projectworkerpart.DatabaseContract.Users.COL_DESCRIPTION;
import static com.example.projectworkerpart.DatabaseContract.Users.COL_FULLNAME;
import static com.example.projectworkerpart.DatabaseContract.Users.COL_IMAGE;
import static com.example.projectworkerpart.DatabaseContract.Users.COL_JOB;
import static com.example.projectworkerpart.DatabaseContract.Users.COL_PASSWORD;
import static com.example.projectworkerpart.DatabaseContract.Users.COL_PHONENUMBER;
import static com.example.projectworkerpart.DatabaseContract.Users.TABLE_NAME;

public class WorkerUpdateActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputCPassword;
    private TextInputLayout textInputCNIC;
    private TextInputLayout textInputCity;
    private TextInputLayout textInputJob;
    private TextInputLayout textInputAddress;
    private TextInputLayout textInputDate;
    private TextInputLayout textInputNumber;
    private TextInputLayout textInputDescription;
    private TextInputLayout textInputGender;

    EditText et1;



    public ImageView personImg;
    final int REQUEST_CODE_GALLERY = 999;
    Bitmap bitmap;

    private RadioGroup radioGroup;

    DatabaseHandler objectDB;
    SQLiteDatabase db;
    DatabaseHandler myDb;
    String name,phone,pass,cpass,city,address,cnic,job,image,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_update);

        personImg =findViewById(R.id.Worker_dp);

        objectDB =new DatabaseHandler(this);
        radioGroup = (RadioGroup)findViewById(R.id.groupradio);


        textInputEmail = findViewById(R.id.text_input_email);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputCPassword = findViewById(R.id.text_input_Confirmpassword);
        textInputCNIC = findViewById(R.id.text_input_CNIC);
        textInputCity = findViewById(R.id.text_input_city);
        textInputJob = findViewById(R.id.text_input_job);
        textInputAddress = findViewById(R.id.text_input_address);

        textInputNumber=findViewById(R.id.text_input_Phone);
        textInputDescription = findViewById(R.id.text_input_description);
        et1 = findViewById(R.id.et);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        String[] cities = new String[] {"Karachi", "Islamabad", "Lahore", "Rawalpindi","Quetta","Peshawar","Multan","Faisalabad","Haiderabad"};
        //adding enhanced constructor in arayadapter ...taking id(R.id.tv) of textview from dropdown_menu_popup_item.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item,R.id.tv, cities);
        editTextFilledExposedDropdown.setAdapter(adapter);

        //Working for Job
        AutoCompleteTextView jobDropdown = findViewById(R.id.jobDropdown);
        String JobNames[] = {"Automobile_mechnician","Beautician","Blacksmith","Butcher","Carpenter","Car Washer","Cobbler",
                "Driver","Electrician","Engineer","Gardener","Hair Dresses","HouseKeeper/maid","Labour","Mechanic","Milkman","Painter",
                "Photographer","Plumber","Security Guard","Tailor"};
        //adding enhanced constructor in arayadapter ...taking id(R.id.tv) of textview from dropdown_menu_popup_item.xml
        ArrayAdapter<String> adapterJobs = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item,R.id.tv, JobNames);
        jobDropdown.setAdapter(adapterJobs);
    }
    public void Update(View v)
    {
        myDb=new DatabaseHandler(this);
        db = myDb.getWritableDatabase();
        name=textInputUsername.getEditText().getText().toString().trim();
        phone=textInputNumber.getEditText().getText().toString().trim();
        pass=textInputPassword.getEditText().getText().toString().trim();
        cpass=textInputCPassword.getEditText().getText().toString().trim();
        city=textInputCity.getEditText().getText().toString().trim();
        address=textInputAddress.getEditText().getText().toString().trim();
        cnic=textInputCNIC.getEditText().getText().toString().trim();
        job=textInputJob.getEditText().getText().toString().trim();
        description=textInputDescription.getEditText().getText().toString().trim();
        ContentValues args = new ContentValues();
        args.put(COL_PHONENUMBER,phone);
        args.put(COL_ADDRESS,address);
        args.put(COL_CITY,city);
        args.put(COL_PASSWORD,pass);
        args.put(COL_PASSWORD,cpass);
        args.put(COL_CNIC,cnic);
        args.put(COL_DESCRIPTION,description);
        args.put(COL_JOB,job);

        String[] wherearg={name};
        Integer count= db.update(TABLE_NAME, args, COL_FULLNAME+ " = ?",wherearg);
        if (count > 0) {
            Toast.makeText(this, count+"  Records updated: " , Toast.LENGTH_SHORT).show();

        }



    }
    public void chooseImage(View objectView)
    {
        try
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                bitmap = BitmapFactory.decodeStream(inputStream);
                personImg.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
