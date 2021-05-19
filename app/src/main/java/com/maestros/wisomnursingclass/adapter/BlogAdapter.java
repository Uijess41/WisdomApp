package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maestros.wisomnursingclass.databinding.RowLayoutBlogBinding;
import com.maestros.wisomnursingclass.databinding.RowLayoutPdfBinding;
import com.maestros.wisomnursingclass.model.Blog_dto;
import com.maestros.wisomnursingclass.model.CourseModel;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder>{

    private Context mContext;
    private List<Blog_dto> list;

    public BlogAdapter(Context mContext, List<Blog_dto> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutBlogBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Blog_dto modelObject = list.get(position);
        holder.binding.name.setText(modelObject.getDescription());

        Glide.with(mContext).load(modelObject.getPath()+modelObject.getImage()).into(holder.binding.ivPost);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutBlogBinding binding;
        public MyViewHolder(RowLayoutBlogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
