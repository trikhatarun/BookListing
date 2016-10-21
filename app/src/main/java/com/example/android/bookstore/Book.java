package com.example.android.bookstore;

/**
 * Created by trikh on 21-10-2016.
 */

public class Book {
    private String mBookTitle;
    private String mBookSubtitle;
    private String mAuthorName;
    private String mUrl;

    public Book(String bookTitle, String bookSubtitle, String authorName, String url) {
        mBookTitle = bookTitle;
        mBookSubtitle = bookSubtitle;
        mAuthorName = authorName;
        mUrl = url;
    }

    public String getmBookTitle() {
        return mBookTitle;
    }

    public String getmBookSubtitle() {
        return mBookSubtitle;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getmUrl() {
        return mUrl;
    }
}
