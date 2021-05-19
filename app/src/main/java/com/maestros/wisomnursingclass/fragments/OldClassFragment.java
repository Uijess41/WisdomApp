package com.maestros.wisomnursingclass.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.maestros.wisomnursingclass.adapter.CbtAdapter;
import com.maestros.wisomnursingclass.databinding.FragmentOldClassBinding;
import com.maestros.wisomnursingclass.model.Cbt_detail_dto;
import com.maestros.wisomnursingclass.utils.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OldClassFragment extends Fragment {

    private FragmentOldClassBinding binding;
    private Context context;
    private View view;

    private CbtAdapter adapter;
    private List<Cbt_detail_dto> list;

    ProgressDialog dialog;

    public OldClassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOldClassBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();

        context = getActivity();

        list = new ArrayList<>();

        getData();

        return view;
    }

    private void getData() {

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait...");
        dialog.setTitle("Upcoming Test");
        dialog.show();

        AndroidNetworking.post(BaseUrl.OLD_CBT)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("response>>cbt", response + "");

                        list = new ArrayList<>();

                        try {

                            if (response.length() > 0) {
                                dialog.dismiss();
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String course_id = jsonObject.getString("course_id");
                                    String status = jsonObject.getString("status");
                                    String path = jsonObject.getString("path");
                                    String image = jsonObject.getString("image");
                                    String start_time = jsonObject.getString("start_time");
                                    String end_time = jsonObject.getString("end_time");
                                    String price = jsonObject.getString("price");
                                    String offer_price = jsonObject.getString("offer_price");
                                    String start_date = jsonObject.getString("start_date");
                                    String duration_of_exam = jsonObject.getString("duration_of_exam");
                                    String cbt_title = jsonObject.getString("cbt_title");

                                    Cbt_detail_dto cbtDto = new Cbt_detail_dto();
                                    cbtDto.setId(id);
                                    cbtDto.setCbt_title(cbt_title);
                                    cbtDto.setCourse_id(course_id);
                                    cbtDto.setImage(image);
                                    cbtDto.setStart_time(start_time);
                                    cbtDto.setPath(path);
                                    cbtDto.setEnd_time(end_time);
                                    cbtDto.setPrice(price);
                                    cbtDto.setDate(start_date);
                                    cbtDto.setOffer_price(offer_price);
                                    cbtDto.setDuration_of_exam(duration_of_exam);

                                    list.add(cbtDto);

                                }

                                adapter = new CbtAdapter(context, list);

                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);                                binding.rvOldClass.setLayoutManager(mLayoutManager);
                                binding.rvOldClass.setAdapter(adapter);

                                adapter.notifyDataSetChanged();

                            }else {
                                dialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            Log.e("cdkckd", e.getMessage() + "");
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Log.e("cdkckd", anError.getMessage() + "");
                    }
                });

    }
}