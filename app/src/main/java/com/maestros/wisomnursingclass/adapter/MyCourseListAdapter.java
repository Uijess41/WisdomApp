package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maestros.wisomnursingclass.activities.TestActivity;
import com.maestros.wisomnursingclass.databinding.RowLayoutCourseBinding;
import com.maestros.wisomnursingclass.databinding.RowLayoutCourseListBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.model.Course_content_dto;

import java.util.List;

public class MyCourseListAdapter extends RecyclerView.Adapter<MyCourseListAdapter.MyViewHolder>{

    private Context mContext;
    private List<Course_content_dto> list;

    public MyCourseListAdapter(Context mContext, List<Course_content_dto> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutCourseListBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Course_content_dto modelObject = list.get(position);
        holder.binding.tvCourseName.setText(modelObject.getContentName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, TestActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutCourseListBinding binding;
        public MyViewHolder(RowLayoutCourseListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
