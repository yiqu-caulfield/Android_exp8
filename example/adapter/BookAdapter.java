package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entityclass.Book;
import com.example.sqliteapplication.R;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private int resourceId;

    public BookAdapter(Context context, int textViewResourceId, List<Book> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获取当前项的book实例
        Book book = getItem(position);
        @SuppressLint("ViewHolder")
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView BookName = view.findViewById(R.id.text_item_name);
        TextView Category = view.findViewById(R.id.text_item_category);
        TextView Price = view.findViewById(R.id.text_item_price);
        BookName.setText(book.getName());
        Category.setText(String.valueOf(book.getCategory()));
        Price.setText(String.valueOf(book.getPrice()));
        return view;
    }
}
