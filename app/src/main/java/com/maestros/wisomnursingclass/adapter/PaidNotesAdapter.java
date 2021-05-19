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
import com.maestros.wisomnursingclass.activities.PaidCourseDetailsActivity;
import com.maestros.wisomnursingclass.activities.PdfDetailActivity;
import com.maestros.wisomnursingclass.databinding.RowLayoutCourseBinding;
import com.maestros.wisomnursingclass.databinding.RowLayoutPdfBinding;
import com.maestros.wisomnursingclass.model.Notes_dto;
import com.maestros.wisomnursingclass.model.PaidCourse_dto;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import java.util.List;

public class PaidNotesAdapter extends RecyclerView.Adapter<PaidNotesAdapter.MyViewHolder>{

    private Context mContext;
    private List<Notes_dto> list;

    public PaidNotesAdapter(Context mContext, List<Notes_dto> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutPdfBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notes_dto modelObject = list.get(position);
        holder.binding.fileName.setText(modelObject.getTitle());
        holder.binding.tvPrice.setText("Original Price: "+modelObject.getPrice());
        holder.binding.tvOfferPrice.setText("Offer Price: "+modelObject.getOffer_price());

        holder.binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPref.setData(mContext, AppConstats.PAID_NOTES_ID,modelObject.getId());

                if (modelObject.getOffer_price().equals("")){
                    SharedPref.setData(mContext, AppConstats.PAID_NOTES_PRICE,modelObject.getPrice());
                }else {
                    SharedPref.setData(mContext, AppConstats.PAID_NOTES_PRICE,modelObject.getOffer_price());

                }

                mContext.startActivity(new Intent(mContext, PdfDetailActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final RowLayoutPdfBinding binding;

        public MyViewHolder(RowLayoutPdfBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
