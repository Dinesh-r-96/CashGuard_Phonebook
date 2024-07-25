package com.example.admin.zs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Smartphonebooks.db";
    public static final String TABLE_NAME1 = "MYDETAILS";
    public static final String TABLE_NAME2 = "MYCONTACTS";
    public static final String PHONECOL = "PHONE";
    public static final String NAMECOL = "NAME";
    public static final String EMAILCOL = "EMAIL";
    public static final String DOBCOL = "DOB";
    public static final String WALLETCOL = "WALLET";
    public static final String PASSWORDCOL = "PASSWORD";


    public DatabaseHelper(Context context) {
        super(context, "Smartphonebooks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MYDETAILS (NAME TEXT,PHONE INTEGER PRIMARY KEY,EMAIL TEXT,DOB TEXT,WALLET INTEGER,PASSWORD TEXT)");
        db.execSQL("CREATE TABLE MYCONTACTS (NAME TEXT,PHONE INTEGER PRIMARY KEY,EMAIL TEXT,DOB TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MYDETAILS");
        db.execSQL("DROP TABLE IF EXISTS MYCONTACTS");
        onCreate(db);
    }
    public Integer samplecontact()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("INSERT INTO MYCONTACTS VALUES('abc',9341,'abc.com','00/00/2000')");
        db.execSQL("INSERT INTO MYCONTACTS VALUES('abcd',9342,'abcd.com','00/00/2000')");
        db.execSQL("INSERT INTO MYCONTACTS VALUES('abce',9343,'abce.com','00/00/2000')");
        return 2;
    }

    public boolean mydetailsinsertData(String n,String p,String e,String d,int w,String pa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",n);
        contentValues.put("PHONE",p);
        contentValues.put("EMAIL",e);
        contentValues.put("DOB", d);
        contentValues.put("WALLET", w);
        contentValues.put("PASSWORD", pa);
        long result = db.insert("MYDETAILS",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean mycontactsinsertData(String n,String p,String e,String d) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",n);
        contentValues.put("PHONE",p);
        contentValues.put("EMAIL",e);
        contentValues.put("DOB", d);

        long result = db.insert("MYCONTACTS",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor mydetailsgetAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from MYDETAILS",null);
        return res;
    }
    public Cursor mycontactgetAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from MYCONTACTS",null);
        return res;
    }
    public Cursor mycontactgetAllDatabyphone(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from MYCONTACTS WHERE PHONE="+phone,null);
        return res;
    }
//    public boolean mydetailsupdateData(String n,String p,String e,String d,String w,String pa) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE MYDETAILS SET NAME = '"+n+"', EMAIL = '"+e+"', DOB = '"+d+"', WALLET =" +w+ ", PASSWORD='"+pa+"' WHERE PHONE ="+p);
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("NAME",n);
//        contentValues.put("PHONE",p);
//        contentValues.put("EMAIL",e);
//        contentValues.put("DOB",d);
//        contentValues.put("WALLET",w);
//        contentValues.put("PASSWORD",pa);
//        db.update("MYDETAILS", contentValues, "PHONE = ?" ,new String[] {p});
//        return true;
//    }

    public boolean update(String v)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE MYDETAILS SET WALLET = 100+" + v);
        return true;
    }

    public boolean detectmywallet(String v)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE MYDETAILS SET WALLET = WALLET-"+v);
        return true;
    }

    public boolean updateData(String phone,String name,String email,String dob,String password) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("NAME",name);
    contentValues.put("EMAIL",email);
    contentValues.put("DOB",dob);
    contentValues.put("PASSWORD",password);
    db.update("MYDETAILS", contentValues, "PHONE= ?", new String[]{phone});
    return true;
}

    public Integer mydetailsdeleteData(String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("MYDETAILS","PHONE = ?",new String[] { phone });
    }
    public Integer mycontactsdeleteData(String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("MYCONTACTS","PHONE = ?",new String[] { phone });
    }
    public boolean queries(String querye)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(querye);
        return true;
    }

    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT NAME, PHONE, EMAIL FROM MYCONTACTS";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex("NAME")));
            user.put("phone",cursor.getString(cursor.getColumnIndex("PHONE")));
            user.put("email",cursor.getString(cursor.getColumnIndex("EMAIL")));
            userList.add(user);
        }
        return userList;
    }

}