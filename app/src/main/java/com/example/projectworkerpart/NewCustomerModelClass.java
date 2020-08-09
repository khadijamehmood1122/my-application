package com.example.projectworkerpart;
//NewCustomerModelClass is used for  retrieving only customer name ,city and time
public class NewCustomerModelClass
{
    String Name;
    String City;
    String dateAndTime;


    public NewCustomerModelClass(String name, String city, String dateAndTime) {
        Name = name;
        City = city;
        this.dateAndTime = dateAndTime;
    }

    public String getName() {
        return Name;
    }

    public String getCity() {
        return City;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }
}
