package com.example.projectworkerpart;

import android.provider.BaseColumns;

//we have database contract class so are able to access coloumns in every activity
public class DatabaseContract {

    public static abstract class Users implements BaseColumns {
        public static final String TABLE_NAME = "workers";
        public static final String COL_FULLNAME = "fullname";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORD = "password";
        public static final String COL_CONFIRMPASSWORD = "cpassword";
        public static final String COL_CNIC = "cnic";
        public static final String COL_GENDER = "gender";
        public static final String COL_DOB = "DateOfBirth";
        public static final String COL_ADDRESS = "address";
        public static final String COL_PHONENUMBER = "phonenumber";
        public static final String COL_CITY = "city";
        public static final String COL_JOB = "job";
        public static final String COL_DESCRIPTION = "descriptionoptional";
        public static final String COL_IMAGE = "image";
    }

    public static abstract class UsersC implements BaseColumns
    {

        public static final String TABLE_NAME_CUSTOMER = "customer";
        public static final String COL_FULLNAME_C= "cfullname";
        public static final String COL_EMAIL_C= "cemail";
        public static final String COL_PASSWORD_C= "cpassword";
        public static final String COL_CONFIRMPASSWORD_C="ccpassword";
        public static final String COL_ADDRESS_C= "caddress";
        public static final String COL_PHONENUMBER_C= "cphonenumber";
        public static final String COL_CITY_C="ccity";



    }
    public static abstract class JobRecord implements BaseColumns
    {

        public static final String TABLE_NAME_RECORD = "records";
        public static final String COL_WORKER_ID= "workerid";
        public static final String COL_CUSTOMER_ID= "customerid";
        public static final String COL_DATE_TIME= "dateandtime";
        public static final String COL_WORKER_RATING= "workerrating";
        public static final String COL_CUSTOMER_RATING= "customerrating";
    }


}
