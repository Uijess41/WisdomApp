package com.maestros.wisomnursingclass.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.BlogAdapter;
import com.maestros.wisomnursingclass.adapter.CbtAdapter;
import com.maestros.wisomnursingclass.databinding.FragmentBlogBinding;
import com.maestros.wisomnursingclass.databinding.FragmentExamBinding;
import com.maestros.wisomnursingclass.model.Blog_dto;
import com.maestros.wisomnursingclass.utils.BaseUrl;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment {
    private FragmentBlogBinding binding;
    private Context context;
    private View view;
    private BlogAdapter adapter;
    private List<Blog_dto> list;
    public BlogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlogBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();

        context=getActivity();
        list = new ArrayList<>();

        getData();

        return view;
    }

    private void getData() {
        AndroidNetworking.post(BaseUrl.SHOW_BLOG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String json=response.toString();

                        Gson gson=new Gson();
                        Type userListType = new TypeToken<ArrayList<Blog_dto>>(){}.getType();

                        list = gson.fromJson(json, userListType);

                        adapter = new BlogAdapter(context, list);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
                        binding.rvBlogs.setLayoutManager(mLayoutManager);
                        binding.rvBlogs.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}