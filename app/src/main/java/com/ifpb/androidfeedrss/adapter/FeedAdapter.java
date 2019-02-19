package com.ifpb.androidfeedrss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ifpb.androidfeedrss.R;
import com.ifpb.androidfeedrss.model.Notice;

import java.util.ArrayList;

public class FeedAdapter extends ArrayAdapter<Notice> {

    private final Context context;
    private final ArrayList<Notice> notices;

    public FeedAdapter(Context context, ArrayList<Notice> notices) {
        super(context, R.layout.adapter_notices, notices);
        this.context = context;
        this.notices = notices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_notices, parent, false);

        TextView title = view.findViewById(R.id.tvTitulo);
        TextView description = view.findViewById(R.id.tvDescticao);
        TextView update = view.findViewById(R.id.tvAtualizacao);

        title.setText(notices.get(position).getTitle());
        description.setText(notices.get(position).getDescription());
        update.setText(notices.get(position).getDateUpdate());

        return view;
    }
}
