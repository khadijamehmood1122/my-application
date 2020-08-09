package com.example.projectworkerpart;

public class customerModelClass {

    private String Name;
    private String Email;
    private String pass;
    private String ConfirmPass;
    private String Address;
    private String PhoneNumber;
    private String City;

    public customerModelClass(String name, String email, String pass, String confirmPass, String address, String phoneNumber, String city) {
        this.Name = name;
        this.Email = email;
        this.pass = pass;
        this.ConfirmPass = confirmPass;
        this.Address = address;
        this.PhoneNumber = phoneNumber;
        this.City = city;
    }

    public String getName() {
        return Name;
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

    public String getAddress() {
        return Address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getCity() {
        return City;
    }
}
