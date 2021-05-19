package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.activities.PaidCourseDetailsActivity;
import com.maestros.wisomnursingclass.databinding.RowLayoutCourseBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import java.util.List;

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<CourseModel> list;

    public MyCourseAdapter(Context mContext, List<CourseModel> list) {
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
        CourseModel modelObject = list.get(position);
        holder.binding.tvCourseName.setText(modelObject.getCourse_name());
        // holder.binding.tvsubject.setText(modelObject.getSubject_name());
        holder.binding.tvPrice.setText("₹" + modelObject.getPrice());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.wisdom_logo);
        requestOptions.fitCenter();

        Log.e("img", modelObject.getPath() + modelObject.getImage());
        Glide.with(mContext)
                .applyDefaultRequestOptions(requestOptions)
                .load(modelObject.getPath() + "/" + modelObject.getImage())
                .into(holder.binding.ivBook);

        holder.itemView.setOnClickListener(view -> {

            Log.e("csncjs", modelObject.getId()+"");
            mContext.startActivity(new Intent(mContext, PaidCourseDetailsActivity.class));
            SharedPref.setData(mContext, AppConstats.PAID_COURSE_ID, modelObject.getId());
            SharedPref.setData(mContext, AppConstats.COURSE_CLICK, "mycourse");

        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutCourseBinding binding;

        public MyViewHolder(RowLayoutCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
