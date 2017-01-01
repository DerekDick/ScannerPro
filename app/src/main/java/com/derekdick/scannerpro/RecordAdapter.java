package com.derekdick.scannerpro;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {
    private int resourceId;

    private Context mContext;

    public RecordAdapter(Context context, int itemViewResourceId, List<Record> records) {
        super(context, itemViewResourceId, records);

        this.resourceId = itemViewResourceId;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*if (convertView != null) {
            return convertView;
        }*/

        View view;
        ViewHolder viewHolder;
        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        viewHolder = new ViewHolder();
        viewHolder.recordImage = (ImageView) view.findViewById(R.id.record_image);
        viewHolder.recordTime = (TextView) view.findViewById(R.id.record_time);
        viewHolder.recordContent = (TextView) view.findViewById(R.id.record_content);
        Record record = getItem(position);
        viewHolder.recordImage.setImageResource(record.getImageId());
        viewHolder.recordTime.setText(record.getTime());
        viewHolder.recordContent.setText(record.getContent());
        viewHolder.deleteImage = (ImageView) view.findViewById(R.id.image_view_delete);
        final String time = record.getTime();
        viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("警告");
                alertDialog.setMessage("是否确认删除此条记录？");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(Record.class, "time = ?", time);
                        if (mContext instanceof MainActivity) {
                            ((MainActivity)mContext).updateListView();
                        }

                        Toast.makeText(getContext(), "You deleted a record.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "You cancelled a deletion.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
        viewHolder.deleteImage.setTag(position);

        view.setTag(viewHolder);

        return view;
    }

    class ViewHolder {
        ImageView recordImage;
        TextView recordTime;
        TextView recordContent;
        ImageView deleteImage;
    }
}
