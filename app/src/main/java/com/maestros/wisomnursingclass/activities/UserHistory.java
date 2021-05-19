package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.CbtAdapter;
import com.maestros.wisomnursingclass.adapter.PurchaseHistoryAdapter;
import com.maestros.wisomnursingclass.databinding.ActivityPurchaseHistoryBinding;
import com.maestros.wisomnursingclass.databinding.ActivityUserHistoryBinding;
import com.maestros.wisomnursingclass.model.Cbt_detail_dto;
import com.maestros.wisomnursingclass.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

public class UserHistory extends AppCompatActivity {
    private ActivityUserHistoryBinding binding;
    private View view;
    private Context context;

    private CbtAdapter adapter;
    private List<Cbt_detail_dto> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserHistoryBinding.inflate(getLayoutInflater());
        view=binding.getRoot();

        setContentView(view);

        context=this;

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        list = new ArrayList<>();
        adapter = new CbtAdapter(context, list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        binding.rvUserHistory.setLayoutManager(mLayoutManager);
        binding.rvUserHistory.setAdapter(adapter);

        getData();

    }

    private void getData() {

       /* CourseModel a = new CourseModel("");
        list.add(a);
        a = new CourseModel("");
        list.add(a);
        a = new CourseModel("");
        list.add(a); a = new CourseModel("");
        list.add(a); a = new CourseModel("");
        list.add(a); a = new CourseModel("");
        list.add(a); a = new CourseModel("");
        list.add(a); a = new CourseModel("");
        list.add(a); a = new CourseModel("");
        list.add(a);



        adapter.notifyDataSetChanged();*/
    }
}