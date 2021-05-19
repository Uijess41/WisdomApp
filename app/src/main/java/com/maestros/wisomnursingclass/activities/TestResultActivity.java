package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.databinding.ActivityTestResultBinding;
import com.maestros.wisomnursingclass.model.Test_result_dto;

import org.json.JSONObject;

import static com.maestros.wisomnursingclass.utils.BaseUrl.TEST_RESULT;

public class TestResultActivity extends AppCompatActivity {

    private ActivityTestResultBinding binding;
    private Context context;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTestResultBinding.inflate(getLayoutInflater());
        view=binding.getRoot();
        setContentView(view);


        context=this;

        getData();

    }

    private void getData() {
        AndroidNetworking.post(TEST_RESULT)
                .addBodyParameter("user_id","1")
                .setTag("TEST_RESULT")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response::result",response.toString());

                        String json=response.toString();
                        Gson gson=new Gson();
                        Test_result_dto dto=gson.fromJson(json,Test_result_dto.class);


                        binding.tvResult.setText(dto.getYourMarks());
                        binding.tvWrong.setText(dto.getWrongAnswer());
                        binding.tvRight.setText(dto.getRightAnswer());

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}