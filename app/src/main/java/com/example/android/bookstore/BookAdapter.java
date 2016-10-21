package com.example.android.bookstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by trikh on 21-10-2016.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            return listItemView;
        }
        Book currentBook = getItem(position);
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getmBookTitle());

        TextView subtitle = (TextView) listItemView.findViewById(R.id.subtitle);
        subtitle.setText(currentBook.getmBookSubtitle());

        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentBook.getmAuthorName());

        return listItemView;
    }
}
