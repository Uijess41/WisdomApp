package com.maestros.wisomnursingclass.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.databinding.ActivityLoginBinding;
import com.maestros.wisomnursingclass.model.District_dto;
import com.maestros.wisomnursingclass.model.State_dto;
import com.maestros.wisomnursingclass.utils.AppConstats;
import com.maestros.wisomnursingclass.utils.BaseUrl;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.maestros.wisomnursingclass.utils.BaseUrl.LOGIN;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Context context;
    private View view;
    private boolean status;

    String strFullname, strEmail, strMobile, strDist="", strState="", strPassword, strConPassword;
    String strEmailLogin, strPwdLogin;
    String strStateId;
    ProgressDialog pd;

    List<State_dto> stateDtoList;
    List<District_dto> districtDtoList;
    ArrayList<String> statenameList;
    ArrayList<String> districtnameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        context = this;

        status = false;


        if (status) {
            binding.tvLogin.setTextColor(getResources().getColor(R.color.bg));
            binding.tvSignup.setTextColor(getResources().getColor(R.color.white));
            binding.layoutLogin.setVisibility(View.GONE);
            binding.layoutSignup.setVisibility(View.VISIBLE);
        } else {
            binding.tvLogin.setTextColor(getResources().getColor(R.color.white));
            binding.tvSignup.setTextColor(getResources().getColor(R.color.bg));
            binding.layoutSignup.setVisibility(View.GONE);
            binding.layoutLogin.setVisibility(View.VISIBLE);
        }


        getState();

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = false;
                binding.tvLogin.setTextColor(getResources().getColor(R.color.white));
                binding.tvSignup.setTextColor(getResources().getColor(R.color.bg));
                binding.layoutSignup.setVisibility(View.GONE);
                binding.layoutLogin.setVisibility(View.VISIBLE);
            }
        });

        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = true;
                binding.tvLogin.setTextColor(getResources().getColor(R.color.bg));
                binding.tvSignup.setTextColor(getResources().getColor(R.color.white));
                binding.layoutLogin.setVisibility(View.GONE);
                binding.layoutSignup.setVisibility(View.VISIBLE);


            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strEmailLogin = binding.etEmailLogin.getText().toString();
                strPwdLogin = binding.etPwdLogin.getText().toString();
                if (!isValidEmail(strEmailLogin)) {
                    binding.etEmailLogin.setError("Email is invalid");
                    binding.etEmailLogin.requestFocus();
                } else if (strEmailLogin.isEmpty()) {
                    binding.etEmailLogin.setError("Email is required");
                    binding.etEmailLogin.requestFocus();
                } else if (strPwdLogin.isEmpty()) {
                    binding.etPwdLogin.setError("Password is required");
                    binding.etPwdLogin.requestFocus();
                } else {
                    loginSend();
                }
            }
        });


        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strFullname = binding.etFullname.getText().toString();
                strEmail = binding.etEmail.getText().toString();
                strMobile = binding.etMobile.getText().toString();
