package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.maestros.wisomnursingclass.R;

import org.json.JSONObject;

public class PrivacyPlocyActivity extends AppCompatActivity {

    TextView text;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_plocy);

        text = findViewById(R.id.text);
        back = findViewById(R.id.back);

        back.setOnClickListener(view -> finish());
        showTc();
    }

    private void showTc() {


        AndroidNetworking.get("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=privacy_policy")
                .setTag("pp")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {
                            try {
                                text.setText(Html.fromHtml(response.getString("privacy_policy")));
                            }catch (Exception e){

                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}