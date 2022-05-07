package com.example.sqliteapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.adapter.BookAdapter;
import com.example.databasehelper.MyDataBaseHelper;
import com.example.entityclass.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private MyDataBaseHelper dbHelper;
    private final List<Book> bookList = new ArrayList<>();

    public static void actionStart(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDataBaseHelper(this,"BookStore.db",null,5);
        Button createDataBase = findViewById(R.id.button_create);
        Button addBook = findViewById(R.id.button_add);
        Button queryBook = findViewById(R.id.button_query);

        //初始化数据
        initBooks();
        //指定适配器（上下文，子项布局id，载入的数据）
        BookAdapter adapter = new BookAdapter(MainActivity.this,R.layout.book_item,bookList);
        ListView bookView = findViewById(R.id.list_book);
        bookView.setAdapter(adapter);

        createDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBookActivity.actionStart(MainActivity.this);
            }
        });

        queryBook.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Recycle")
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //查询book表中的所有数据
                //Cursor cursor = db.query("Book",null,null,null,null,null,null);
                Cursor cursor = db.rawQuery("select * from Book",null);
                if(cursor.moveToFirst()){
                    do{
                        //遍历cursor对象，取出数据
                        @SuppressLint("Range")
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range")
                        double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
                        @SuppressLint("Range")
                        int category =Integer.parseInt(cursor.getString(cursor.getColumnIndex("category_id")));
                        Log.e(TAG, "name: " + name + " price: " + price + " category: "+ category);
                    }while(cursor.moveToNext());
                }
                cursor.close();
                db.close();
            }
        });
    }

    @SuppressLint("Recycle")
    private void initBooks() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询book表中的所有数据
        //Cursor cursor = db.query("Book",null,null,null,null,null,null);
        Cursor cursor = db.rawQuery("select * from Book",null);
        if(cursor.moveToFirst()){
            do{
                //遍历cursor对象，取出数据
                @SuppressLint("Range")
                String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range")
                double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
                @SuppressLint("Range")
                int category =Integer.parseInt(cursor.getString(cursor.getColumnIndex("category_id")));
                Log.d(TAG, "name: " + name + " price: " + price + " category: "+ category);

                String category_id = String.valueOf(category);
                Cursor cursor_category = db.rawQuery("select category_name from Category where category_code = ?",new String[]{category_id});
                if(cursor_category.moveToFirst()) {
                    @SuppressLint("Range")
                    String category_name = cursor_category.getString(cursor_category.getColumnIndex("category_name"));
                    Log.d(TAG, "name: " + name + " price: " + price + " category: "+ category_name);
                    Book book = new Book(category_name,price,name);
                    bookList.add(book);
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}