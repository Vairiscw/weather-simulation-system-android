package com.vairiscw.wssandroid.data.scenes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vairiscw.wssandroid.R;

import java.util.List;

public class ScenesAdapter extends RecyclerView.Adapter<ScenesAdapter.ViewHolder>{
    private final List<Scenes> scenes;

    public ScenesAdapter(List<Scenes> scenes) {
        this.scenes = scenes;
    }

    @NonNull
    @Override
    public ScenesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_items_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScenesAdapter.ViewHolder holder, int position) {
        Scenes scenes = this.scenes.get(position);
        holder.nameView.setText(scenes.getDesignation());
    }

    @Override
    public int getItemCount() {
        return scenes.size();
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
