package com.example.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class contactsDB extends SQLiteOpenHelper implements BaseColumns{
	//Table Information
	public static final String TABLE_NAME = "ContactList";
	public static final String COL_NAME = "Name";
	public static final String COL_PH = "Phone";
	public static final String COL_EM = "Email";
    //Database Methods
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
    _ID + " INTEGER PRIMARY KEY," + COL_NAME +  " TEXT," + COL_PH + " TEXT," + COL_EM
    + " TEXT)";
    private static final String SQL_DELETE_TABLE = "DROP TABLES IF EXIST " + TABLE_NAME;
	//Database Information
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contacts.db";
    
    public contactsDB(Context context){
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public void onCreate(SQLiteDatabase db){
    	db.execSQL(SQL_CREATE_TABLE);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
    
}