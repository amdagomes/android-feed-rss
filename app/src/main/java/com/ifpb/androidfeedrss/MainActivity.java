package com.ifpb.androidfeedrss;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ifpb.androidfeedrss.adapter.FeedAdapter;
import com.ifpb.androidfeedrss.model.Notice;
import com.ifpb.androidfeedrss.notification.NotificationFeedRss;
import com.ifpb.androidfeedrss.service.ServiceFeedRss;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer;

    private Intent intent;

    private ListView listView;
    private ArrayList<Notice> notices;
    private ArrayList<Notice> responseNotices;
    private boolean isInstant;

    final Handler handler = new Handler();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            responseNotices = (ArrayList<Notice>) intent.getSerializableExtra("notices");

            ArrayAdapter adapter = new FeedAdapter(context, responseNotices);
            listView.setAdapter(adapter);

            if (isInstant){
                isInstant = false;
                notices = responseNotices;
            } else if (!notices.equals(responseNotices)){
                NotificationFeedRss.notificar(
                        context,
                        1,
                        intent,
                        R.mipmap.ic_launcher,
                        "Atualização do Feed",
                        "As noticias foram atualizadas"
                );
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, ServiceFeedRss.class);
        intent.setAction("PULL_FEED");

        listView = findViewById(R.id.listView);
        notices = new ArrayList<>();
        responseNotices = new ArrayList<>();
        isInstant = true;

        startService();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = responseNotices.get(position).getLink().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    public void startService(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startService(intent);
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 300000);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        startService(intent);
//    }

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
