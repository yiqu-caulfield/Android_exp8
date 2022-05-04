package com.example.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text,"
            + "category_id integer)";

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    private final Context mContext;

    public MyDataBaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        db.execSQL("insert into Category(category_name,category_code) " +
                "values(?,?)",new String[]{ "悬疑类","2"});
        db.execSQL("insert into Category(category_name,category_code) " +
                "values(?,?)",new String[]{ "科幻类","1"});
        db.execSQL("insert into Book(author,price,pages,name,category_id) " +
                "values(?,?,?,?,?)",new String[]{ "刘慈欣","20.59","268","梦之海","1"});
        Toast.makeText(mContext, "Create succeeded",
                Toast.LENGTH_SHORT).show();
    }
    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);

    }
}
