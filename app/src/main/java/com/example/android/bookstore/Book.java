package com.example.android.bookstore;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trikh on 21-10-2016.
 */

public class Book implements Parcelable {
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
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

    protected Book(Parcel in) {
        mBookTitle = in.readString();
        mBookSubtitle = in.readString();
        mAuthorName = in.readString();
        mUrl = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mBookTitle);
        parcel.writeString(mBookSubtitle);
        parcel.writeString(mAuthorName);
        parcel.writeString(mUrl);
    }
}
