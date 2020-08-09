package com.example.projectworkerpart;

import android.graphics.Bitmap;
//NewWorkerModelList is used for detail information related to worker with date/time and rating
//this is used in deatil_W_RVAdapter which is used in customer Record activity and customer E activity
public class NewWorkerModelList
{
    String WorkerName;
    String workerJob;
    String dateAndTime;
    Bitmap image;
    String WRating;


    public NewWorkerModelList(String workerName, String workerJob, String dateAndTime, Bitmap image, String WRating) {
        WorkerName = workerName;
        this.workerJob = workerJob;
        this.dateAndTime = dateAndTime;
        this.image = image;
        this.WRating = WRating;
    }

    public String getWorkerName() {
        return WorkerName;
    }

    public String getWorkerJob() {
        return workerJob;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getWRating() {
        return WRating;
    }
}
