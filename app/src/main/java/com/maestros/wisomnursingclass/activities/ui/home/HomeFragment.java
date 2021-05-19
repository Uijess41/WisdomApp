package com.maestros.wisomnursingclass.activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.activities.CBTActivity;
import com.maestros.wisomnursingclass.activities.MyCourse;
import com.maestros.wisomnursingclass.activities.MyNotesActivity;
import com.maestros.wisomnursingclass.activities.YoutubeLiveActivity;
import com.maestros.wisomnursingclass.databinding.FragmentHomeBinding;
import com.maestros.wisomnursingclass.model.Slider_dto;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.maestros.wisomnursingclass.utils.BaseUrl.SHOW_SLIDER;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    View root;

    private LinearLayout layout1;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    CirclePageIndicator indicator;
    private ArrayList<Slider_dto> ImagesArray;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        root = binding.getRoot();

        mPager = root.findViewById(R.id.pager);
        layout1 = root.findViewById(R.id.layout1);
        indicator = root.findViewById(R.id.indicator);
        ImagesArray = new ArrayList<>();


        binding.layoutPaidCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), MyCourse.class)
                        .putExtra("go_to", "Paid Course"));
            }
        });

        binding.layoutMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), MyCourse.class)
                        .putExtra("go_to", "My Course"));
            }
        });


        binding.layoutPaidTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), MyCourse.class)
                        .putExtra("go_to", "Paid Test"));
            }
        });
        binding.layoutPaidNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), MyCourse.class)
                        .putExtra("go_to", "Paid Notes"));
            }
        });

        binding.layoutMyTest.setOnClickListener(view -> getActivity().startActivity(new Intent(getActivity(), MyCourse.class)
                .putExtra("go_to", "My Test")));

        binding.layoutCbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CBTActivity.class));
            }
        });

        binding.linearyoutubelive.setOnClickListener(view -> getActivity().startActivity(new Intent(getActivity(), YoutubeLiveActivity.class)));


        binding.linMyNotes.setOnClickListener(view -> getActivity().startActivity(new Intent(getActivity(), MyNotesActivity.class)));


        init();

        return root;
    }


    private void init() {

        AndroidNetworking.post(SHOW_SLIDER)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response::slider", response + "");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String url = jsonObject.getString("url");
                                String image = jsonObject.getString("image");
                                String base_url = jsonObject.getString("base_url");
                                Slider_dto sliderDto = new Slider_dto();
                                sliderDto.setImage(image);
                                sliderDto.setBaseUrl(base_url);
                                sliderDto.setUrl(url);
                                ImagesArray.add(sliderDto);
                            }

                            if (getActivity() != null) {
                                mPager.setAdapter(new SlidingImage_Adapter(getActivity(), ImagesArray));
                                indicator.setViewPager(mPager);
                                final float density = getResources().getDisplayMetrics().density;
                                indicator.setRadius(5 * density);
                                NUM_PAGES = response.length();
                                final Handler handler = new Handler();
                                final Runnable Update = () -> {
                                    if (currentPage == NUM_PAGES) {
                                        currentPage = 0;
                                    }
                                    mPager.setCurrentItem(currentPage++, true);
                                };
                                Timer swipeTimer = new Timer();
                                swipeTimer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        handler.post(Update);
                                    }
                                }, 3000, 3000);
                                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                    @Override
                                    public void onPageSelected(int position) {
                                        currentPage = position;

                                    }

                                    @Override
                                    public void onPageScrolled(int pos, float arg1, int arg2) {

                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int pos) {

                                    }
                                });

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

}