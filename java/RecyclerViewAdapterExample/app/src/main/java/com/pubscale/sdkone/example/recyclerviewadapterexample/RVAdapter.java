package com.pubscale.sdkone.example.recyclerviewadadapterexample;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pubscale.sdkone.example.recyclerviewadadapterexample.databinding.ItemRecyclerViewBinding;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    private ArrayList<String> list = new ArrayList<>();

    public void submitList(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RVAdapter.RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecyclerViewBinding binding = ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RVViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.RVViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder {

        ItemRecyclerViewBinding binding;

        public RVViewHolder(@NonNull ItemRecyclerViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String string) {
            binding.tv.setText(string);
        }
    }
}
