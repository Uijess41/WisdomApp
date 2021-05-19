package com.maestros.wisomnursingclass.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.maestros.wisomnursingclass.R;

import org.json.JSONObject;

public class ContactUsActivity extends AppCompatActivity {

    TextView email, number;
    ImageView back;

    String strNumber = "", strEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        email = findViewById(R.id.email);
        number = findViewById(R.id.number);
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> finish());


        number.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + strNumber));
                startActivity(intent);

            } catch (Exception e) {

                Log.e("scjsjc", e.getMessage() + "");
            }

        });

        showContacts();
    }


    private void showContacts() {

        AndroidNetworking.post("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=contact_us")
                .setTag("contacts")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {
                            try {
                                email.setText("Email : " + response.getString("email"));
                                number.setText("Mobile Number : " + response.getString("mobile"));

                                strNumber = response.getString("mobile");
                                strEmail = response.getString("email");
                            } catch (Exception e) {

                            }


                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}