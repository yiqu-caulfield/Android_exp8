package com.example.sqliteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.databasehelper.MyDataBaseHelper;

public class AddBookActivity extends AppCompatActivity {
    //log t + tab
    private static final String TAG = "AddBookActivity";
    private MyDataBaseHelper dbHelper;

    public static void actionStart(Context context){
        Intent intent = new Intent(context, AddBookActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        dbHelper = new MyDataBaseHelper(this,"BookStore.db",null,5);

        EditText name = findViewById(R.id.edit_name);
        EditText author = findViewById(R.id.edit_author);
        EditText price = findViewById(R.id.edit_price);
        EditText page = findViewById(R.id.edit_page);
        EditText Category_id = findViewById(R.id.edit_category_id);
        Button submit = findViewById(R.id.button_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bookName = name.getText().toString();
                String bookAuthor = author.getText().toString();
                String bookPrice = price.getText().toString();
                String bookPage = page.getText().toString();
                String CategoryId = Category_id.getText().toString();

                //test part
                Log.e(TAG, "name: "+bookName);

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //组装数据
                values.put("name",bookName);
                values.put("author",bookAuthor);
                values.put("price",bookPrice);
                values.put("pages",bookPage);
                values.put("category_id",CategoryId);
                db.insert("Book",null,values);
                MainActivity.actionStart(AddBookActivity.this);

            }
        });
    }
}