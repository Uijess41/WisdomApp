package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.model.MyNotesModel;

import java.util.List;

public class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.Viewholder> {

    List<MyNotesModel> myNotesModelList;
    Context context;

    public MyNotesAdapter(List<MyNotesModel> myNotesModelList, Context context) {
        this.myNotesModelList = myNotesModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyNotesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout_pdf, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNotesAdapter.Viewholder holder, int position) {

        MyNotesModel model = myNotesModelList.get(position);
        if (model != null) {

            holder.fileName.setText(model.getTitle());
            holder.tvPrice.setText("Price : "+model.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return myNotesModelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView fileName, tvPrice, tvOfferPrice;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            fileName = itemView.findViewById(R.id.fileName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOfferPrice = itemView.findViewById(R.id.tvOfferPrice);
        }
    }
}
