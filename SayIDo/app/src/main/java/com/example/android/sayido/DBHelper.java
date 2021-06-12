package com.example.android.sayido;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "database";

    // Table Names
    private static final String TABLE_VENUES = "venues";
    private static final String TABLE_VENDORS = "vendors";
    private static final String TABLE_DECORS = "decors";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRICES = "prices";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String PRICE = "price";

    // Venue Table Columns
    private static final String VENUE_NAME = "Name";
    private static final String VENUE_ADDRESS = "Address";
    private static final String VENUE_IMAGE_ID= "ImageId";

    // Vendors Table Column Names
    private static final String VENDOR_NAME = "Name";
    private static final String VENDOR_ADDRESS= "Address";
    private static final String VENDOR_IMAGE_ID= "ImageId";

    // Users Table - column names
    private static final String USER_NAME = "Name";
    private static final String User_PASSWORD = "Gender";
    private static final String USER_EMAIL = "Email";
    private static final String USER_PHONE = "Phone";

    // Decor Table - column names
    private static final String DECOR_NAME = "Name";
    private static final String DECOR_ADDRESS= "Address";
    private static final String DECOR_IMAGE_ID= "ImageId";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_VENUES = "CREATE TABLE "
            + TABLE_VENUES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + VENUE_NAME
            + " TEXT,"  + VENUE_ADDRESS
            + " TEXT," +VENUE_IMAGE_ID
            + " INTEGER," +PRICE
            + " INTEGER"+ ")";

    // Todo table create statement
    private static final String CREATE_TABLE_PRICES = "CREATE TABLE "
            + TABLE_PRICES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +PRICE
            + " INTEGER"+ ")";

    // Todo table create statement
    private static final String CREATE_TABLE_VENDERS = "CREATE TABLE "
            + TABLE_VENDORS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + VENDOR_NAME
            + " TEXT," + VENDOR_ADDRESS
            + " TEXT," +VENDOR_IMAGE_ID
            + " INTEGER,"+PRICE
            + " INTEGER" + ")";


    // Todo table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + USER_NAME
            + " TEXT," + User_PASSWORD + " TEXT," + USER_EMAIL
            + " TEXT," +USER_PHONE
            + " TEXT" + ")";

    // Todo table create statement
    private static final String CREATE_TABLE_DECORS = "CREATE TABLE "
            + TABLE_DECORS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + DECOR_NAME
            + " TEXT," + DECOR_ADDRESS
            + " TEXT," +DECOR_IMAGE_ID
            + " INTEGER,"+PRICE
            + " INTEGER" + ")";


    private static DBHelper obj;
    static DBHelper getDB(Context context)
    {
        // To ensure only one instance is created
        if (obj == null)
        {
            obj = new DBHelper(context);
        }
        return obj;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_VENUES);
        db.execSQL(CREATE_TABLE_VENDERS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_DECORS);
        db.execSQL(CREATE_TABLE_PRICES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENUES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DECORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICES);
        // create new tables
        onCreate(db);
    }

    //--------------------------- Table Venues Functions ---------------------------

    void insertVenue(VenueOptions venue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VENUE_NAME, venue.getText());
        values.put(VENUE_ADDRESS,venue.getCountry_text());
        values.put(VENUE_IMAGE_ID,String.valueOf(venue.getImageId()));
        values.put(PRICE,String.valueOf(venue.price));
        db.insert(TABLE_VENUES, null, values);
        db.close();

    }
    ArrayList<VenueOptions> getAllVenues() {
        ArrayList<VenueOptions> venueList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VENUES;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VenueOptions venue = new VenueOptions();

                venue.setText(cursor.getString(1));
                venue.setCountry_text(cursor.getString(2));
                venue.setImageId(Integer.parseInt(cursor.getString(3)));
                venue.price = Integer.parseInt(cursor.getString(4));

                // Adding venue to list
                venueList.add(venue);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return venueList;
    }
    public void deleteVenue(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VENUES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    VenueOptions getVenue(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VENUES, new String[] {VENUE_NAME,
                        VENUE_ADDRESS,
                        VENUE_IMAGE_ID,PRICE},
                KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        VenueOptions venue1= new VenueOptions(cursor.getString(0),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)));
        cursor.close();
        // return contact
        return venue1;
    }
    void updateVenue(VenueOptions venue, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VENUE_NAME, venue.getText());
        values.put(VENUE_ADDRESS,venue.getCountry_text());
        values.put(VENUE_IMAGE_ID,String.valueOf(venue.getImageId()));
        values.put(PRICE,String.valueOf(venue.price));
        // updating row
        db.update(TABLE_VENUES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


    void insertPrice(int price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRICE,String.valueOf(price));
        db.insert(TABLE_PRICES, null, values);
        db.close();

    }
    public void deletePrice(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRICES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    ArrayList<Integer> getAllPrices() {
        ArrayList<Integer> decorList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRICES;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding venue to list
                decorList.add(Integer.parseInt(cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return decorList;
    }

    //--------------------------- Table Venues Functions ---------------------------

    void insertDecor(DecorOptions decor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DECOR_NAME, decor.getText());
        values.put(DECOR_ADDRESS,decor.getCountry_text());
        values.put(DECOR_IMAGE_ID,String.valueOf(decor.getImageId()));
        values.put(PRICE,String.valueOf(decor.price));
        db.insert(TABLE_DECORS, null, values);
        db.close();

    }
    ArrayList<DecorOptions> getAllDecors() {
        ArrayList<DecorOptions> decorList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DECORS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DecorOptions decor = new DecorOptions();

                decor.setText(cursor.getString(1));
                decor.setCountry_text(cursor.getString(2));
                decor.setImageId(Integer.parseInt(cursor.getString(3)));
                decor.price = Integer.parseInt(cursor.getString(4));

                // Adding venue to list
                decorList.add(decor);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return decorList;
    }
    public void deleteDecor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DECORS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    DecorOptions getDecor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DECORS, new String[] {DECOR_NAME,
                        DECOR_ADDRESS,
                        DECOR_IMAGE_ID,PRICE},
                KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        DecorOptions decor1= new DecorOptions(cursor.getString(0),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)));
        cursor.close();
        // return contact
        return decor1;
    }
    void updateDecor(DecorOptions decor, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DECOR_NAME, decor.getText());
        values.put(DECOR_ADDRESS,decor.getCountry_text());
        values.put(DECOR_IMAGE_ID,String.valueOf(decor.getImageId()));
        values.put(PRICE,String.valueOf(decor.price));

        // updating row
        db.update(TABLE_DECORS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


    //--------------------------- Table Vendors Functions ---------------------------

    void insertVendor(VendorOptions vendor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VENDOR_NAME, vendor.getText());
        values.put(VENDOR_ADDRESS,vendor.getCountry_text());
        values.put(VENDOR_IMAGE_ID,String.valueOf(vendor.getImageId()));
        values.put(PRICE,String.valueOf(vendor.price));
        db.insert(TABLE_VENDORS, null, values);
        db.close();

    }
    ArrayList<VendorOptions> getAllVendors() {
        ArrayList<VendorOptions> vendorList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VENDORS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VendorOptions vendor = new VendorOptions();

                vendor.setText(cursor.getString(1));
                vendor.setCountry_text(cursor.getString(2));
                vendor.setImageId(Integer.parseInt(cursor.getString(3)));
                vendor.price = Integer.parseInt(cursor.getString(4));
                // Adding venue to list
                vendorList.add(vendor);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return vendorList;
    }
    public void deleteVendor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VENDORS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    VendorOptions getVendor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VENDORS, new String[] {VENUE_NAME,
                        VENDOR_ADDRESS,
                        VENDOR_IMAGE_ID,PRICE},
                KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        VendorOptions vendor= new VendorOptions(cursor.getString(0),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)));
        cursor.close();
        // return contact
        return vendor;
    }
    void updateVendor(VendorOptions vendor, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VENDOR_NAME, vendor.getText());
        values.put(VENDOR_ADDRESS,vendor.getCountry_text());
        values.put(VENDOR_IMAGE_ID,String.valueOf(vendor.getImageId()));
        values.put(PRICE,String.valueOf(vendor.price));
        // updating row
        db.update(TABLE_VENDORS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }



    //--------------------------- Table Users Functions ---------------------------
    void insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.name);
        values.put(User_PASSWORD,user.password);
        values.put(USER_EMAIL,user.email);
        values.put(USER_PHONE,user.phone);
        db.insert(TABLE_USERS, null, values);
        db.close();

    }
    ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();

                user.setName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setPhone(cursor.getString(4));
                // Adding venue to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return userList;
    }
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] {USER_NAME,
                        User_PASSWORD, USER_EMAIL,
                        USER_PHONE },
                KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        User user= new User(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
        cursor.close();
        // return contact
        return user;
    }
    int getUserId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_ID },
                USER_EMAIL + "=?",
                new String[] { email }, null,
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        int userid= Integer.parseInt(cursor.getString(0));

        cursor.close();
        // return contact
        return userid;
    }
    boolean authenticate(String email,String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] {USER_NAME,
                        User_PASSWORD, USER_EMAIL,
                        USER_EMAIL },
                USER_EMAIL + "=?",
                new String[] { email}, null,
                null, null, null);
        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0)
        {

            if (cursor.getString(1).equals(password)){
                cursor.close();
                return true;
            }

        }
        cursor.close();
        // return contact
        return false;
    }
    boolean emailExist(User user) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] {USER_NAME,
                        User_PASSWORD, USER_EMAIL,
                        USER_EMAIL },
                USER_EMAIL + "=?",
                new String[] { user.email}, null,
                null, null, null);
        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0)
        {
            cursor.close();
            return true;
        }
        cursor.close();
        // return contact
        return false;
    }
    void updateUser(User user, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.name);
        values.put(User_PASSWORD,user.password);
        values.put(USER_EMAIL,user.email);
        values.put(USER_PHONE,user.phone);
        // updating row
        db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
