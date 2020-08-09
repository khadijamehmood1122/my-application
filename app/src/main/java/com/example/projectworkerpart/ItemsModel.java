package com.example.projectworkerpart;

import java.io.Serializable;

public class ItemsModel implements Serializable {

    private String name;

    private int images;

    public ItemsModel(String name,int images) {
        this.name = name;

        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
