package com.vairiscw.wssandroid.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.view.environment_page.BigEnvironmentPage;

import java.util.List;

public class BigListRecyclerAdapter extends RecyclerView.Adapter<BigListRecyclerAdapter.ViewHolder> {
    private List<BigEnvironmentPage> data;
    private Context context;
    int currentActivePosition;

    public BigListRecyclerAdapter(List<BigEnvironmentPage> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public BigListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_without_text_list_view, parent, false);
        return new BigListRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BigListRecyclerAdapter.ViewHolder holder, int position) {
        int realPosition = position % data.size();
        BigEnvironmentPage data = this.data.get(realPosition);
        holder.imageView.setImageResource(data.getImage());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.IconView);
        }
    }

    public void setActivePosition(int position) {
        int previousActivePosition = currentActivePosition;
        currentActivePosition = position;
        if (previousActivePosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousActivePosition);
        }
        notifyItemChanged(currentActivePosition);
    }
}
