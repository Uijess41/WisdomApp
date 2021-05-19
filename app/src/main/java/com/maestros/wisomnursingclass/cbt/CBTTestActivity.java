package com.maestros.wisomnursingclass.cbt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.fragments.ExamFragment;
import com.maestros.wisomnursingclass.model.CBTtestModel;
import com.maestros.wisomnursingclass.utils.BaseUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CBTTestActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    List<CBTtestModel> cbTtestModelList;
    public static List<CBTtestModel.CBTOptions> cbtOptionsList;
    private FragmentStateAdapter mPagerAdapter;
    private FragmentActivity fragmentActivity;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_b_t_test);

        viewPager = findViewById(R.id.pager);
        fragmentActivity = this;


        startTest();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        public ScreenSlidePagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Bundle b = new Bundle();
            Log.e("position:::::::", cbTtestModelList.get(position).getId() + "");
            b.putString("questionToFind", cbTtestModelList.get(position).getQuestion());
            b.putString("question_id", cbTtestModelList.get(position).getCbtOptionsList().get(position).getId());
            b.putInt("position", position);
            b.putString("question_answer", cbTtestModelList.get(position).getAnswer());
            ExamFragment examFragment = new ExamFragment();
            examFragment.setArguments(b);
            return examFragment;
        }

        @Override
        public int getItemCount() {
            return cbTtestModelList.size();
        }
    }


    private void startTest() {

        dialog = new ProgressDialog(this);
        dialog.setTitle("Start test");
        dialog.setMessage("please wait....");
        dialog.show();

        AndroidNetworking.post(BaseUrl.CBT_QUIZ)
                .setTag("startTest")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {

                            try {

                                dialog.dismiss();

                                JSONArray jsonArray = new JSONArray(response.getString("question"));
                                if (jsonArray.length() > 0) {

                                    cbTtestModelList = new ArrayList<>();
                                    cbtOptionsList = new ArrayList<>();

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        CBTtestModel cbTtestModel = new CBTtestModel();
                                        String u = jsonObject.getString("options");

                                        cbTtestModel.setQuestion(jsonObject.getString("Question"));


                                        JSONArray optArry = new JSONArray(u);

                                        for (int j = 0; j < optArry.length(); j++) {
                                            JSONObject object = optArry.getJSONObject(j);
                                            CBTtestModel.CBTOptions cbtOptions = new CBTtestModel.CBTOptions();
                                            Log.e("psxopsx", object.getString("options") + "");

                                            cbtOptions.setId(object.getString("id"));
                                            cbtOptions.setQuestion_id(object.getString("question_id"));
                                            cbtOptions.setOptions(object.getString("options"));
                                            cbtOptionsList.add(cbtOptions);
                                            cbTtestModel.setCbtOptionsList(cbtOptionsList);

                                        }

                                        cbTtestModelList.add(cbTtestModel);

                                    }

                                    mPagerAdapter = new CBTTestActivity.ScreenSlidePagerAdapter(fragmentActivity);
                                    viewPager.setAdapter(mPagerAdapter);

                                }
                            } catch (Exception e) {
                                dialog.dismiss();
                                Log.e("sodisoa", e.getMessage() + "");
                            }

                        }else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Log.e("sodisoa", anError.getMessage() + "");
                    }
                });

    }
}