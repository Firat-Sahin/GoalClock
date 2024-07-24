package com.firatsahin.goalclock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AddictionAdapter extends RecyclerView.Adapter<AddictionAdapter.AddictionViewHolder> {

    private final List<String> addictionList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String addiction);
    }

    public AddictionAdapter(List<String> addictionList, OnItemClickListener listener) {
        this.addictionList = addictionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddictionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new AddictionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddictionViewHolder holder, int position) {
        String addiction = addictionList.get(position);
        holder.bind(addiction, listener);
    }

    @Override
    public int getItemCount() {
        return addictionList.size();
    }

    public static class AddictionViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public AddictionViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String addiction, OnItemClickListener listener) {
            textView.setText(addiction);
            itemView.setOnClickListener(v -> listener.onItemClick(addiction));
        }
    }
}
