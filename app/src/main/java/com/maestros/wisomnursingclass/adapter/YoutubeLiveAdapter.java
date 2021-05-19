package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.activities.PlayYoutubeVideoActivity;
import com.maestros.wisomnursingclass.model.YoutubeModel;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import java.util.List;

public class YoutubeLiveAdapter extends RecyclerView.Adapter<YoutubeLiveAdapter.Viewholder> {

    List<YoutubeModel> youtubeModelList;
    Context context;

    public YoutubeLiveAdapter(List<YoutubeModel> youtubeModelList, Context context) {
        this.youtubeModelList = youtubeModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public YoutubeLiveAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ylive_video_list, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeLiveAdapter.Viewholder holder, int position) {
        YoutubeModel model = youtubeModelList.get(position);
        if (model != null) {

            holder.tvName.setText("Video "+(position+1));

            String[] id = model.getName().split("=");
            String videoId = id[1];

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPref.setData(context, AppConstats.YOUTUBE_VIDEO_ID, videoId);
                    context.startActivity(new Intent(context, PlayYoutubeVideoActivity.class));
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return youtubeModelList.size();
    }


    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView tvName;
        CardView card;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            card = itemView.findViewById(R.id.card);


        }
    }
}
