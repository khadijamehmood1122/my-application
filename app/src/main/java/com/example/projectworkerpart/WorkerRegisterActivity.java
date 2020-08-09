package com.example.projectworkerpart;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class WorkerRegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
    ImageButton pickdate;
    EditText et1;


    String date;            String cityInput;
    String JobInput;        String CNICInput;
    String DateInput;       String PhoneNumberInput;
    String emailInput;      String AddressInput;
    String passwordInput;   String usernameInput;
    String CpasswordInput;  String DesInput;
    String Gender;

    private RadioGroup radioGroup;

    public ImageView personImg;
    final int REQUEST_CODE_GALLERY = 999;
    Bitmap bitmap;

    DatabaseHandler objectDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_register);


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
        textInputDate = findViewById(R.id.text_input_date);
        textInputGender = findViewById(R.id.text_input_Gender);
        pickdate = findViewById(R.id.calender);
        textInputNumber=findViewById(R.id.text_input_Phone);
        textInputDescription = findViewById(R.id.text_input_description);
        et1 = findViewById(R.id.et);



        personImg =findViewById(R.id.Worker_dp);


        //Working for city
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


        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Uncheck or reset the radio buttons initially
        radioGroup.clearCheck();

        // Add the Listener to the RadioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //Get the selected Radio Button
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
            }
        });

    }
    public void showDatePickerDialog() {
        DatePickerDialog newDialog = new DatePickerDialog(
                this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        newDialog.show();

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        date =month+"/"+dayOfMonth+"/"+year;
        et1.setText(date);
    }

    private boolean validateDate() {
         DateInput = et1.getText().toString().trim();
        if (DateInput.isEmpty()) {
            et1.setError("Field can't be empty");
            return false;
        } else {
            et1.setError(null);
            return true;
        }
    }


    public void chooseImage(View objectView)
    {
        try
        {
            ActivityCompat.requestPermissions(
                    WorkerRegisterActivity.this,
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

    public boolean checkGender()
    {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1)
        {
            textInputGender.setError("Field can't be empty");
            return false;
        }
        else {

            RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
            Gender = radioButton.getText().toString();
            return true;
        }
    }
    private boolean validateDescription() {
        DesInput = textInputDescription.getEditText().getText().toString().trim();
        if (DesInput.isEmpty()) {
            DesInput=" ";
            return true;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
         emailInput = textInputEmail.getEditText().getText().toString().trim();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        }
        else if (!emailInput.matches(emailPattern))
        {
            textInputEmail.setError("Invalid Email Format");
            return false;
        }
        else
        {
            textInputEmail.setError(null);
            return true;
        }

    }

    private boolean validateCity() {
        cityInput = textInputCity.getEditText().getText().toString().trim();
        if (cityInput.isEmpty()) {
            textInputCity.setError("Field can't be empty");
            return false;
        } else {
            textInputCity.setError(null);
            return true;
        }
    }

    private boolean validateJob() {
        JobInput = textInputJob.getEditText().getText().toString().trim();
        if (JobInput.isEmpty()) {
            textInputJob.setError("Field can't be empty");
            return false;
        } else {
            textInputJob.setError(null);
            return true;
        }
    }
    private boolean validateCnic() {
         CNICInput = textInputCNIC.getEditText().getText().toString().trim();
        if (CNICInput.isEmpty()) {
            textInputCNIC.setError("Field can't be empty");
            return false;
        }
        else if (CNICInput.length() > 13 | CNICInput.length() < 13) {
            textInputCNIC.setError("Phone Number must be equal to 13");
            return false;
        }
        else {
            textInputCNIC.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
         PhoneNumberInput = textInputNumber.getEditText().getText().toString().trim();
        if (PhoneNumberInput.isEmpty()) {
            textInputNumber.setError("Field can't be empty");
            return false;
        }
        else if (PhoneNumberInput.length() > 11 | PhoneNumberInput.length() < 11) {
            textInputNumber.setError("Phone Number must be equal to 11");
            return false;
        }
        else {
            textInputNumber.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        AddressInput = textInputAddress.getEditText().getText().toString().trim();
        if (AddressInput.isEmpty()) {
            textInputAddress.setError("Field can't be empty");
            return false;
        } else {
            textInputAddress.setError(null);
            return true;
        }
    }
    private boolean validateUsername() {
         usernameInput = textInputUsername.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 20 && usernameInput.length() < 6) {
            textInputUsername.setError("Username must be in range of 6 to 20");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        }
        else if (passwordInput.length() < 8) {
            textInputCPassword.setError("Password must be greater than 8");
            return false;

        }else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
         CpasswordInput = textInputCPassword.getEditText().getText().toString().trim();
        if (CpasswordInput.isEmpty()) {
            textInputCPassword.setError("Field can't be empty");
            return false;
        }
        else if (CpasswordInput.length() < 8) {
            textInputCPassword.setError("Password must be greater than 8");
            return false;
        }
        else if (!CpasswordInput.equals(passwordInput)) {
            textInputCPassword.setError("Password don't match");
            return false;
        }
        else {
            textInputCPassword.setError(null);
            return true;
        }
    }
    public void confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword() | !validateCnic() | !validateConfirmPassword() |
                !validateCity() |!validateAddress() |!validateDate() | !validatePhone() |!validateJob() |!validateDescription() |!checkGender()) {
            return;
        }
        String input = "Email: " + textInputEmail.getEditText().getText().toString();
        input += "\n";
        input += "Username: " + textInputUsername.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();
        input += "\n";
        input += "Cnic: " + textInputCNIC.getEditText().getText().toString();
        input += "\n";
        input += "CPass: " + textInputCPassword.getEditText().getText().toString();
        input += "\n";
        input += "City: " + textInputCity.getEditText().getText().toString();
        input += "\n";
        input += "Address: " + textInputAddress.getEditText().getText().toString();
        input += "\n";
        input += "Date: " + et1.getText().toString();
        input += "\n";
        input += "Phone: " + textInputNumber.getEditText().getText().toString();
        input += "\n";
        input += "Job: " + textInputJob.getEditText().getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

        try
        {
            if(!usernameInput.isEmpty() && personImg.getDrawable()!=null && bitmap!=null)
            {
                //if every functions return true then we call function which is in database handler class
                objectDB.InsertWorkerData(new ModelClass(usernameInput,CNICInput,emailInput,passwordInput,CpasswordInput,Gender,date,bitmap,AddressInput,PhoneNumberInput,cityInput,JobInput,DesInput));
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"please select image name and image",Toast.LENGTH_SHORT).show();
        }




    }
    public void Goback(View view)
    {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
        finish();
    }



}

