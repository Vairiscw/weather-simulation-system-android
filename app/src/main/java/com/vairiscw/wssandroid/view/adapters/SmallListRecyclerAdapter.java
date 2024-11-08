package com.vairiscw.wssandroid.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.view.environment_page.SmallEnvironmentPage;

import java.util.List;

public class SmallListRecyclerAdapter extends RecyclerView.Adapter<SmallListRecyclerAdapter.ViewHolder> {
    private List<SmallEnvironmentPage> data;
    private Context context;
    int currentActivePosition;

    public SmallListRecyclerAdapter(List<SmallEnvironmentPage> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int realPosition = position % data.size();
        SmallEnvironmentPage data = this.data.get(realPosition);
        holder.textView.setText(data.getText());
        holder.imageView.setImageResource(data.getImage());
        if (position == currentActivePosition) {
            holder.imageView.clearColorFilter();
            holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.active_icon_color));
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.active_icon_color));
        } else {
            holder.imageView.clearColorFilter();
            holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.disable_icon_color));
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.disable_icon_color));
        }

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE - 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.IconView);
            textView = view.findViewById(R.id.TitleView);
        }
    }

    public void setActivePosition(int position) {
        int previousActivePosition = currentActivePosition;
        currentActivePosition = position;
        if (previousActivePosition != RecyclerView.NO_POSITION) {
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }
}
