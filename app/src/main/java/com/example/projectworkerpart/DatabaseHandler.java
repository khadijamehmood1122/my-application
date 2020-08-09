package com.example.projectworkerpart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME ="fori mazdoori" +
            ".db";
    private static int DATABASE_VERSION=9;


    private static final String CREATE_TBL_USERS = "CREATE TABLE "
            + DatabaseContract.Users.TABLE_NAME + " ("
            + DatabaseContract.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.Users.COL_FULLNAME + " TEXT NOT NULL,"
            + DatabaseContract.Users.COL_EMAIL + " TEXT,"
            + DatabaseContract.Users.COL_PASSWORD+" TEXT,"
            + DatabaseContract.Users.COL_CONFIRMPASSWORD+ " TEXT,"
            + DatabaseContract.Users.COL_CNIC+ " TEXT,"
            + DatabaseContract.Users.COL_GENDER+ " TEXT,"
            + DatabaseContract.Users.COL_DOB+ " TEXT,"
            + DatabaseContract.Users.COL_ADDRESS+ " TEXT,"
            + DatabaseContract.Users.COL_PHONENUMBER+ " TEXT,"
            + DatabaseContract.Users.COL_CITY+ " TEXT,"
            + DatabaseContract.Users.COL_JOB+ " TEXT,"
            + DatabaseContract.Users.COL_DESCRIPTION+ " TEXT,"
            + DatabaseContract.Users.COL_IMAGE+ " BLOB)";



    private static final String CREATE_TBL_CUSTOMER_USERS = "CREATE TABLE "
            + DatabaseContract.UsersC.TABLE_NAME_CUSTOMER + " ("
            + DatabaseContract.UsersC._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.UsersC.COL_FULLNAME_C + " TEXT NOT NULL,"
            + DatabaseContract.UsersC.COL_EMAIL_C + " TEXT,"
            + DatabaseContract.UsersC.COL_PASSWORD_C+" TEXT,"
            + DatabaseContract.UsersC.COL_CONFIRMPASSWORD_C+ " TEXT,"
            + DatabaseContract.UsersC.COL_ADDRESS_C+ " TEXT,"
            + DatabaseContract.UsersC.COL_PHONENUMBER_C+ " TEXT,"
            + DatabaseContract.UsersC.COL_CITY_C+ " TEXT)";


    private static final String CREATE_TBL_JOB_RECORD = "CREATE TABLE "
            + DatabaseContract.JobRecord.TABLE_NAME_RECORD + " ("
            + DatabaseContract.JobRecord._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.JobRecord.COL_WORKER_ID + " TEXT NOT NULL,"
            + DatabaseContract.JobRecord.COL_CUSTOMER_ID + " TEXT,"
            + DatabaseContract.JobRecord.COL_DATE_TIME+ " TEXT,"
            + DatabaseContract.JobRecord.COL_WORKER_RATING + " TEXT,"
            + DatabaseContract.JobRecord.COL_CUSTOMER_RATING + " TEXT)";








    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL(CREATE_TBL_USERS);
            db.execSQL(CREATE_TBL_CUSTOMER_USERS);
            db.execSQL(CREATE_TBL_JOB_RECORD);
            Toast.makeText(context, "Table Created Successfully inside our database",Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public void InsertWorkerData(ModelClass objectModelClass)
    {
        try
        {
            SQLiteDatabase objectSql = this .getWritableDatabase();
            Bitmap imageToStoreBitmap = objectModelClass.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues ContentValues = new ContentValues();


            ContentValues.put(DatabaseContract.Users.COL_FULLNAME, objectModelClass.getName());
            ContentValues.put(DatabaseContract.Users.COL_EMAIL,objectModelClass.getEmail());
            ContentValues.put(DatabaseContract.Users.COL_PASSWORD,objectModelClass.getPass());
            ContentValues.put(DatabaseContract.Users.COL_CONFIRMPASSWORD,objectModelClass.getConfirmPass());
            ContentValues.put(DatabaseContract.Users.COL_CNIC,objectModelClass.getCnic());
            ContentValues.put(DatabaseContract.Users.COL_GENDER,objectModelClass.getGender());
            ContentValues.put(DatabaseContract.Users.COL_DOB,objectModelClass.getDOB());
            ContentValues.put(DatabaseContract.Users.COL_ADDRESS,objectModelClass.getAddress());
            ContentValues.put(DatabaseContract.Users.COL_PHONENUMBER,objectModelClass.getPhoneNumber());
            ContentValues.put(DatabaseContract.Users.COL_CITY,objectModelClass.getCity());
            ContentValues.put(DatabaseContract.Users.COL_JOB,objectModelClass.getJob());
            ContentValues.put(DatabaseContract.Users.COL_DESCRIPTION,objectModelClass.getDescription());
            ContentValues.put(DatabaseContract.Users.COL_IMAGE,imageInBytes);


            long check = objectSql.insert(DatabaseContract.Users.TABLE_NAME,null,ContentValues);
            if(check!=-1)
            {
                Toast.makeText(context, "Data added in table succesfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context , "Fails to add in database ", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void InsertCustomerData(customerModelClass customerModelClass)
    {
        try
        {
            SQLiteDatabase objectSql = this .getWritableDatabase();
            ContentValues ContentValues2 = new ContentValues();

            ContentValues2.put(DatabaseContract.UsersC.COL_FULLNAME_C, customerModelClass.getName());
            ContentValues2.put(DatabaseContract.UsersC.COL_EMAIL_C, customerModelClass.getEmail());
            ContentValues2.put(DatabaseContract.UsersC.COL_PASSWORD_C, customerModelClass.getPass());
            ContentValues2.put(DatabaseContract.UsersC.COL_CONFIRMPASSWORD_C, customerModelClass.getConfirmPass());
            ContentValues2.put(DatabaseContract.UsersC.COL_ADDRESS_C, customerModelClass.getAddress());
            ContentValues2.put(DatabaseContract.UsersC.COL_PHONENUMBER_C, customerModelClass.getPhoneNumber());
            ContentValues2.put(DatabaseContract.UsersC.COL_CITY_C, customerModelClass.getCity());

            long check = objectSql.insert(DatabaseContract.UsersC.TABLE_NAME_CUSTOMER,null,ContentValues2);
            if(check!=-1)
            {
                Toast.makeText(context, "Data added in table succesfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context , "Fails to add in database ", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void InsertJobRecord(Records record)
    {

        try
        {
            SQLiteDatabase objectSql = this .getWritableDatabase();
            ContentValues ContentValues3 = new ContentValues();

            ContentValues3.put(DatabaseContract.JobRecord.COL_WORKER_ID,record.getWorker_EmailId());
            ContentValues3.put(DatabaseContract.JobRecord.COL_CUSTOMER_ID,record.getCustomer_EmailId());
            ContentValues3.put(DatabaseContract.JobRecord.COL_DATE_TIME,record.getDate_time());
            ContentValues3.put(DatabaseContract.JobRecord.COL_WORKER_RATING,record.getWorker_rating());
            ContentValues3.put(DatabaseContract.JobRecord.COL_CUSTOMER_RATING,record.getCustomer_rating());

            long check = objectSql.insert(DatabaseContract.JobRecord.TABLE_NAME_RECORD,null,ContentValues3);
            if(check!=-1)
            {
                Toast.makeText(context, "Data added in table succesfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context , "Fails to add in database ", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<NewModelClass> getAllWorkersData()
    {
        try
        {
            SQLiteDatabase objectsqlLiteDatabse = this.getReadableDatabase();
            ArrayList<NewModelClass> objectModelClassList = new ArrayList<>();

            Cursor objectCursor = objectsqlLiteDatabse.rawQuery("select * from "+DatabaseContract.Users.TABLE_NAME , null);
            if(objectCursor.getCount()!=0)
            {
                while(objectCursor.moveToNext())
                {
                    String nameofImg = objectCursor.getString(1);
                    String jobname = objectCursor.getString(11);
                    byte[] imgageBytes = objectCursor.getBlob(13);

                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imgageBytes,0,imgageBytes.length);
                    objectModelClassList.add(new NewModelClass(nameofImg,jobname,objectBitmap));

                }
                return objectModelClassList;

            }
            else
            {
                Toast.makeText(context,"No value in the Database",Toast.LENGTH_SHORT).show();
                return null;
            }

        }
        catch (Exception e)
        {

            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public ArrayList<NewCustomerModelClass> getAllCustomersData()
    {
        try
        {
            SQLiteDatabase objectsqlLiteDatabse = this.getReadableDatabase();
            ArrayList<NewCustomerModelClass> objectNCMC = new ArrayList<>();

            Cursor objectCursor = objectsqlLiteDatabse.rawQuery("select * from "+DatabaseContract.UsersC.TABLE_NAME_CUSTOMER , null);
            if(objectCursor.getCount()!=0)
            {
                while(objectCursor.moveToNext())
                {
                    String customer_name = objectCursor.getString(1);
                    String customer_city = objectCursor.getString(7);
                    objectNCMC.add(new NewCustomerModelClass(customer_name,customer_city," "));

                }
                return objectNCMC;

            }
            else
            {
                Toast.makeText(context,"No value in the Database",Toast.LENGTH_SHORT).show();
                return null;
            }

        }
        catch (Exception e)
        {

            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}



