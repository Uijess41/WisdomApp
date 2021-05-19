package com.maestros.wisomnursingclass.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.adapter.PdfAdapter;
import com.maestros.wisomnursingclass.adapter.VideoAdapter;
import com.maestros.wisomnursingclass.databinding.FragmentPdfBinding;
import com.maestros.wisomnursingclass.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

public class PdfFragment extends Fragment {
    private FragmentPdfBinding binding;
    private Context context;
    private View view;
    private PdfAdapter adapter;
    private List<CourseModel> list;

    public PdfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentPdfBinding.inflate(getLayoutInflater(),container,false);
        view=binding.getRoot();

        context=getActivity();

        list = new ArrayList<>();
        adapter = new PdfAdapter(context, list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        binding.rvPdf.setLayoutManager(mLayoutManager);
        binding.rvPdf.setAdapter(adapter);



        return view;
    }

    private void getData() {
        CourseModel a = new CourseModel("");
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

        adapter.notifyDataSetChanged();
    }
}