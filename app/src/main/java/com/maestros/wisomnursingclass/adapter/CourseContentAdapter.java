package com.maestros.wisomnursingclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.activities.CourseContentYoutubeActivity;
import com.maestros.wisomnursingclass.activities.VideoPlayerActivity;
import com.maestros.wisomnursingclass.model.CourseContentModel;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import java.util.List;

public class CourseContentAdapter extends RecyclerView.Adapter<CourseContentAdapter.Viewholder> {

    List<CourseContentModel> courseContentModelList;
    Context context;

    public CourseContentAdapter(List<CourseContentModel> courseContentModelList, Context context) {
        this.courseContentModelList = courseContentModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseContentAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_content_list, parent, false);
        return new CourseContentAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseContentAdapter.Viewholder holder, int position) {

        CourseContentModel model = courseContentModelList.get(position);
        if (model != null) {

            if (!model.getName().equals("")) {
                holder.title.setText(model.getName() + "");
            } else {
                holder.title.setText("Not available");
            }


            if (model.getLink2().contains("www.youtube.com")) {

                holder.youtube.setVisibility(View.VISIBLE);
                holder.mp4.setVisibility(View.GONE);

            } else {
                holder.youtube.setVisibility(View.GONE);
                holder.mp4.setVisibility(View.VISIBLE);
            }


            holder.watchNow.setOnClickListener(view -> {

                if (model.getLink2().contains("www.youtube.com")) {
                    SharedPref.setData(context, AppConstats.VIDEO_NAME, model.getLink2());
                    context.startActivity(new Intent(context, CourseContentYoutubeActivity.class));
                } else {

                    String extension = "";

                        int i = model.getLink2().lastIndexOf('.');
                        if (i > 0) {

                            extension = model.getLink2().substring(i + 1);
                        }
                        Log.e("wuewds", model.getLink2() + "," + " r :: " + extension);

                        if (extension.equals("mov") || extension.equals("AVI") || extension.equals("avi") || extension.equals("WMV") || extension.equals("wmv") || extension.equals("MP4") || extension.equals("mp4") || extension.equals("FLV") || extension.equals("flv") || extension.equals("WEBM") || extension.equals("webm") || extension.equals("3gp")) {
                            SharedPref.setData(context, AppConstats.VIDEO_NAME, model.getPath()+model.getLink2());
                            context.startActivity(new Intent(context, VideoPlayerActivity.class));
                        }else {
                            Toast.makeText(context, "Invalid resource", Toast.LENGTH_SHORT).show();
                        }




                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return courseContentModelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView title, watchNow;
        ImageView youtube, mp4;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            watchNow = itemView.findViewById(R.id.watchNow);
            youtube = itemView.findViewById(R.id.youtube);
            mp4 = itemView.findViewById(R.id.mp4);
        }
    }
}
