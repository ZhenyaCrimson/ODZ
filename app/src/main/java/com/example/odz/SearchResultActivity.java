package com.example.odz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    RecyclerView recyclerView2;

    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView2 = findViewById(R.id.recyclerView2);
        String titl = getIntent().getStringExtra("title_value");

        myDB = new MyDatabaseHelper(SearchResultActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataInSortedArray(titl);

        customAdapter = new CustomAdapter(SearchResultActivity.this, this, book_id, book_title, book_author, book_pages);
        recyclerView2.setAdapter(customAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
    }

    void storeDataInSortedArray(String title){
        Cursor cursor = myDB.searchData(title);
        book_id.clear();
        book_title.clear();
        book_author.clear();
        book_pages.clear();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }
}