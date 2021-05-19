package com.maestros.wisomnursingclass.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maestros.wisomnursingclass.adapter.MyCourseAdapter;
import com.maestros.wisomnursingclass.adapter.MyTestAdapter;
import com.maestros.wisomnursingclass.adapter.PaidCourseAdapter;
import com.maestros.wisomnursingclass.adapter.PaidNotesAdapter;
import com.maestros.wisomnursingclass.adapter.PaidTestAdapter;
import com.maestros.wisomnursingclass.databinding.ActivityMyCourseBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.model.MyTest_dto;
import com.maestros.wisomnursingclass.model.Notes_dto;
import com.maestros.wisomnursingclass.model.PaidCourse_dto;
import com.maestros.wisomnursingclass.model.Paid_test_dto;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.BaseUrl;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.maestros.wisomnursingclass.utils.BaseUrl.MY_TEST_SERIES;
import static com.maestros.wisomnursingclass.utils.BaseUrl.PAID_COURSE;
import static com.maestros.wisomnursingclass.utils.BaseUrl.PAID_NOTES;

public class MyCourse extends AppCompatActivity {

    private ActivityMyCourseBinding binding;

    private Context context;

    private MyCourseAdapter adapter;
    private PaidTestAdapter testAdapter;
    private MyTestAdapter myTestAdapter;
    private PaidCourseAdapter paidCourseAdapter;
    private PaidNotesAdapter paidNotesAdapter;
    private List<CourseModel> list;
    private List<Paid_test_dto> modelList;
    private List<Notes_dto> notesDtoList;
    private List<MyTest_dto> myTestDtoList;
    private List<PaidCourse_dto> paidCourseDtoList;
    ProgressDialog pd;

    String go_to = "";
    //rzp_live_RiV2Q0vKCytZ8B
    //rzp_test_NWJwhBRujEiZYp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyCourseBinding.inflate(getLayoutInflater());
        View view1 = binding.getRoot();
        setContentView(view1);
        context = this;

        binding.toolbar.setNavigationOnClickListener(view -> finish());

        if (getIntent() != null) {
            go_to = getIntent().getStringExtra("go_to");
        }

        binding.tvToolbar.setText(go_to);

