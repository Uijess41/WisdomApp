package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.activities.OfflinebatchDetail;
import com.maestros.wisomnursingclass.databinding.RowLayoutCourseBinding;
import com.maestros.wisomnursingclass.databinding.RowLayoutOfflineBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.model.Offline_batch_dto;

import java.util.List;

public class OfflineBatchAdapter extends RecyclerView.Adapter<OfflineBatchAdapter.MyViewHolder>{

    private Context mContext;
    private List<Offline_batch_dto> list;

    public OfflineBatchAdapter(Context mContext, List<Offline_batch_dto> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutOfflineBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Offline_batch_dto modelObject = list.get(position);
        holder.binding.tvCourseName.setText(modelObject.getTextName());

        Glide.with(mContext)
                .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.wisdom_logo))
                .load(modelObject.getPath()+modelObject.getBanner())
                .override(120, 100)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.ivBook);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, OfflinebatchDetail.class)
                .putExtra("batch_id",modelObject.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutOfflineBinding binding;
        public MyViewHolder(RowLayoutOfflineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
