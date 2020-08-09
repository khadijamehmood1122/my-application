package com.example.projectworkerpart;

import android.graphics.Bitmap;

//ModelClass is used for worker whole information used in database handler class in insertWorkerdata
public class ModelClass {
    private String Name;
    private String Cnic;
    private String Email;
    private String pass;
    private String ConfirmPass;
    private String Gender;
    private String DOB;
    private Bitmap image;
    private String Address;
    private String PhoneNumber;
    private String City;
    private String Job;
    private String Description;

    public ModelClass(String name, String cnic, String email, String pass, String confirmPass, String gender, String DOB, Bitmap image, String address, String phoneNumber, String city, String job, String description) {
        this.Name = name;
        this.Cnic = cnic;
        this.Email = email;
        this.pass = pass;
        this.ConfirmPass = confirmPass;
        this.Gender = gender;
        this.DOB = DOB;
        this.image = image;
        this.Address = address;
        this.PhoneNumber = phoneNumber;
        this.City = city;
        this.Job = job;
        this.Description = description;
    }


    public String getName() {
        return Name;
    }

    public String getCnic() {
        return Cnic;
    }

    public String getEmail() {
        return Email;
    }

    public String getPass() {
        return pass;
    }

    public String getConfirmPass() {
        return ConfirmPass;
    }

    public String getGender() {
        return Gender;
    }

    public String getDOB() {
        return DOB;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getCity() {
        return City;
    }

    public String getJob() {
        return Job;
    }

    public String getDescription() {
        return Description;
    }
}
