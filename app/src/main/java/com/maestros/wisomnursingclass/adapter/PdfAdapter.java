package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maestros.wisomnursingclass.databinding.RowLayoutPdfBinding;
import com.maestros.wisomnursingclass.databinding.RowLayoutVideoBinding;
import com.maestros.wisomnursingclass.model.CourseModel;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.MyViewHolder>{

    private Context mContext;
    private List<CourseModel> list;

    public PdfAdapter(Context mContext, List<CourseModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutPdfBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CourseModel modelObject = list.get(position);
       // holder.binding.tvCourseName.setText(modelObject.getBookname());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutPdfBinding binding;
        public MyViewHolder(RowLayoutPdfBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
