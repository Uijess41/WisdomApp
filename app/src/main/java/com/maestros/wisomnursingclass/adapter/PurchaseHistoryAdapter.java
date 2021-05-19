package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maestros.wisomnursingclass.databinding.RowLayoutOfflineBinding;
import com.maestros.wisomnursingclass.databinding.RowLayoutPurchaseHistoryBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.model.Purchase_dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.MyViewHolder>{

    private Context mContext;
    private List<Purchase_dto> list;
    SimpleDateFormat dfDate;
    public PurchaseHistoryAdapter(Context mContext, List<Purchase_dto> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutPurchaseHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Purchase_dto modelObject = list.get(position);
        holder.binding.tvCourseName.setText(modelObject.getCourseName());
        holder.binding.tvSubjectName.setText(modelObject.getSubjectName());
        holder.binding.tvAmount.setText("Rs."+modelObject.getAmount());
        Glide.with(mContext).load(modelObject.getPath()+"/"+modelObject.getImage()).into(holder.binding.ivBook);

        dfDate  = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date1 = dfDate.parse(modelObject.getDate());
            Date date2 = dfDate.parse(modelObject.getCurentDate());

            long different = date2.getTime()-date1.getTime();
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;

            holder.binding.tvDay.setText("1Day");
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutPurchaseHistoryBinding binding;
        public MyViewHolder(RowLayoutPurchaseHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
