package com.vairiscw.wssandroid.data.times;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vairiscw.wssandroid.R;

import java.util.List;

public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.ViewHolder> {
    private final List<Times> times;

    public TimesAdapter(List<Times> times) {
        this.times = times;
    }

    @NonNull
    @Override
    public TimesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_items_list, parent, false);
        return new TimesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimesAdapter.ViewHolder holder, int position) {
        Times times = this.times.get(position);
        holder.nameView.setText(times.getDesignation());
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView icon;
        final TextView nameView;
        ViewHolder(View view){
            super(view);
            icon = view.findViewById(R.id.rec_image);
            nameView = view.findViewById(R.id.rec_text);
        }
    }

}
