package com.vairiscw.wssandroid.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.data.temperature.TemperatureData;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>{

    private List<TemperatureData> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView temp;
        public TextView time;
        public MyViewHolder(View v) {
            super(v);
            id = v.findViewById(R.id.ids);
            temp = v.findViewById(R.id.temperature);
            time = v.findViewById(R.id.time);
        }
    }

    public RecycleAdapter(List<TemperatureData> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.temperature_items, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.id.setText(mDataset.get(position).getId().toString());
        holder.temp.setText(mDataset.get(position).getTemperature().toString());
        holder.temp.setText(mDataset.get(position).getTime().toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