//                strDist=binding.etDist.getText().toString();
//                strState=binding.etState.getText().toString();
                strPassword = binding.etPassword.getText().toString();
                strConPassword = binding.etConfirmPass.getText().toString();

                if (!isValidEmail(strEmail)) {
                    binding.etEmail.setError("Email is invalid");
                    binding.etEmail.requestFocus();
                } else if (strFullname.isEmpty()) {
                    binding.etFullname.setError("Fullname is required");
                    binding.etFullname.requestFocus();
                } else if (strEmail.isEmpty()) {
                    binding.etEmail.setError("Email is required");
                    binding.etEmail.requestFocus();
                } else if (strMobile.isEmpty()) {
                    binding.etMobile.setError("Mobile is required");
                    binding.etMobile.requestFocus();
                }else if (strDist.equals("")){
                    Toast.makeText(context, "District is required", Toast.LENGTH_SHORT).show();
                    binding.etDist.requestFocus();
                }else if (strState.equals("")){
                    Toast.makeText(context, "State is required", Toast.LENGTH_SHORT).show();
                    binding.etState.requestFocus();
                } else if (strPassword.isEmpty()) {
                    binding.etPassword.setError("Password is required");
                    binding.etPassword.requestFocus();
                } else if (strConPassword.isEmpty()) {
                    binding.etConfirmPass.setError("Confirm Password is required");
                    binding.etConfirmPass.requestFocus();
                } else if (!strConPassword.equals(strPassword)) {
                    binding.etConfirmPass.setError("Password not matched");
                    binding.etConfirmPass.requestFocus();
                } else {
                    signupSend();
                }
            }
        });
    }

    private void getState() {

        AndroidNetworking.post(BaseUrl.STATE)
                .setTag("STATE")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        pd.dismiss();
                        Log.e("response", response.toString() + "");
                        try {

                            stateDtoList = new ArrayList<>();
                            statenameList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String country_id = jsonObject.getString("country_id");

                                if (country_id.equals("101")) {
                                    State_dto categoryDetailDto = new State_dto();
                                    categoryDetailDto.setName(name);
                                    categoryDetailDto.setCountryId(country_id);
                                    categoryDetailDto.setId(id);
                                    stateDtoList.add(categoryDetailDto);
                                }
                            }

                            for (State_dto categoryDetailDto : stateDtoList) {
                                statenameList.add(categoryDetailDto.getName());
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, statenameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            arrayAdapter.notifyDataSetChanged();
                            binding.spinnerState.setAdapter(arrayAdapter);


                            binding.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    strState = adapterView.getSelectedItem().toString();

                                    Log.e("skjfdks", strState + "");

                                    State_dto sb = stateDtoList.get(i);
                                    strStateId = sb.getId();
                                    getDistrict(sb.getId());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sdfdsfsf", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
//                        pd.dismiss();
                        Log.e("rtuygjmnb", anError.getMessage() + "");
                    }
                });

    }

    private void getDistrict(String stateid) {
        Log.e("stateid", stateid);
        AndroidNetworking.post(BaseUrl.DISTRICT)
                .addBodyParameter("state_id", stateid)
                .setTag("DISTRICT")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response_dist", response.toString() + "");
                        try {

                            districtDtoList = new ArrayList<>();
                            districtnameList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String distid = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String state_id = jsonObject.getString("state_id");

                                if (stateid.equals(state_id)) {
                                    District_dto districtDto = new District_dto();
                                    districtDto.setName(name);
                                    districtDto.setStateId(state_id);
                                    districtDto.setId(distid);
                                    districtDtoList.add(districtDto);
                                }
                            }

                            for (District_dto dis : districtDtoList) {
                                districtnameList.add(dis.getName());
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, districtnameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            arrayAdapter.notifyDataSetChanged();
                            binding.spinnerDist.setAdapter(arrayAdapter);

                            binding.spinnerDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    strDist = adapterView.getSelectedItem().toString();
                                    Log.e("skjfdks", strDist + "");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sdfdsfsf", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("rtuygjmnb", anError.getMessage() + "");
                    }
                });

    }


    private void signupSend() {
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.show();
        AndroidNetworking.post(BaseUrl.SIGNUP)
                .addBodyParameter("full_name", strFullname)
                .addBodyParameter("email", strEmail)
                .addBodyParameter("mobile", strMobile)
                .addBodyParameter("dist", strDist)
                .addBodyParameter("state", strState)
                .addBodyParameter("password", strPassword)
                .addBodyParameter("token_id", "123")
                .setTag("SIGNUP")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        Log.e("rtuygjmnb", response.toString() + "");
                        try {
                            if (response.getString("result").equals("Registration Successfull")) {
                                binding.tvLogin.setTextColor(getResources().getColor(R.color.white));
                                binding.tvSignup.setTextColor(getResources().getColor(R.color.bg));
                                binding.layoutSignup.setVisibility(View.GONE);
                                binding.layoutLogin.setVisibility(View.VISIBLE);

                            } else {

                            }

                            Toast.makeText(context, "" + response.getString("result"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sdfdsfsf", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("rtuygjmnb", anError.getMessage() + "");
                        pd.dismiss();
                    }
                });

    }

    private void loginSend() {
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.show();

        AndroidNetworking.post(BaseUrl.LOGIN)
                .addBodyParameter("action", LOGIN)
                .addBodyParameter("email", strEmailLogin)
                .addBodyParameter("password", strPwdLogin)
                .addBodyParameter("token_id", "123")
                .setTag("LOGIN")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        pd.dismiss();

                        Log.e("response_brand", response + "");

                        try {
                            if (response.getString("result").equals("Login SuccessFull")) {
                                SharedPref.setData(context, SharedPref.NAME, response.getString("full_name"));
                                SharedPref.setData(context, SharedPref.USERID, response.getString("id"));
                                SharedPref.setData(context, SharedPref.EMAIL, response.getString("email"));
                                SharedPref.setData(context, SharedPref.MOBILE, response.getString("mobile"));
                                SharedPref.setData(context, SharedPref.DISTRICT, response.getString("dist"));
                                SharedPref.setData(context, SharedPref.STATE, response.getString("state"));
                                SharedPref.setData(context, SharedPref.IMAGE, response.getString("image"));
                                SharedPref.setData(context, SharedPref.PATH, response.getString("path"));
                                SharedPref.setData(context, AppConstats.USER_ID, response.getString("id"));


                                startActivity(new Intent(context, MainActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            } else {

                            }
                            Toast.makeText(context, "" + response.getString("result"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}