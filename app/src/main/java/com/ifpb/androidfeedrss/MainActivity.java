package com.ifpb.androidfeedrss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ifpb.androidfeedrss.adapter.FeedAdapter;
import com.ifpb.androidfeedrss.model.Notice;
import com.ifpb.androidfeedrss.service.ServiceFeedRss;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private ArrayList<Notice> notices;
    private ArrayList<Notice> responseNotices;

    final Handler handler = new Handler();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            responseNotices = (ArrayList<Notice>) intent.getSerializableExtra("notices");

            ListView listView = findViewById(R.id.listView);
            ArrayAdapter adapter = new FeedAdapter(context, responseNotices);
            listView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, ServiceFeedRss.class);
        intent.setAction("PULL_FEED");

        notices = new ArrayList<>();
        responseNotices = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastReceiver,
                new IntentFilter("PULL_FEED")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
