package com.example.projectworkerpart;

//Record class is used for saving jobrecord table info ...we use class object for inserting table data rather than
//columns name
public class Records
{
    String Worker_EmailId;
    String Customer_EmailId;
    String date_time;
    String worker_rating;
    String customer_rating;

    public Records(String worker_EmailId, String customer_EmailId, String date_time, String worker_rating, String customer_rating) {
        Worker_EmailId = worker_EmailId;
        Customer_EmailId = customer_EmailId;
        this.date_time = date_time;
        this.worker_rating = worker_rating;
        this.customer_rating = customer_rating;
    }

    public String getWorker_EmailId() {
        return Worker_EmailId;
    }

    public String getCustomer_EmailId() {
        return Customer_EmailId;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getWorker_rating() {
        return worker_rating;
    }

    public String getCustomer_rating() {
        return customer_rating;
    }
}
