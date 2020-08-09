package com.example.projectworkerpart;

import android.graphics.Bitmap;
import android.widget.RelativeLayout;
//NewModelClass is used for retrieving worker name ,image and jobname and used this info in recyclerviews
//this is used in RVAdapter which is used for "showImageactivity" and for RecordActivity
public class NewModelClass {
    private String imagename;
    private String jobtitle;
    private Bitmap image;

    public NewModelClass(String imagename, String jobtitle, Bitmap image) {
        this.imagename = imagename;
        this.jobtitle = jobtitle;
        this.image = image;
    }


    public String getImagename() {
        return imagename;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getJobtitle() {
        return jobtitle;
    }
}
