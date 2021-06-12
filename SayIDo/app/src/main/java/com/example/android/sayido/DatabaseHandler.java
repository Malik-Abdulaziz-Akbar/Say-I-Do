package com.example.android.sayido;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper  {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SayIDoRecords";
    private static final String TABLE_Users = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_Email = "email";
    private static final String User_Password = "password";
    private static final String User_Phone_Number = "Phone_Number";
    private static final String User_Image = "Image";

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
   /*Singleton Code*/
    private static DatabaseHandler obj;
    public static DatabaseHandler getDB(Context context)
    {
        // To ensure only one instance is created
        if (obj == null)
        {
            obj = new DatabaseHandler(context);
        }
        return obj;
    }
    /* Creating Tables*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + "users" + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + KEY_NAME + " TEXT,"
                + "email" + " TEXT," +"password"+" TEXT,"+"Phone_Number"
                +" TEXT,"+"Image"+" TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        CREATE_CONTACTS_TABLE = "CREATE TABLE " + "Users_PLAN" + "( "
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + "user_id" + " INTEGER,"+"Venue_id"
                +" INTEGER,"+"Vendor_id"+" INTEGER,"+"Decor_id"+" INTEGER"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        CREATE_CONTACTS_TABLE = "CREATE TABLE " + "Vendor_TABLE" + "( "
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + "Vendor_Info" + " TEXT,"+ "Vendor_Budget" + " INTEGER,"+"Image"
                +" TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        CREATE_CONTACTS_TABLE = "CREATE TABLE " + "Venue_TABLE" + "( "
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + "Venue_Info" + " TEXT,"+ "Venue_Budget" + " INTEGER,"+"Image"
                +" TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        CREATE_CONTACTS_TABLE = "CREATE TABLE " + "Decor_TABLE" + "( "
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + "Decor_Info" + " TEXT,"+ "Decor_Budget" + " INTEGER,"+"Image"
                +" TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    /*Upgrading database*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " +  " Users_PLAN " );
        // Create tables again
        onCreate(db);
    }

    /* code to add the new User "SIGN UP"  */
    public Boolean addUser(String NAME,String Phone_Number,String email,String password) {

        if (Get_user_id(email) != -1){//check if email already registered
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_Email, email); //  email
            values.put(User_Password, password); //  password
            values.put(User_Phone_Number, Phone_Number); //  Phone_Number
            values.put(KEY_NAME, NAME); //  NAME

            // Inserting Row
            double result = db.insert(TABLE_Users, null, values);


            db.close(); // Closing database connection
            if (result == -1)
                return false;
            else {

                values = new ContentValues();
                values.put("user_id", Get_user_id(email)); //  user_id
                result = db.insert("Users_PLAN", null, values);

                db.close(); // Closing database connection
                if (result == -1)
                    return false;
                else {
                    values = new ContentValues();
                    values.put(KEY_Email, email); //  email

                    return true;

                }


            }
        }
return false;
    }
    /*code to update/edit the user profile*/
    public int updateprofile(String email ,String Name,String PhoneNumber ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name);
        values.put(User_Phone_Number, PhoneNumber);

        // updating row
        return db.update(TABLE_Users, values, KEY_Email + " = ?",
                new String[] { String.valueOf(email) });
    }
   /*  code to get SIGN IN inf0 */
    public Boolean SIGN_IN(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_Users, new String[] { KEY_ID,
                        KEY_NAME, KEY_Email,User_Password , User_Phone_Number ,
                        User_Image }, KEY_Email + "=?",
                new String[] { String.valueOf(email) }, null, null, User_Phone_Number, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(3).matches(password)){
                        /*email , password matches database*/
                        return true;
                    }
                } while (cursor.moveToNext());
            }
        }

        return false;
    }

    public int Get_user_id(String email) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_Users, new String[] { KEY_ID,
                        KEY_NAME, KEY_Email,User_Password , User_Phone_Number ,
                        User_Image }, KEY_Email + "=?",
                new String[] { String.valueOf(email) }, null, null, null, null);
        int id = -1;
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        id = Integer.parseInt(cursor.getString(0));
                        return id;
                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }
                    /*email  matches database*/
                } while (cursor.moveToNext());
            }
        }
            return -1;
    }
    public int Get_Venue_Budget(int Venue_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Venue_TABLE", new String[] { KEY_ID,
                        "Venue_Info", "Venue_Budget" }, KEY_ID + "=?",
                new String[] { String.valueOf(Venue_id) }, null, null, null, null);

        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        return Integer.parseInt(cursor.getString(2));

                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                } while (cursor.moveToNext());
            }
        }
        return -1;
    }
    public int Get_Vendor_Budget(int Vendor_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Vendor_TABLE", new String[] { KEY_ID,
                        "Vendor_Info", "Vendor_Budget" }, KEY_ID + "=?",
                new String[] { String.valueOf(Vendor_id) }, null, null, null, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        return Integer.parseInt(cursor.getString(2));

                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                } while (cursor.moveToNext());
            }
        }
        return -1;
    }
    public int Get_Decor_Budget(int Decor_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Decor_TABLE", new String[] { KEY_ID,
                        "Decor_Info", "Decor_Budget" }, KEY_ID + "=?",
                new String[] { String.valueOf(Decor_id) }, null, null, null, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        return Integer.parseInt(cursor.getString(2));

                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                } while (cursor.moveToNext());
            }
        }return -1;
    }
    public String Total_Budget(String email) {

        SQLiteDatabase db = this.getReadableDatabase();
        int id = -1,Venue_id,Vendor_id,Decor_id,Total=0 ;
        StringBuilder result = new StringBuilder();
        id = Get_user_id(email);

        Cursor cursor = db.query("Users_PLAN", new String[] { KEY_ID,
                        "user_id", "Venue_id","Vendor_id","Decor_id" }, "user_id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {

                    try {
                        /*Venue_id*/
                        Venue_id = Integer.parseInt(cursor.getString(2));
                        int venue_budget= Get_Venue_Budget(Venue_id);
                        Total = venue_budget;
                        result.append (" Venue Budget = " + venue_budget  + "\n");
                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }
                    try {
                        /*Vendor_id*/
                        Vendor_id = Integer.parseInt(cursor.getString(3));
                        int vendor_budget= Get_Vendor_Budget(Vendor_id);
                        Total    = +vendor_budget;
                        result.append (" Vendor Budget = " + vendor_budget  + "\n");

                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }
                    try {
                        /*Decor_id*/
                        Decor_id = Integer.parseInt(cursor.getString(4));
                        int Decor_budget= Get_Decor_Budget(Decor_id);
                        Total    = +Decor_budget;
                        result.append (" Decor Budget = " + Decor_budget + "\n");

                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }
                        /*email , password matches database*/
                        return result.toString()+"\n Total Budget = "+Total;

                } while (cursor.moveToNext());
            }
        }

        return result.toString();
    }

    public int Booking(String email , int Venue_id , int Vendor_id, int Decor_id ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Venue_id", Venue_id);
        values.put("Vendor_id", Vendor_id);
        values.put("Decor_id", Decor_id);

        // updating row
        return db.update("Users_PLAN", values, KEY_Email + " = ?",
                new String[] { String.valueOf(email) });
    }

    public int Get_Venue_Id(int Venue_info ) {
        SQLiteDatabase db = this.getWritableDatabase();

        /*Venue_id*/
        Cursor cursor = db.query("Venue_TABLE", new String[] { KEY_ID,
                        "Venue_Info", "Venue_Budget" }, "Venue_Info" + "=?",
                new String[] { String.valueOf(Venue_info) }, null, null, null, null);

        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        return Integer.parseInt(cursor.getString(0));

                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                } while (cursor.moveToNext());
            }
        }
    return -1;
    }
    public int Get_Vendor_Id(int Vendor_info ) {
        SQLiteDatabase db = this.getWritableDatabase();

        /*Vendor_id*/
        Cursor cursor = db.query("Vendor_TABLE", new String[] { KEY_ID,
                        "Vendor_Info", "Vendor_Budget" }, "Vendor_Info" + "=?",
                new String[] { String.valueOf(Vendor_info) }, null, null, null, null);

        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        return Integer.parseInt(cursor.getString(0));

                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                } while (cursor.moveToNext());
            }
        }
        return -1;
    }
    public int Get_Decor_Id(int Decor_info ) {
        SQLiteDatabase db = this.getWritableDatabase();

        /*Vendor_id*/
        Cursor cursor = db.query("Decor_TABLE", new String[] { KEY_ID,
                        "Decor_Info", "Decor_Budget" }, "Decor_Info" + "=?",
                new String[] { String.valueOf(Decor_info) }, null, null, null, null);

        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        return Integer.parseInt(cursor.getString(0));

                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                } while (cursor.moveToNext());
            }
        }
        return -1;
    }

  /*  // code to add the new contact
    Boolean addAttendance(Attendance_Class attendanceClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, attendanceClass.getKEY_NAME()); //  Name
        values.put(KEY_Email, attendanceClass.getKEY_Email()); //  R_n
        values.put(User_Image, attendanceClass.getUser_Image()); //  CuRse
        values.put(User_Password, attendanceClass.getUser_Password()); //  status
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        values.put(User_Phone_Number, strDate); //  Date

        // Inserting Row
        double result = db.insert(TABLE_Users, null, values);

        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        if (result == -1)
            return false;
        else
            return true;
    }





    // code to get the All record
    ArrayList<Attendance_Class> getAllAttendance() {
        ArrayList<Attendance_Class> AttendanceList = new ArrayList<Attendance_Class>();


        String selectQuery = "SELECT * FROM " + TABLE_Users;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    AttendanceList.add(new Attendance_Class(cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5)));
                    // Adding Users_PLAN to list

                } while (cursor.moveToNext());
            }
        }

        // return
        return AttendanceList;
    }
    // code to get all contacts in a list view
    public ArrayList<String> getAllUsers_PLAN() {
        ArrayList<String> Users_PLANList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT course_name FROM " + " Users_PLAN ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Users_PLANList.add(cursor.getString(0));
                // Adding Users_PLAN to list

            } while (cursor.moveToNext());
        }
        return Users_PLANList;
    }


    // code to get all contacts in a list view
    public ArrayList<Attendance_Class> getAllAttendances() {
        ArrayList<Attendance_Class> AttendanceList = new ArrayList<Attendance_Class>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Users;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Attendance_Class Attendance = new Attendance_Class();
                Attendance.setKEY_NAME(cursor.getString(1));
                Attendance.setKEY_Email(cursor.getString(2));
                Attendance.setKEY_Email(cursor.getString(3));
                Attendance.setKEY_Email(cursor.getString(5));
                // Adding contact to list
                AttendanceList.add(Attendance);
            } while (cursor.moveToNext());
        }


        return AttendanceList;
    }

*/

/*
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Users;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }*/

}