        switch (go_to) {
            case "My Course":
                getCourse();
                break;
            case "Paid Test":
                getPaidTest();
                break;
            case "My Test":
                getMyTest();
                break;
            case "Paid Course":
                getPaidCourse();
                break;
            case "Paid Notes":
                getPaidNotes();
                break;
        }


    }

    private void getPaidNotes() {
        AndroidNetworking.post(PAID_NOTES)
                .setTag("PAID_NOTES")
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                notesDtoList = new ArrayList<>();

                String json = response.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Notes_dto>>() {
                }.getType();
                notesDtoList = gson.fromJson(json, listType);

                paidNotesAdapter = new PaidNotesAdapter(context, notesDtoList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                binding.rvCourse.setLayoutManager(mLayoutManager);
                binding.rvCourse.setAdapter(paidNotesAdapter);
                paidNotesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }


    private void getPaidCourse() {
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.show();

        AndroidNetworking.post(PAID_COURSE)
                .setTag("PAID_COURSE")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.dismiss();
                        Log.e("PAID_COURSE", response.toString() + "");
                        try {
                            paidCourseDtoList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String course_name = jsonObject.getString("course_name");
                                String price = jsonObject.getString("price");
                                String image = jsonObject.getString("banner");
                                String offer_price = jsonObject.getString("offer_price");
                                String status = jsonObject.getString("status");
                                String description = jsonObject.getString("description");
                                String banner = jsonObject.getString("banner");
                                String path = jsonObject.getString("path");

                                PaidCourse_dto courseModel = new PaidCourse_dto();
                                courseModel.setId(id);
                                courseModel.setCourseName(course_name);
                                courseModel.setPrice(price);
                                courseModel.setImage(image);
                                courseModel.setOfferPrice(offer_price);
                                courseModel.setStatus(status);
                                courseModel.setDescription(description);
                                courseModel.setBanner(banner);
                                courseModel.setPath(path);

                                paidCourseDtoList.add(courseModel);

                            }

                            paidCourseAdapter = new PaidCourseAdapter(context, paidCourseDtoList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                            binding.rvCourse.setLayoutManager(mLayoutManager);
                            binding.rvCourse.setAdapter(paidCourseAdapter);
                            paidCourseAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sdfdsfsf", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("rtuygjmnb", anError.getMessage() + "");
                    }
                });

    }

    private void getMyTest() {

        AndroidNetworking.post(MY_TEST_SERIES)
                .addBodyParameter("user_id", "1")
                .setTag("MY_TEST_SERIES")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("MY_TEST_SERIES:::", response.toString());

                        myTestDtoList = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String user_id = jsonObject.getString("user_id");
                                String test_id = jsonObject.getString("test_id");
                                String exam_name = jsonObject.getString("exam_name");
                                String price = jsonObject.getString("price");
                                String discount = jsonObject.getString("discount");
                                String strtotime = jsonObject.getString("strtotime");
                                String image = jsonObject.getString("image");
                                String path = jsonObject.getString("path");

                                MyTest_dto myTestDto = new MyTest_dto();
                                myTestDto.setId(id);
                                myTestDto.setUserId(user_id);
                                myTestDto.setTestId(test_id);
                                myTestDto.setExamName(exam_name);
                                myTestDto.setPrice(price);
                                myTestDto.setDiscount(discount);
                                myTestDto.setStrtotime(strtotime);
                                myTestDto.setImage(image);
                                myTestDto.setPath(path);

                                myTestDtoList.add(myTestDto);

                            }

                            myTestAdapter = new MyTestAdapter(context, myTestDtoList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                            binding.rvCourse.setLayoutManager(mLayoutManager);
                            binding.rvCourse.setAdapter(myTestAdapter);
                            myTestAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e("JSONEXCEP:::::", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    private void getPaidTest() {
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.show();
        AndroidNetworking.post(BaseUrl.PAID_TEST_SERIES)
                .setTag("PAID_TEST_SERIES")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.dismiss();
                        Log.e("PAID_TEST_SERIES", response.toString() + "");
                        try {
                            modelList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String user_id = jsonObject.getString("user_id");
                                String test_id = jsonObject.getString("test_id");
                                String test_name = jsonObject.getString("test_name");
                                String exam_name = jsonObject.getString("exam_name");
                                String price = jsonObject.getString("price");
                                String offer_price = jsonObject.getString("offer_price");
                                String image = jsonObject.getString("image");
                                String strtotime = jsonObject.getString("strtotime");
                                String Path = jsonObject.getString("Path");

                                Paid_test_dto courseModel = new Paid_test_dto();
                                courseModel.setId(id);
                                courseModel.setUserId(user_id);
                                courseModel.setTestId(test_id);
                                courseModel.setTestName(test_name);
                                courseModel.setExamName(exam_name);
                                courseModel.setPrice(price);
                                courseModel.setOfferPrice(offer_price);
                                courseModel.setImage(image);
                                courseModel.setStrtotime(strtotime);
                                courseModel.setPath(Path);
                                modelList.add(courseModel);

                            }

                            testAdapter = new PaidTestAdapter(context, modelList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                            binding.rvCourse.setLayoutManager(mLayoutManager);
                            binding.rvCourse.setAdapter(testAdapter);
                            testAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sdfdsfsf", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("rtuygjmnb", anError.getMessage() + "");
                    }
                });

    }

    private void getCourse() {

        Log.e("skxosax", SharedPref.getData(getApplicationContext(), AppConstats.USER_ID) + "");
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.show();
        AndroidNetworking.post(BaseUrl.MY_COURSE)
                .addBodyParameter("user_id", SharedPref.getData(getApplicationContext(), AppConstats.USER_ID))
                .setTag("COURSE_LIST")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.dismiss();
                        Log.e("COURSE_LIST", response.toString() + "");
                        try {
                            list = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                CourseModel courseModel = new CourseModel();

                                String course_id = jsonObject.getString("course_id");
                                String course_name = jsonObject.getString("Course_Name");
                                String price = jsonObject.getString("amount");
                                String image = jsonObject.getString("Banner_image");
                                String path = jsonObject.getString("Path");
                                String offer_price = jsonObject.getString("Offer_price");

                                courseModel.setCourse_name(course_name);
                                courseModel.setId(course_id);
                                courseModel.setPrice(price);
                                courseModel.setImage(image);
                                courseModel.setPath(path);
                                courseModel.setOffer_price(offer_price);
                                list.add(courseModel);

                            }

                            adapter = new MyCourseAdapter(context, list);

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                            binding.rvCourse.setLayoutManager(mLayoutManager);
                            binding.rvCourse.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sdfdsfsf", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("rtuygjmnb", anError.getMessage() + "");
                    }
                });

    }


}