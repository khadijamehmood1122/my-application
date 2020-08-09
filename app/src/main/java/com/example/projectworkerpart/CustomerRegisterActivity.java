package com.example.projectworkerpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class CustomerRegisterActivity extends AppCompatActivity {


    private TextInputLayout textInputEmail2;
    private TextInputLayout textInputUsername2;
    private TextInputLayout textInputPassword2;
    private TextInputLayout textInputCPassword2;
    private TextInputLayout textInputCity2;
    private TextInputLayout textInputAddress2;
    private TextInputLayout textInputNumber2;



    String cityInput2 ;      String PhoneNumberInput2;
    String emailInput2;      String AddressInput2;
    String passwordInput2;   String usernameInput2;
    String CpasswordInput2;

    DatabaseHandler objectDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);


        objectDB =new DatabaseHandler(this);
        textInputEmail2 = findViewById(R.id.text_input_email2);
        textInputUsername2 = findViewById(R.id.text_input_username2);
        textInputPassword2 = findViewById(R.id.text_input_password2);
        textInputCPassword2= findViewById(R.id.text_input_Confirmpassword2);
        textInputCity2 = findViewById(R.id.text_input_city2);
        textInputAddress2 = findViewById(R.id.text_input_address2);
        textInputNumber2=findViewById(R.id.text_input_Phone2);



        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        String[] cities = new String[] {"Karachi", "Islamabad", "Lahore", "Rawalpindi","Quetta","Peshawar","Multan","Faisalabad","Haiderabad"};
        //adding enhanced constructor in arayadapter ...taking id(R.id.tv) of textview from dropdown_menu_popup_item.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item,R.id.tv, cities);
        editTextFilledExposedDropdown.setAdapter(adapter);
    }

    private boolean validateEmail() {
        emailInput2 = textInputEmail2.getEditText().getText().toString().trim();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailInput2.isEmpty()) {
            textInputEmail2.setError("Field can't be empty");
            return false;
        }
        else if (!emailInput2.matches(emailPattern))
        {
            textInputEmail2.setError("Invalid Email Format");
            return false;
        }
        else
        {
            textInputEmail2.setError(null);
            return true;
        }

    }

    private boolean validateCity() {
        cityInput2= textInputCity2.getEditText().getText().toString().trim();
        if (cityInput2.isEmpty()) {
            textInputCity2.setError("Field can't be empty");
            return false;
        } else {
            textInputCity2.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        PhoneNumberInput2 = textInputNumber2.getEditText().getText().toString().trim();
        if (PhoneNumberInput2.isEmpty()) {
            textInputNumber2.setError("Field can't be empty");
            return false;
        }
        else if (PhoneNumberInput2.length() > 11 | PhoneNumberInput2.length() < 11) {
            textInputNumber2.setError("Phone Number must be equal to 11");
            return false;
        }
        else {
            textInputNumber2.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        AddressInput2 = textInputAddress2.getEditText().getText().toString().trim();
        if (AddressInput2.isEmpty()) {
            textInputAddress2.setError("Field can't be empty");
            return false;
        } else {
            textInputAddress2.setError(null);
            return true;
        }
    }
    private boolean validateUsername() {
        usernameInput2 = textInputUsername2.getEditText().getText().toString().trim();
        if (usernameInput2.isEmpty()) {
            textInputUsername2.setError("Field can't be empty");
            return false;
        } else if (usernameInput2.length() > 20 && usernameInput2.length() < 6) {
            textInputUsername2.setError("Username must be in range of 6 to 20");
            return false;
        } else {
            textInputUsername2.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        passwordInput2 = textInputPassword2.getEditText().getText().toString().trim();
        if (passwordInput2.isEmpty()) {
            textInputPassword2.setError("Field can't be empty");
            return false;
        }
        else if (passwordInput2.length() < 8) {
            textInputCPassword2.setError("Password must be greater than 8");
            return false;

        }else {
            textInputPassword2.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        CpasswordInput2 = textInputCPassword2.getEditText().getText().toString().trim();
        if (CpasswordInput2.isEmpty()) {
            textInputCPassword2.setError("Field can't be empty");
            return false;
        }
        else if (CpasswordInput2.length() < 8) {
            textInputCPassword2.setError("Password must be greater than 8");
            return false;
        }
        else if (!CpasswordInput2.equals(passwordInput2)) {
            textInputCPassword2.setError("Password don't match");
            return false;
        }
        else {
            textInputCPassword2.setError(null);
            return true;
        }
    }

    public void back(View view)
    {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
        finish();
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword()  | !validateConfirmPassword() |
                !validateCity() | !validateAddress()  | !validatePhone()) {
            return;
        }
        String input = "Email: " + emailInput2;

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        try
        {
            if(!usernameInput2.isEmpty())
            {
                objectDB.InsertCustomerData(new customerModelClass(usernameInput2,emailInput2,passwordInput2,CpasswordInput2,AddressInput2,PhoneNumberInput2,cityInput2));
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"please select image name and image",Toast.LENGTH_SHORT).show();
        }
    }
}
