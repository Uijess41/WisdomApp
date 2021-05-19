package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.databinding.ActivityOfflineBatchBinding;
import com.maestros.wisomnursingclass.databinding.ActivityOfflinebatchDetailBinding;
import com.maestros.wisomnursingclass.model.Offline_batch_dto;

import org.json.JSONObject;

import static com.maestros.wisomnursingclass.utils.BaseUrl.OFFLINE_BATCH_DETAILS;

public class OfflinebatchDetail extends AppCompatActivity {

    private ActivityOfflinebatchDetailBinding binding;
    private View view;
    private Context context;
    private String batch_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityOfflinebatchDetailBinding.inflate(getLayoutInflater());
        view=binding.getRoot();
        setContentView(view);

        context=this;

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (getIntent()!=null){
            batch_id=getIntent().getStringExtra("batch_id");
        }


        getData();


    }

    private void getData() {
        AndroidNetworking.post(OFFLINE_BATCH_DETAILS)
                .addBodyParameter("id",batch_id)
                .setTag("OFFLINE_BATCH_DETAILS")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String json=response.toString();
                        Gson gson=new Gson();
                        Offline_batch_dto dto=gson.fromJson(json,Offline_batch_dto.class);


                        Glide.with(context)
                                .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.wisdom_logo))
                                .load(dto.getPath()+dto.getBanner())
                                .override(120, 100)
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.ivBook);

                        binding.toolbar.setTitle(dto.getClassName());


                        Glide.with(context)
                                .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.wisdom_logo))
                                .load(dto.getPath()+dto.getBanner())
                                .into(binding.ivBanner);

                        binding.tvDescr.setText(dto.getTextName());

                        binding.tvInquiry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, ""+dto.getLink(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}