package com.maestros.wisomnursingclass.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.maestros.wisomnursingclass.R;

import org.json.JSONObject;

public class TermsAndConditionActivity extends AppCompatActivity {

    TextView text;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        text = findViewById(R.id.text);
        back = findViewById(R.id.back);

        back.setOnClickListener(view -> finish());
        showTc();
    }


    private void showTc() {


        AndroidNetworking.get("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=terms_condition")
                .setTag("tc")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {
                            try {
                                text.setText(Html.fromHtml(response.getString("terms_condition")));
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