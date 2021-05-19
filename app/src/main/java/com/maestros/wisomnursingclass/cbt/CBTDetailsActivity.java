package com.maestros.wisomnursingclass.cbt;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.BaseUrl;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CBTDetailsActivity extends AppCompatActivity {

    TextView buyNow, tvDescr, txtTitle, price, offerPrice, startTest;
    ImageView iv_book;
    String startTime = "", endTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_b_t_details);
        buyNow = findViewById(R.id.buyNow);
        tvDescr = findViewById(R.id.tvDescr);
        iv_book = findViewById(R.id.iv_book);
        txtTitle = findViewById(R.id.txtTitle);
        price = findViewById(R.id.price);
        offerPrice = findViewById(R.id.offerPrice);
        startTest = findViewById(R.id.startTest);


        String id = SharedPref.getData(getApplicationContext(), AppConstats.CBT_TEST_ID);
        Log.e("scjncxsk", id + "");
        showDesc(id);


        startTest.setOnClickListener(view -> {

        });


        buyNow.setOnClickListener(view -> {

            try {

                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");

                Log.e("mkcnskc", simpleDateFormat.format(currentTime));
                Log.e("mkcnskc", currentTime.toString());

                Date date = simpleDateFormat.parse(startTime);
                Log.e("opoi", "onCreate: "+date.toString());
                startActivity(new Intent(getApplicationContext(), CBTTestActivity.class));

            }catch (Exception e){

            }

        });


    }


    private void showDesc(String id) {

        AndroidNetworking.post(BaseUrl.CBT_DETAIL)
                .addBodyParameter("id", id)
                .setTag("showDesc")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {

                            try {

                                txtTitle.setText(response.getString("cbt_title"));
                                tvDescr.setText("Description : " + Html.fromHtml(response.getString("description")));
                                String lofferPrice = response.getString("offer_price");
                                String lprice = response.getString("price");

                                startTime = response.getString("start_time");
                                endTime = response.getString("end_time");

                                if (!offerPrice.equals("")) {

                                    price.setText("Price : " + lprice);
                                    offerPrice.setText("Rs." + lofferPrice);
                                    price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                                } else {
                                    price.setText("Price : " + lprice);
                                }

                                if (response.getString("Payment_Status").equals("0")) {

                                    buyNow.setVisibility(View.VISIBLE);
                                    startTest.setVisibility(View.GONE);

                                } else {

                                    buyNow.setVisibility(View.GONE);
                                    startTest.setVisibility(View.VISIBLE);
                                }


                                Glide.with(CBTDetailsActivity.this).load(response.getString("path") + response.getString("image")).into(iv_book);


                            } catch (Exception e) {

                                Log.e("nopgkpn", "onResponse: " + e.getMessage());
                            }

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }
}