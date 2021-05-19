package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.activities.CourseDetailsActivity;
import com.maestros.wisomnursingclass.databinding.RowLayoutCourseBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.model.MyTest_dto;

import java.util.List;

public class MyTestAdapter extends RecyclerView.Adapter<MyTestAdapter.MyViewHolder>{

    private Context mContext;
    private List<MyTest_dto> list;

    public MyTestAdapter(Context mContext, List<MyTest_dto> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutCourseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyTest_dto modelObject = list.get(position);
        holder.binding.tvCourseName.setText(modelObject.getExamName());
        holder.binding.tvPrice.setText("₹"+modelObject.getPrice());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.wisdom_logo);
        requestOptions.fitCenter();

        Log.e("img",modelObject.getPath()+modelObject.getImage());
        Glide.with(mContext)
                .applyDefaultRequestOptions(requestOptions)
                .load(modelObject.getPath()+"/"+modelObject.getImage())
                .into(holder.binding.ivBook);

        holder.itemView.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, CourseDetailsActivity.class)
        .putExtra("course_id",modelObject.getId())
                .putExtra("from","my_test")
        .putExtra("offer_price",modelObject.getPrice())
                .putExtra("str_amount",modelObject.getPrice())));


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final RowLayoutCourseBinding binding;
        public MyViewHolder(RowLayoutCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
