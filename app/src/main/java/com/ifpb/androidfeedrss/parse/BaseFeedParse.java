package com.ifpb.androidfeedrss.parse;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseFeedParse implements FeedParser{

    static final String TITLE = "title";
    static final String DESCRIPTION = "summary";
    static final String DATE_UPDATE = "updated";
    static final String LINK = "link";
    static final String ENTRY = "entry";

    final URL feedUrl;

    protected BaseFeedParse(String feedUrl) {
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            Log.e("Error: ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream(){
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
