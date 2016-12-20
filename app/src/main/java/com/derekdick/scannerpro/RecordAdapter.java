package com.derekdick.scannerpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {
    private int resourceId;

    public RecordAdapter(Context context, int textViewResourceId, List<Record> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView recordImage = (ImageView) view.findViewById(R.id.record_image);
        TextView recordTime = (TextView) view.findViewById(R.id.record_time);
        recordImage.setImageResource(record.getImageId());
        recordTime.setText(record.getTime());
        TextView recordContent = (TextView) view.findViewById(R.id.record_content);
        recordContent.setText(record.getContent());

        return view;
    }
}
