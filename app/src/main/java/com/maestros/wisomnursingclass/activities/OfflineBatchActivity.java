package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.MyCourseListAdapter;
import com.maestros.wisomnursingclass.adapter.OfflineBatchAdapter;
import com.maestros.wisomnursingclass.databinding.ActivityOfflineBatchBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.model.Offline_batch_dto;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.maestros.wisomnursingclass.utils.BaseUrl.OFFLINE_BATCH;

public class OfflineBatchActivity extends AppCompatActivity {
    private ActivityOfflineBatchBinding binding;
    private View view;
    private Context context;
    private OfflineBatchAdapter adapter;
    private List<Offline_batch_dto> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityOfflineBatchBinding.inflate(getLayoutInflater());
        view=binding.getRoot();

        setContentView(view);

        context=this;

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        modelList = new ArrayList<>();

        getData();
    }

    private void getData() {

        AndroidNetworking.post(OFFLINE_BATCH)
                .setTag("OFFLINE_BATCH")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("response:::offline",response.toString());

                        Gson gson=new Gson();
                        String json=response.toString();

                        Type listType = new TypeToken<List<Offline_batch_dto>>(){}.getType();
                        modelList = gson.fromJson(json, listType);

                        adapter = new OfflineBatchAdapter(context, modelList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                        binding.rvOfflineBatch.setLayoutManager(mLayoutManager);
                        binding.rvOfflineBatch.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }
}