package com.maestros.wisomnursingclass.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.CourseContentAdapter;
import com.maestros.wisomnursingclass.model.CourseContentModel;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseContentsActivity extends AppCompatActivity {


    RecyclerView courseContentRecycler;
    private CourseContentAdapter adapter;
    TextView tvToolbar;
    private List<CourseContentModel> courseContentModelList;
    ImageView back;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_contents);

        courseContentRecycler = findViewById(R.id.courseContentRecycler);
        back = findViewById(R.id.back);
        tvToolbar = findViewById(R.id.tvToolbar);
        courseContentRecycler.setItemViewCacheSize(20);
        courseContentRecycler.setHasFixedSize(true);
        courseContentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String contentID = SharedPref.getData(CourseContentsActivity.this, AppConstats.COURSE_CONTENT_ID);
        String COURSE_CONTENT_NAME = SharedPref.getData(CourseContentsActivity.this, AppConstats.COURSE_CONTENT_NAME);

        Log.e("csskcnskc", contentID + "");
        showContents(contentID);
        tvToolbar.setText(COURSE_CONTENT_NAME);

        back.setOnClickListener(view -> finish());

    }

    private void showContents(String contentID) {

        dialog=new ProgressDialog(this);
        dialog.setMessage("please wait...");
        dialog.setTitle("Course Contents");
        dialog.show();

        AndroidNetworking.post("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=course_content_classes_details")
                .addBodyParameter("content_id", contentID)
                .setTag("showContent")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() > 0) {

                            dialog.hide();
                            courseContentModelList = new ArrayList<>();

                            try {
                                for (int i = 0; i < response.length(); i++) {

                                    CourseContentModel model = new CourseContentModel();
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    model.setId(jsonObject.getString("id"));
                                    model.setLink(jsonObject.getString("banner"));
                                    model.setName(jsonObject.getString("class_name"));
                                    model.setPath(jsonObject.getString("path"));
                                    model.setLink2(jsonObject.getString("link"));
                                    courseContentModelList.add(model);

                                }

                                adapter = new CourseContentAdapter(courseContentModelList, CourseContentsActivity.this);
                                courseContentRecycler.setAdapter(adapter);

                            } catch (Exception e) {

                                Log.e("soapas", e.getMessage() + "");
                            }


                        }else {

                            Toast.makeText(CourseContentsActivity.this, "No videos found!!!", Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Log.e("soapas", anError.getMessage() + "");
                    }
                });

    }
}