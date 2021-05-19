package com.maestros.wisomnursingclass.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class PlayYoutubeVideoActivity extends AppCompatActivity {


    YouTubePlayerView youtube_player_view;


    String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_youtube_video);
        youtube_player_view = findViewById(R.id.youtube_player_view);

        videoId = SharedPref.getData(getApplicationContext(), AppConstats.YOUTUBE_VIDEO_ID);
        youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.play();
            }
        });
    }
}