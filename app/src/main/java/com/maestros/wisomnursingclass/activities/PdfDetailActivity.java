package com.maestros.wisomnursingclass.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONObject;

public class PdfDetailActivity extends AppCompatActivity {


    TextView tv_buy, tvDescr, txtTitle, price, offerPrice, tv_open;
    ImageView iv_book;
    String fPrice = "";
    ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_detail);
        tv_buy = findViewById(R.id.tv_buy);
        tvDescr = findViewById(R.id.tvDescr);
        iv_book = findViewById(R.id.iv_book);
        txtTitle = findViewById(R.id.txtTitle);
        price = findViewById(R.id.price);
        offerPrice = findViewById(R.id.offerPrice);
        tv_open = findViewById(R.id.tv_open);


        String id = SharedPref.getData(PdfDetailActivity.this, AppConstats.PAID_NOTES_ID);

        showPdfDetails(id);


        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCourse();
            }
        });
    }


    public void buyCourse() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.buy_dialog);
        MaterialButton relYes = dialog.findViewById(R.id.btnYes);
        MaterialButton relNo = dialog.findViewById(R.id.btnNo);

        relYes.setOnClickListener(v -> {
            pdialog = new ProgressDialog(PdfDetailActivity.this);
            pdialog.setTitle("Buy notes");
            pdialog.setMessage("please wait...");
            pdialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pdialog.dismiss();

                    String userID = SharedPref.getData(PdfDetailActivity.this, AppConstats.USER_ID);
                    String notesID = SharedPref.getData(PdfDetailActivity.this, AppConstats.PAID_NOTES_ID);
                    String amount = SharedPref.getData(PdfDetailActivity.this, AppConstats.PAID_NOTES_PRICE);
                    buyNotes(userID, notesID, amount);
                }
            }, 2000);
            dialog.dismiss();
        });

        relNo.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Log.e("dskjdsjd", e.getMessage(), e);
        }


    }


    private void showPdfDetails(String id) {

        AndroidNetworking.post("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=paid_notes_detail")
                .addBodyParameter("id", id)
                .setTag("pdfDetail")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {
                            try {
                                fPrice = response.getString("offer_price");
                                offerPrice.setText("Offer price : " + fPrice);
                                Log.e("cixccc", response.toString());
                                txtTitle.setText(response.getString("title"));
                                tvDescr.setText("Description : " + response.getString("description"));
                                price.setText("Price : " + response.getString("price"));


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


    private void buyNotes(String userID, String notesId, String amount) {

        AndroidNetworking.post("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=buy_notes")
                .addBodyParameter("user_id", userID)
                .addBodyParameter("notes_id", notesId)
                .addBodyParameter("amount", amount)
                .setTag("pdfDetail")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {
                            try {

                                if (response.getString("result").equals("Notes Buy Success")) {

                                    Toast.makeText(PdfDetailActivity.this, "Successfully buy", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {

                                Log.e("sdsds", "onResponse: " + e.getMessage());
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("sdsds", "onResponse: " + anError.getMessage());
                    }
                });
    }
}