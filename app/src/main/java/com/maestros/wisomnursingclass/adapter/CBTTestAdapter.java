package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.model.CBTtestModel;

import java.util.List;

public class CBTTestAdapter extends RecyclerView.Adapter<CBTTestAdapter.Viewholder> {

    List<CBTtestModel> cbTtestModelList;
    Context context;

    public CBTTestAdapter(List<CBTtestModel> cbTtestModelList, Context context) {
        this.cbTtestModelList = cbTtestModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public CBTTestAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cbt_test_layout, parent, false);
        return new CBTTestAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CBTTestAdapter.Viewholder holder, int position) {
        CBTtestModel model = cbTtestModelList.get(position);
        if (model != null) {

            holder.options.setText(model.getCbtOptionsList().get(position).getOptions());


        }
    }

    @Override
    public int getItemCount() {
        return cbTtestModelList.size();
    }


    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView options;
        CardView card;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            options = itemView.findViewById(R.id.options);


        }
    }
}
