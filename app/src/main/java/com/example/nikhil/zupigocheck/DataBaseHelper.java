package com.example.nikhil.zupigocheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 1/21/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "items.db";
    public static final String TABLE_NAME = "item_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "DESCRIPTION";
    public static final String COL_4 = "MODEL";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DESCRIPTION TEXT,MODEL INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXSTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String Description,String Model)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,Description);
        contentValues.put(COL_4,Model);
       long result= db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
        {
            return false;
        }
        else return true;
    }
   /* public Cursor getAllData()
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * from "+TABLE_NAME,null);
        return res;
    }*/
    // Read records related to the search term
    public List<MyObject> read(String searchTerm) {

        List<MyObject> recordsList = new ArrayList<MyObject>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + TABLE_NAME;
        sql += " WHERE " + COL_2 + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + COL_1 + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                String objectName = cursor.getString(cursor.getColumnIndex(COL_2));
                MyObject myObject = new MyObject(objectName);

                // add to list
                recordsList.add(myObject);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list of records
        return recordsList;
    }

}

