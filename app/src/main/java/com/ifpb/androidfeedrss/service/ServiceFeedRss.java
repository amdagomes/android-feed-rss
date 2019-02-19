package com.ifpb.androidfeedrss.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.ifpb.androidfeedrss.model.Notice;
import com.ifpb.androidfeedrss.parse.XmlPullFeedParse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceFeedRss extends IntentService {

    private final  String URL_FEED = "https://www.diariodosertao.com.br/feed/atom";
    private List<Notice> notices = new ArrayList<>();

    public ServiceFeedRss() {
        super("ServiceFeedRss");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        XmlPullFeedParse feedParse = new XmlPullFeedParse(URL_FEED);
        notices = feedParse.parse();

        intent.putExtra("notices", (Serializable) notices);

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(intent);
    }
}
