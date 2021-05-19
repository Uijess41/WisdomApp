package com.maestros.wisomnursingclass.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.CourseUnlockAdapter;
import com.maestros.wisomnursingclass.model.CourseUnlockModel;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.maestros.wisomnursingclass.utils.BaseUrl.COURSE_CONTENT;

public class ShowFilesActivity extends AppCompatActivity {

    RecyclerView courseUnlockRecycler;
    private CourseUnlockAdapter adapter;
    private List<CourseUnlockModel> modelList;
    Context context;
    ProgressDialog dialog;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_files);
        back = findViewById(R.id.back);
        courseUnlockRecycler = findViewById(R.id.courseUnlockRecycler);
        courseUnlockRecycler.setHasFixedSize(true);
        context = this;


        back.setOnClickListener(view -> finish());
        getData();

    }


    private void getData() {

        dialog=new ProgressDialog(this);
        dialog.setMessage("please wait...");
        dialog.setTitle("Course Contents");
        dialog.show();

        String course_id = SharedPref.getData(ShowFilesActivity.this, AppConstats.PAID_COURSE_ID);
        String userID = SharedPref.getData(ShowFilesActivity.this, AppConstats.USER_ID);

        Log.e("csmscs", course_id + "");
        Log.e("csmscs", userID + "");

        AndroidNetworking.post(COURSE_CONTENT)
                .addBodyParameter("course_id", course_id)
                .addBodyParameter("user_id", userID)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response:::Course_content", response.toString());

                        try {
                            if (response.length() > 0) {

                                dialog.hide();

                                modelList = new ArrayList<>();
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String course_id = jsonObject.getString("course_id");
                                    String content_name = jsonObject.getString("content_name");
                                    String lockStatus = jsonObject.getString("lock_status");
                                    String Payment_Status = jsonObject.getString("Payment_Status");
                                    CourseUnlockModel model = new CourseUnlockModel();
                                    model.setId(id);
                                    model.setTitle(content_name);
                                    model.setLockStatus(lockStatus);
                                    model.setPaymentStatus(Payment_Status);
                                    modelList.add(model);
                                }
                                adapter = new CourseUnlockAdapter(context, modelList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                courseUnlockRecycler.setLayoutManager(mLayoutManager);
                                courseUnlockRecycler.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                dialog.hide();
                                Toast.makeText(context, "No data found!!!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.hide();
                            Log.e("dclkclmslc", e.getMessage() + "");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Log.e("dclkclmslc", anError.getMessage() + "");
                    }
                });

    }
}