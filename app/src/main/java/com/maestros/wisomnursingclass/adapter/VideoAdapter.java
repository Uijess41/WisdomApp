package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maestros.wisomnursingclass.databinding.RowLayoutPurchaseHistoryBinding;
import com.maestros.wisomnursingclass.databinding.RowLayoutVideoBinding;
import com.maestros.wisomnursingclass.model.CourseModel;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{

    private Context mContext;
    private List<CourseModel> list;

    public VideoAdapter(Context mContext, List<CourseModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutVideoBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CourseModel modelObject = list.get(position);
        holder.binding.tvCourseName.setText(modelObject.getBookname());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutVideoBinding binding;
        public MyViewHolder(RowLayoutVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
