package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.cbt.CBTDetailsActivity;
import com.maestros.wisomnursingclass.databinding.RowLayoutUpcomingBinding;
import com.maestros.wisomnursingclass.model.Cbt_detail_dto;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import java.util.List;

public class CbtAdapter extends RecyclerView.Adapter<CbtAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cbt_detail_dto> list;


    public CbtAdapter(Context mContext, List<Cbt_detail_dto> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowLayoutUpcomingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cbt_detail_dto modelObject = list.get(position);

        if (modelObject != null) {

            holder.binding.tvCourseName.setText(modelObject.getCbt_title());
            holder.binding.date.setText("Date: " + modelObject.getDate());
            holder.binding.time.setText("Time: " + modelObject.getStart_time() + "-" + modelObject.getEnd_time());


            if (!modelObject.getOffer_price().equals("")) {
                holder.binding.price.setText("Price: " + modelObject.getPrice());
                holder.binding.offprice.setText("Rs."+modelObject.getOffer_price());
                holder.binding.price.setPaintFlags(holder.binding.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            } else {
                holder.binding.price.setText("Price: " + "Rs."+modelObject.getPrice());
            }


            holder.binding.btnView.setOnClickListener(view -> {
                SharedPref.setData(mContext, AppConstats.CBT_TEST_ID,modelObject.getId());
                mContext.startActivity(new Intent(mContext, CBTDetailsActivity.class));
            });

            Glide.with(mContext).load(modelObject.getPath() + modelObject.getImage()).placeholder(R.drawable.wisdom_logo).into(holder.binding.rl);

        }


    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutUpcomingBinding binding;

        public MyViewHolder(RowLayoutUpcomingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }


    private void stopPlayer(PlayerView pv, SimpleExoPlayer absPlayer) {
        pv.setPlayer(null);
        absPlayer.release();
        absPlayer = null;
    }

}
