package com.maestros.wisomnursingclass.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.YoutubeLiveAdapter;
import com.maestros.wisomnursingclass.model.YoutubeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YoutubeLiveActivity extends AppCompatActivity {

    RecyclerView yLiveVideosRecycler;

    List<YoutubeModel> youtubeModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_live);

        yLiveVideosRecycler = findViewById(R.id.yLiveVideosRecycler);
        yLiveVideosRecycler.setHasFixedSize(true);
        yLiveVideosRecycler.setItemViewCacheSize(20);
        yLiveVideosRecycler.setLayoutManager(new GridLayoutManager(this,2));

        showLiveVideo();


    }


    private void showLiveVideo() {


        AndroidNetworking.post("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=youtube_live_video")
                .setTag("live")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() > 0) {
                            youtubeModelList = new ArrayList<>();
                            try {

                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject jsonObject = response.getJSONObject(i);
                                    YoutubeModel model = new YoutubeModel();
                                    model.setName(jsonObject.getString("link"));

                                    youtubeModelList.add(model);
                                }

                                yLiveVideosRecycler.setAdapter(new YoutubeLiveAdapter(youtubeModelList, YoutubeLiveActivity.this));

                            } catch (Exception e) {

                                Log.e("cskcjs", e.getMessage() + "");
                            }


                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("cskcjs", anError.getMessage() + "");
                    }
                });
    }
}