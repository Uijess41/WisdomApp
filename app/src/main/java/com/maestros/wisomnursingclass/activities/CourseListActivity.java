package com.maestros.wisomnursingclass.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.maestros.wisomnursingclass.adapter.CourseUnlockAdapter;
import com.maestros.wisomnursingclass.adapter.MyCourseListAdapter;
import com.maestros.wisomnursingclass.databinding.ActivityCourseListBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.model.CourseUnlockModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.maestros.wisomnursingclass.utils.BaseUrl.COURSE_CONTENT;

public class CourseListActivity extends AppCompatActivity {
    private ActivityCourseListBinding binding;
    private Context context;

    private CourseUnlockAdapter adapter;
    private List<CourseUnlockModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = this;

        binding.toolbar.setNavigationOnClickListener(view1 -> finish());


        modelList = new ArrayList<>();


        getData();

    }

    private void getData() {

        AndroidNetworking.post(COURSE_CONTENT)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("rsponse:::Course_content", response.toString());

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String course_id = jsonObject.getString("course_id");
                                String content_name = jsonObject.getString("content_name");

                                CourseUnlockModel model = new CourseUnlockModel();
                                model.setId(id);
                                model.setTitle(content_name);
                                modelList.add(model);

                            }

                            adapter = new CourseUnlockAdapter(context, modelList);

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                            binding.rvCourseList.setLayoutManager(mLayoutManager);
                            binding.rvCourseList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}