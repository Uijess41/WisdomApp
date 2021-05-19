package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.activities.CourseContentsActivity;
import com.maestros.wisomnursingclass.model.CourseUnlockModel;
import com.maestros.wisomnursingclass.model.Course_content_dto;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import java.util.List;

public class CourseUnlockAdapter extends RecyclerView.Adapter<CourseUnlockAdapter.MyViewHolder> {

    private Context mContext;
    private final List<CourseUnlockModel> list;

    public CourseUnlockAdapter(Context mContext, List<CourseUnlockModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public CourseUnlockAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.courses_file_list, parent, false);
        return new CourseUnlockAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseUnlockAdapter.MyViewHolder holder, int position) {
        CourseUnlockModel modelObject = list.get(position);

        if (modelObject!=null){
            holder.tv_course_name.setText(modelObject.getTitle());

            if (modelObject.getPaymentStatus().equals("1")){
                holder.lock.setVisibility(View.GONE);
                holder.relContent.setOnClickListener(view -> {
                    SharedPref.setData(mContext, AppConstats.COURSE_CONTENT_ID,modelObject.getId());
                    SharedPref.setData(mContext, AppConstats.COURSE_CONTENT_NAME,modelObject.getTitle());
                    mContext.startActivity(new Intent(mContext, CourseContentsActivity.class));
                });

            }else {
                if (modelObject.getLockStatus().equals("1")){
                    holder.lock.setVisibility(View.GONE);
                    holder.relContent.setOnClickListener(view -> {
                        SharedPref.setData(mContext, AppConstats.COURSE_CONTENT_ID,modelObject.getId());
                        SharedPref.setData(mContext, AppConstats.COURSE_CONTENT_NAME,modelObject.getTitle());
                        mContext.startActivity(new Intent(mContext, CourseContentsActivity.class));
                    });
                }else {
                    holder.lock.setVisibility(View.VISIBLE);

                }
            }





        }


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_course_name;
        ImageView lock;
        RelativeLayout relContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            lock = itemView.findViewById(R.id.lock);
            relContent = itemView.findViewById(R.id.relContent);
        }
    }
}

