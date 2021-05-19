package com.maestros.wisomnursingclass.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.maestros.wisomnursingclass.cbt.CBTTestActivity;
import com.maestros.wisomnursingclass.databinding.FragmentExamBinding;
import com.maestros.wisomnursingclass.utils.QuestNum;

import org.jetbrains.annotations.NotNull;


public class ExamFragment extends Fragment {

    private FragmentExamBinding binding;
    private Context context;
    private int n;
    private String question_answer;
    public static int questionNumber;
    public static QuestNum num;
    String opt1, opt2, opt3, opt4;

    public ExamFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();

        String quest = getArguments().getString("questionToFind");
        String question_id = getArguments().getString("question_id");
        questionNumber = getArguments().getInt("position");
        question_answer = getArguments().getString("question_answer");

        binding.tvQuestion.setText((questionNumber + 1) + ". " + quest);

        n = Integer.parseInt(question_id);
        Log.e("questionNumber", questionNumber + "");
        Log.e("quest", quest + "");
        Log.e("opt1", opt1 + "");

        //num.finalValue(questionNumber+"");
        binding.tvOption1.setText(CBTTestActivity.cbtOptionsList.get(0).getOptions());
        binding.tvOption2.setText(CBTTestActivity.cbtOptionsList.get(1).getOptions());
        binding.tvOption3.setText(CBTTestActivity.cbtOptionsList.get(2).getOptions());
        binding.tvOption4.setText(CBTTestActivity.cbtOptionsList.get(3).getOptions());

      /*  if (!getArguments().getBoolean("Instant")) {
            if (question_answer.equals(opt1)) {
                binding.tvOption1.setBackground(getResources().getDrawable(R.drawable.txt_border_grey));
            } else if (question_answer.equals(opt2)) {
                binding.tvOption2.setBackground(getResources().getDrawable(R.drawable.txt_border_grey));
            } else if (question_answer.equals(opt3)) {
                binding.tvOption3.setBackground(getResources().getDrawable(R.drawable.txt_border_grey));
            } else if (question_answer.equals(opt4)) {
                binding.tvOption4.setBackground(getResources().getDrawable(R.drawable.txt_border_grey));
            }
        }
*/


     /*   binding.tvOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opt1 = binding.tvOption1.getText().toString();
                Log.e("question_answer", question_answer);
                Log.e("opt1", opt1);

                if (question_answer.equals(opt1)) {
                    binding.tvOption1.setBackground(getResources().getDrawable(R.drawable.txt_border_green));
                } else {
                    binding.tvOption1.setBackground(getResources().getDrawable(R.drawable.txt_border_res));
                }

                binding.tvOption1.setEnabled(false);
                binding.tvOption2.setEnabled(false);
                binding.tvOption3.setEnabled(false);
                binding.tvOption4.setEnabled(false);


//                selectedColourChange(question_answer);

            }
        });

        binding.tvOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opt2 = binding.tvOption2.getText().toString();
                Log.e("question_answer", question_answer);
                Log.e("opt2", opt2);
                if (question_answer.equals(opt2)) {
                    binding.tvOption2.setBackground(getResources().getDrawable(R.drawable.txt_border_green));
                } else {
                    binding.tvOption2.setBackground(getResources().getDrawable(R.drawable.txt_border_res));
                }

                binding.tvOption1.setEnabled(false);
                binding.tvOption2.setEnabled(false);
                binding.tvOption3.setEnabled(false);
                binding.tvOption4.setEnabled(false);


            }
        });

        binding.tvOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opt3 = binding.tvOption3.getText().toString();
                Log.e("question_answer", question_answer);
                Log.e("opt3", opt3);

                if (question_answer.equals(opt3)) {
                    binding.tvOption3.setBackground(getResources().getDrawable(R.drawable.txt_border_green));
                } else {
                    binding.tvOption3.setBackground(getResources().getDrawable(R.drawable.txt_border_res));
                }

                binding.tvOption1.setEnabled(false);
                binding.tvOption2.setEnabled(false);
                binding.tvOption3.setEnabled(false);
                binding.tvOption4.setEnabled(false);


            }
        });

        binding.tvOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opt4 = binding.tvOption4.getText().toString();
                Log.e("question_answer", question_answer);
                Log.e("opt4", opt4);

                if (question_answer.equals(opt4)) {
                    binding.tvOption4.setBackground(getResources().getDrawable(R.drawable.txt_border_green));
                } else {
                    binding.tvOption4.setBackground(getResources().getDrawable(R.drawable.txt_border_res));
                }

                binding.tvOption1.setEnabled(false);
                binding.tvOption2.setEnabled(false);
                binding.tvOption3.setEnabled(false);
                binding.tvOption4.setEnabled(false);

            }
        });
*/
        return view;
    }


  /*  private void selectedColourChange(String in) {
        String choice = questions.get(questionNumber);
        // Changes previous selected button back to original colour
        if (!choice.equals("Z")) {
            if (choice.equals("A")) {
                binding.tvOption1.setBackground(getResources().getDrawable(R.drawable.txt_border_grey));
            }
            else if (choice.equals("B")) {
                secondResponse.setBackgroundColor(getResources().getColor(R.color.questionButtonColour));
            }
            else if (choice.equals("C")) {
                thirdResponse.setBackgroundColor(getResources().getColor(R.color.questionButtonColour));
            }
            else if (choice.equals("D")) {
                fourthResponse.setBackgroundColor(getResources().getColor(R.color.questionButtonColour));
            }
        }
        //Sets the new answer as a different colour
        if (getArguments().getBoolean("Instant", false)) {
            String answer = answers.get(Integer.parseInt(questions.get(questionNumber)[0]));
            if ((answer).equals(in)) {
                input.setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                input.setBackgroundColor(getResources().getColor(R.color.red));
                if (answer.equals("A")) {
                    firstResponse.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else if (answer.equals("B")) {
                    secondResponse.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else if (answer.equals("C")) {
                    thirdResponse.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else if (answer.equals("D")) {
                    fourthResponse.setBackgroundColor(getResources().getColor(R.color.green));
                }
            }
        }
        else {
            input.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        questions.get(questionNumber)[1] = in;   //Mark the answer change in the answers array
    }

    public void setBothColours() {
        if (questions.get(questionNumber)[1].equals("A")) {
            binding.tvOption1.setBackground(getResources().getDrawable(R.drawable.txt_border_res));
        }
        else if (questions.get(questionNumber)[1].equals("B")) {
            binding.tvOption2.setBackground(getResources().getDrawable(R.drawable.txt_border_res));
        }
        else if (questions.get(questionNumber)[1].equals("C")) {
            thirdResponse.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if (questions.get(questionNumber)[1].equals("D")) {
            fourthResponse.setBackgroundColor(getResources().getColor(R.color.red));
        }

        if (answers.get(Integer.parseInt(questions.get(questionNumber)[0])).equals("A")) {
            firstResponse.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if (answers.get(Integer.parseInt(questions.get(questionNumber)[0])).equals("B")) {
            secondResponse.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if (answers.get(Integer.parseInt(questions.get(questionNumber)[0])).equals("C")) {
            thirdResponse.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if (answers.get(Integer.parseInt(questions.get(questionNumber)[0])).equals("D")) {
            fourthResponse.setBackgroundColor(getResources().getColor(R.color.green));
        }

        binding.tvOption1.setEnabled(false);
        binding.tvOption2.setEnabled(false);
        binding.tvOption3.setEnabled(false);
        binding.tvOption4.setEnabled(false);
    }


*/

    public static void sendVal(QuestNum n) {
        num = n;
    }


}