package com.maestros.wisomnursingclass.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.databinding.ActivityTestBinding;
import com.maestros.wisomnursingclass.fragments.ExamFragment;
import com.maestros.wisomnursingclass.model.Option;
import com.maestros.wisomnursingclass.model.test.Question_dto;
import com.maestros.wisomnursingclass.utils.QuestNum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.maestros.wisomnursingclass.utils.BaseUrl.QUESTION;
import static com.maestros.wisomnursingclass.utils.BaseUrl.SUBMIT_ANSWER;

public class TestActivity extends AppCompatActivity /*implements ViewPager.OnPageChangeListener*/{

    private ActivityTestBinding binding;
    private FragmentActivity fragmentActivity;
    private Context context;
    public static List<Option> optionList;
    public static List<Question_dto> questionDtoList;
    private boolean isInstantAnswer;
    private FragmentStateAdapter  mPagerAdapter;
    public static ArrayList<String[]> questions;
    int quest_count,finalVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = this;
        fragmentActivity=this;

        optionList = new ArrayList<>();
        questionDtoList = new ArrayList<>();
        questions = new ArrayList<>();

        getQuestions();

        isInstantAnswer = getIntent().getBooleanExtra("InstantFeedback", true);

        ExamFragment.sendVal(new QuestNum() {
            @Override
            public void finalValue(String val) {
                finalVal= Integer.parseInt(val);

                Log.e("finalVal",finalVal+"");
                if (finalVal == (quest_count-1)) {
                    binding.tvNext.setText("Submit");
                } else {
                    binding.tvNext.setText("Next");
                }
            }
        });


        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.tvNext.getText().toString().equals("Submit")) {
                     submitTest();

                    Toast.makeText(context, "Test Submitted", Toast.LENGTH_SHORT).show();
                } else {
                    delay();
                }
            }
        });


        long seventymin = 1000 * 60 * 60;
        // in milliseconds i.e. 60 seconds
        //                finishGame();
        CountDownTimer countDownTimer = new CountDownTimer(seventymin, 1000) {

            public void onTick(long millisUntilFinished) {
                int totalTime = 60000; // in milliseconds i.e. 60 seconds
                String v = String.format("%02d", millisUntilFinished / totalTime);
                int va = (int) ((millisUntilFinished % totalTime) / 1000);
                binding.tvTimer.setText(v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
//                finishGame();
            }
        };
        countDownTimer.start();


        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                Log.e("position::onpage",position+"");

            }

        });


        binding.ivTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, TestResultActivity.class));
            }
        });
        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delay();
            }
        });

        binding.tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delayPrev();
            }
        });

    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Bundle b = new Bundle();
            Log.e("position:::::::",questionDtoList.get(position).getId()+"");
            b.putString("questionToFind", questionDtoList.get(position).getQuestion());
            b.putString("question_id", questionDtoList.get(position).getOptions().get(position).getId());
            b.putInt("position", position);
            b.putBoolean("Instant", isInstantAnswer);
            b.putString("question_answer", questionDtoList.get(position).getAnswer());
            ExamFragment examFragment = new ExamFragment();
            examFragment.setArguments(b);
            return examFragment;
        }

        @Override
        public int getItemCount() {
            return questionDtoList.size();
        }
    }

    private void submitTest() {
        AndroidNetworking.post(SUBMIT_ANSWER)
                .addBodyParameter("user_id", "1")
                .addBodyParameter("course_id", "1")
                .addBodyParameter("exam_id", "1")
                .addBodyParameter("question_id", "1")
                .setTag("SUBMIT_ANSWER")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response:::submit", response.toString());
                        try {
                            startActivity(new Intent(context, TestResultActivity.class));
                            Toast.makeText(context, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void getQuestions() {
        AndroidNetworking.post(QUESTION)
                .addBodyParameter("user_id", "1")
                .addBodyParameter("course_id", "1")
                .addBodyParameter("exam_id", "1")
                .addBodyParameter("question_id", "1")
                .setTag("QUESTION")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("dcxcxcxxz", response.toString());

                        try {

                            quest_count = response.getInt("Questions_count");
                            if (quest_count > 0) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String answer = jsonObject.getString("answer");
                                    String course_id = jsonObject.getString("course_id");
                                    String id = jsonObject.getString("id");
                                    String Question = jsonObject.getString("Question");
                                    String type = jsonObject.getString("type");

                                    JSONArray jsonArray1 = jsonObject.getJSONArray("options");

                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject1 = jsonArray1.getJSONObject(j);

                                        String option_id = jsonObject1.getString("id");
                                        String options = jsonObject1.getString("options");
                                        String question_id = jsonObject1.getString("question_id");

                                        Option option = new Option();
                                        option.setId(option_id);
                                        option.setOptions(options);
                                        option.setQuestionId(question_id);

                                        optionList.add(option);
                                    }

                                    Question_dto questionDto = new Question_dto();
                                    questionDto.setAnswer(answer);
                                    questionDto.setCourseId(course_id);
                                    questionDto.setId(id);
                                    questionDto.setQuestion(Question);
                                    questionDto.setType(type);
                                    questionDto.setOptions(optionList);

                                    questionDtoList.add(questionDto);

                                }

                                mPagerAdapter = new ScreenSlidePagerAdapter(fragmentActivity);
                                binding.pager.setAdapter(mPagerAdapter);
                                binding.tvQuestNo.setText(quest_count + "");

                            } else {
                                Toast.makeText(context, "There is no question!", Toast.LENGTH_SHORT).show();
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


    private void delay() {
        final Handler handler = new Handler();
        final int duration;
        duration = 600;
        final int currentItem = binding.pager.getCurrentItem();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (binding.pager.getCurrentItem() < 99 && currentItem == binding.pager.getCurrentItem()) {
                    binding.pager.setCurrentItem(currentItem + 1);
                }
            }
        }, duration);
    }


    private void delayPrev() {
        final Handler handler = new Handler();
        final int duration;
        duration = 600;
        final int currentItem = binding.pager.getCurrentItem();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (binding.pager.getCurrentItem() < 99 && currentItem == binding.pager.getCurrentItem()) {
                    binding.pager.setCurrentItem(currentItem - 1);
                }
            }
        }, duration);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (binding.pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            binding.pager.setCurrentItem(binding.pager.getCurrentItem() - 1);
        }

        /*if (keyCode == KeyEvent.KEYCODE_BACK) {
            binding.pager.setCurrentItem(0, true);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }*/

        return super.onKeyDown(keyCode, event);

    }



}