package com.example.android.bookstore;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    String query;
    ArrayList<Book> booklist = new ArrayList<>();
    ListView bookListView;
    TextView noResult;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookAdapter = new BookAdapter(this, booklist);
        final EditText name = (EditText) findViewById(R.id.name);
        bookListView = (ListView) findViewById(R.id.booklist);
        noResult = (TextView) findViewById(R.id.noResult);
        ImageButton search = (ImageButton) findViewById(R.id.search);
        final LinearLayout result = (LinearLayout) findViewById(R.id.result);
        bookListView.setAdapter(bookAdapter);
        if (savedInstanceState == null || !savedInstanceState.containsKey("list")) {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booklist.clear();
                Log.v("Onclick", "Button Clickd");
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

                result.setVisibility(View.VISIBLE);
                ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnected()) {
                    BookStoreAsyncTask task = new BookStoreAsyncTask();
                    task.execute(BASE_URL + query);
                } else {
                    Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
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
    } else {
            result.setVisibility(View.VISIBLE);
            noResult.setVisibility(View.GONE);
            booklist = savedInstanceState.getParcelableArrayList("list");
            bookAdapter.addAll(booklist);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LinearLayout result = (LinearLayout) findViewById(R.id.result);
        result.setVisibility(View.VISIBLE);
        outState.putParcelableArrayList("list", booklist);
        super.onSaveInstanceState(outState);
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
                noResult.setVisibility(View.GONE);
                booklist = (ArrayList<Book>) books;
                bookAdapter.addAll(booklist);
            } else
                bookListView.setVisibility(View.GONE);
            noResult.setVisibility(View.VISIBLE);
        }
    }
}
