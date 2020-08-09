package com.example.projectworkerpart;

public class detailWorkerRecord
{
    String Name;
    String City;
    String dateAndTime;
    String Wrating;

    public detailWorkerRecord(String name, String city, String dateAndTime, String wrating) {
        Name = name;
        City = city;
        this.dateAndTime = dateAndTime;
        Wrating = wrating;
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

    public String getWrating() {
        return Wrating;
    }
}
