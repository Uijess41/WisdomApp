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
import com.maestros.wisomnursingclass.adapter.PurchaseHistoryAdapter;
import com.maestros.wisomnursingclass.databinding.ActivityPurchaseHistoryBinding;
import com.maestros.wisomnursingclass.model.Purchase_dto;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.maestros.wisomnursingclass.utils.BaseUrl.PURCHASE_HISTORY;

public class PurchaseHistory extends AppCompatActivity {

    private ActivityPurchaseHistoryBinding binding;
    private View view;
    private Context context;
    private PurchaseHistoryAdapter adapter;
    private List<Purchase_dto> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseHistoryBinding.inflate(getLayoutInflater());
        view = binding.getRoot();

        setContentView(view);

        context = this;

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

        Log.e("xjsnsc", SharedPref.getData(context, SharedPref.USERID));

        AndroidNetworking.post(PURCHASE_HISTORY)
                .addBodyParameter("user_id", SharedPref.getData(context, SharedPref.USERID))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response:::::", response + "");

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String user_id = jsonObject.getString("user_id");
                                String course_id = jsonObject.getString("course_id");
                                String strtotime = jsonObject.getString("strtotime");
                                String amount = jsonObject.getString("amount");
                                String Image = jsonObject.getString("Image");
                                String Course_name = jsonObject.getString("Course_name");
                                String Subject_Name = jsonObject.getString("Subject_Name");
                                String Class_name = jsonObject.getString("Class_name");
                                String date = jsonObject.getString("date");
                                String curent_date = jsonObject.getString("curent_date");
                                String Path = jsonObject.getString("Path");

                                Purchase_dto dto = new Purchase_dto();
                                dto.setId(id);
                                dto.setUserId(user_id);
                                dto.setCourseId(course_id);
                                dto.setStrtotime(strtotime);
                                dto.setAmount(amount);
                                dto.setImage(Image);
                                dto.setCourseName(Course_name);
                                dto.setSubjectName(Subject_Name);
                                dto.setClassName(Class_name);
                                dto.setDate(date);
                                dto.setCurentDate(curent_date);
                                dto.setPath(Path);

                                modelList.add(dto);

                            }

                            adapter = new PurchaseHistoryAdapter(context, modelList);

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                            binding.rvPurchase.setLayoutManager(mLayoutManager);
                            binding.rvPurchase.setAdapter(adapter);
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