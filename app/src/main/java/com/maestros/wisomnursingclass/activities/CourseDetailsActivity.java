package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.MyCourseAdapter;
import com.maestros.wisomnursingclass.databinding.ActivityCourseDetailsBinding;
import com.maestros.wisomnursingclass.model.CourseModel;
import com.maestros.wisomnursingclass.model.Course_detail_dto;
import com.maestros.wisomnursingclass.utils.BaseUrl;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    private ActivityCourseDetailsBinding binding;
    private Context context;
    private String courseId="";
    private String from="";
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        View view1 = binding.getRoot();
        setContentView(view1);

        context=this;

        binding.toolbar.setNavigationOnClickListener(view -> finish());

        if (getIntent()!=null){
            courseId=getIntent().getStringExtra("course_id");
            String str_amount = getIntent().getStringExtra("str_amount");
            String offer_price = getIntent().getStringExtra("offer_price");
            from=getIntent().getStringExtra("from");
        }

        if (from.equals("paid_test")){
            binding.tvOpen.setVisibility(View.VISIBLE);
        }else if (from.equals("my_course")) {
            binding.tvOpen.setVisibility(View.VISIBLE);
        }

        binding.tvOpen.setOnClickListener(view -> startActivity(new Intent(context,CourseListActivity.class)));

        getData();

    }



    private void getData() {
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.show();
        AndroidNetworking.post(BaseUrl.COURSE_DETAILS)
                .addBodyParameter("id",courseId)
                .setTag("COURSE_DETAILS")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pd.dismiss();
                        Log.e("COURSEdetail>>",jsonObject.toString()+"");


                        String json=jsonObject.toString();
                        Gson gson=new Gson();
                        Course_detail_dto dto=gson.fromJson(json,Course_detail_dto.class);

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.wisdom_logo);
                        requestOptions.fitCenter();

                        Glide.with(context)
                                .applyDefaultRequestOptions(requestOptions)
                                .load(dto.getPath() + "/" + dto.getImage())
                                .into(binding.ivBook);

                        binding.tvCourseName.setText(dto.getCourseName());
                        binding.tvsubject.setText(dto.getSubjectName());
                        binding.tvCostPrice.setText(dto.getOfferPrice());
                        binding.tvActualPrice.setText(dto.getPrice());
                        binding.tvDescr.setText(dto.getDescription());

                        Glide.with(context).load(dto.getPath() + "/" + dto.getBanner()).into(binding.ivBanner);


                        if (dto.getStatus().equals("0")) {
                            binding.tvOpen.setVisibility(View.VISIBLE);
                            binding.tvBuy.setVisibility(View.GONE);
                        }
                        if (dto.getStatus().equals("1")) {
                            binding.tvOpen.setVisibility(View.VISIBLE);
                            binding.tvBuy.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("rtuygjmnb",anError.getMessage()+"");
                    }
                });

    }
}