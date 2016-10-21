package com.example.android.bookstore;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    String query;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView bookListView = (ListView) findViewById(R.id.booklist);
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(bookAdapter);
        final EditText name = (EditText) findViewById(R.id.name);
        ImageButton search = (ImageButton) findViewById(R.id.search);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = spinner.getSelectedItem().toString();
                if (selected.contains("Title")) {
                    query = "intitle:";
                } else if (selected.contains("Author")) {
                    query = "inauthor:";
                } else if (selected.contains("Subject")) {
                    query = "subject:";
                } else {
                    query = "inpublisher:";
                }
                query = query + name.getText().toString().replace(" ", "+");
                LinearLayout result = (LinearLayout) findViewById(R.id.result);
                result.setVisibility(View.VISIBLE);
                BookStoreAsyncTask task = new BookStoreAsyncTask();
                task.execute(BASE_URL + query);
            }
        });
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book currentBook = bookAdapter.getItem(i);
                Uri bookUri = Uri.parse(currentBook.getmUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                startActivity(websiteIntent);
            }
        });
    }

    private class BookStoreAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... url) {
            List<Book> result = Util.fetchBookData(url[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            bookAdapter.clear();
            if (books != null && !books.isEmpty()) {
                bookAdapter.addAll(books);
            } else
                Toast.makeText(MainActivity.this, getString(R.string.no_result), Toast.LENGTH_SHORT).show();
        }
    }
}
