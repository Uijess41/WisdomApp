package com.maestros.wisomnursingclass.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaidCourseDetailsActivity extends AppCompatActivity implements PaymentResultListener {


    TextView tv_buy, tvDescr, txtTitle, price, offerPrice, tv_open;
    ImageView iv_book;
    String fPrice = "";
    String[] date;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_course_details);

        tv_buy = findViewById(R.id.tv_buy);
        tvDescr = findViewById(R.id.tvDescr);
        iv_book = findViewById(R.id.iv_book);
        txtTitle = findViewById(R.id.txtTitle);
        price = findViewById(R.id.price);
        offerPrice = findViewById(R.id.offerPrice);
        tv_open = findViewById(R.id.tv_open);


        String click = SharedPref.getData(getApplicationContext(), AppConstats.COURSE_CLICK);

        if (click.equals("mycourse")) {
            tv_buy.setVisibility(View.GONE);
        } else {
            tv_buy.setVisibility(View.VISIBLE);
        }


        tv_open.setOnClickListener(view -> {
            Log.e("czczzc", SharedPref.getData(PaidCourseDetailsActivity.this, AppConstats.PAID_COURSE_ID));
            startActivity(new Intent(getApplicationContext(), ShowFilesActivity.class));
        });


        String userID = SharedPref.getData(PaidCourseDetailsActivity.this, AppConstats.USER_ID);

        showDesc(SharedPref.getData(PaidCourseDetailsActivity.this, AppConstats.PAID_COURSE_ID), userID);

        tv_buy.setOnClickListener(view -> startPayment());
    }


    public void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Wisdom Nursing");
            options.put("description", "App Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            String payment = fPrice;
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", SharedPref.getData(PaidCourseDetailsActivity.this, SharedPref.EMAIL));
            preFill.put("contact", SharedPref.getData(PaidCourseDetailsActivity.this, SharedPref.MOBILE));
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        // payment successfull pay_DGU19rDsInjcF2
        String courseID = SharedPref.getData(PaidCourseDetailsActivity.this, AppConstats.PAID_COURSE_ID);
        String userID = SharedPref.getData(PaidCourseDetailsActivity.this, AppConstats.USER_ID);
        Log.e("lsskdcss", userID + "," + courseID + fPrice);
        buyCourse(userID, courseID, fPrice);

    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("error code " + String.valueOf(i), " -- Payment failed " + s);
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }


    private void showDesc(String id, String userID) {

        dialog=new ProgressDialog(this);
        dialog.setMessage("please wait...");
        dialog.setTitle("Course Detail");
        dialog.show();



        AndroidNetworking.post("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=paid_course_details")
                .addBodyParameter("id", id)
                .addBodyParameter("user_id", userID)
                .setTag("showDesc")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {
                            dialog.hide();

                            try {
                                fPrice = response.getString("offer_price");

                                if (!fPrice.equals("")) {
                                    price.setText("Price : " + response.getString("price"));
                                    offerPrice.setText("Offer price : " + fPrice);
                                    price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                } else {
                                    price.setText("Price : " + response.getString("price"));
                                }

                                Log.e("cixccc", response.toString());
                                txtTitle.setText(response.getString("course_name"));
                                tvDescr.setText("Description : " + response.getString("description"));

                                Glide.with(PaidCourseDetailsActivity.this).load(response.getString("path") + response.getString("banner")).into(iv_book);

                                String purchaseStatus = response.getString("Payment_Status");

                                Log.e("vdkvmld", purchaseStatus + "");

                                if (purchaseStatus.equals("0")) {
                                    tv_buy.setVisibility(View.VISIBLE);
                                } else {
                                    tv_buy.setVisibility(View.GONE);
                                }


                            } catch (Exception e) {

                                Log.e("nopgkpn", "onResponse: " + e.getMessage());
                            }

                        }else {
                            dialog.hide();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                    }
                });


    }


    private void buyCourse(String userID, String courseId, String amount) {

        AndroidNetworking.post("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=buy_course")
                .addBodyParameter("user_id", userID)
                .addBodyParameter("course_id", courseId)
                .addBodyParameter("amount", amount)
                .setTag("buyCourse")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {

                            try {
                                if (response.getString("result").equals("Course Buy Success")) {

                                    Toast.makeText(PaidCourseDetailsActivity.this, "Payment Successfully done", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("csjnckj", e.getMessage() + "");
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}