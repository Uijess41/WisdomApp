package com.maestros.wisomnursingclass.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.MyNotesAdapter;
import com.maestros.wisomnursingclass.model.MyNotesModel;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyNotesActivity extends AppCompatActivity {


    List<MyNotesModel> myNotesModelList;
    RecyclerView myNotesRecycler;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        myNotesRecycler = findViewById(R.id.myNotesRecycler);
        back = findViewById(R.id.back);
        myNotesRecycler.setHasFixedSize(true);
        myNotesRecycler.setItemViewCacheSize(20);

        back.setOnClickListener(view -> finish());

        myNotesRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        String userid = SharedPref.getData(this, AppConstats.USER_ID);

        showMyNotes(userid);

    }


    private void showMyNotes(String userID) {

        AndroidNetworking.post("http://wisdomnursing.maestrosinfotech.org/api/process.php?action=my_notes")
                .addBodyParameter("user_id", userID)
                .setTag("showMynotes")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() > 0) {

                            Log.e("cskmcks", response + "");

                            myNotesModelList = new ArrayList<>();

                            try {

                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    MyNotesModel model = new MyNotesModel();
                                    model.setId(jsonObject.getString("id"));
                                    model.setAmount(jsonObject.getString("amount"));
                                    model.setTitle(jsonObject.getString("Tile"));
                                    model.setDesc(jsonObject.getString("description"));

                                    myNotesModelList.add(model);

                                }


                                myNotesRecycler.setAdapter(new MyNotesAdapter(myNotesModelList, MyNotesActivity.this));
                            } catch (Exception e) {

                                Log.e("sckcssc", e.getMessage() + "");
                            }


                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("sckcssc", anError.getMessage() + "");
                    }
                });

    }
}