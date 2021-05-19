package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.databinding.ActivityLoginBinding;
import com.maestros.wisomnursingclass.databinding.ActivityUpdateProfileBinding;
import com.maestros.wisomnursingclass.model.District_dto;
import com.maestros.wisomnursingclass.model.State_dto;
import com.maestros.wisomnursingclass.utils.BaseUrl;
import com.maestros.wisomnursingclass.utils.Connectivity;
import com.maestros.wisomnursingclass.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.maestros.wisomnursingclass.utils.BaseUrl.UPDATE_PROFILE;

public class UpdateProfile extends AppCompatActivity {
    private ActivityUpdateProfileBinding binding;
    private Context context;
    private View view;
    private static final String IMAGE_DIRECTORY = "/directory";
    private int GALLERY = 1, CAMERA = 2;
    File f;
    List<State_dto> stateDtoList;
    List<District_dto> districtDtoList;
    ArrayList<String> statenameList;
    ArrayList<String> districtnameList;
    static String strDist,strState,strStateId;
    String Fullname,Email,Mobile,Password;
    private Connectivity connectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        view=binding.getRoot();
        setContentView(view);

        context=this;
        connectivity=new Connectivity(context);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getState();

        binding.ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.wisdom_logo);
        requestOptions.fitCenter();

        Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(SharedPref.getData(context, SharedPref.PATH) + "/" + SharedPref.getData(context, SharedPref.IMAGE))
                .into(binding.ivLogo);


        strDist="0";
        strState="0";

        binding.etEmail.setText(SharedPref.getData(context,SharedPref.EMAIL));
        binding.etMobile.setText(SharedPref.getData(context,SharedPref.MOBILE));
        binding.etFullname.setText(SharedPref.getData(context,SharedPref.NAME));

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fullname=binding.etFullname.getText().toString();
                Email=binding.etEmail.getText().toString();
                Mobile=binding.etMobile.getText().toString();
                Password=binding.etPassword.getText().toString();

                if (connectivity.isOnline(context)) {
                    sendData();
                }else {
                    Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendData() {

    AndroidNetworking.upload(UPDATE_PROFILE)
            .addMultipartFile("image",f)
            .addMultipartParameter("id", SharedPref.getData(context,SharedPref.USERID))
            .addMultipartParameter("email",Email)
            .addMultipartParameter("mobile",Mobile)
            .addMultipartParameter("password",Password)
            .addMultipartParameter("dist",strDist)
            .addMultipartParameter("state",strState)
            .addMultipartParameter("full_name",Fullname)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("response:::profile",response+"");

                    try {
                        if (response.getString("message").equals("Profile Updated Successfull")) {
                            SharedPref.setData(context, SharedPref.USERID, response.getString("id"));
                            SharedPref.setData(context, SharedPref.EMAIL, response.getString("email"));
                            SharedPref.setData(context, SharedPref.MOBILE, response.getString("mobile"));
                            SharedPref.setData(context, SharedPref.DISTRICT, response.getString("dist"));
                            SharedPref.setData(context, SharedPref.STATE, response.getString("state"));
                            SharedPref.setData(context, SharedPref.IMAGE, response.getString("image"));
                            SharedPref.setData(context, SharedPref.PATH, response.getString("path"));
                            Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
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

    public void showPictureDialog() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        choosePhotoFromGallery();
                        break;

                    case 1:
                        captureFromCamera();
                        break;
                }
            }
        });

        builder.show();
    }

    public void choosePhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY);
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpeg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},
                    new String[]{"image/jpg"}, null);
            fo.close();
            Log.d("fvbcbv", "File Saved::---&gt;" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void captureFromCamera() {
        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA);
    }

    private void getState() {

        AndroidNetworking.post(BaseUrl.STATE)
                .setTag("STATE")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response",response.toString()+"");
                        try {

                            stateDtoList=new ArrayList<>();
                            statenameList=new ArrayList<>();

                            for (int i=0;i<response.length();i++){
                                JSONObject jsonObject=response.getJSONObject(i);
                                String id=jsonObject.getString("id");
                                String name=jsonObject.getString("name");
                                String country_id=jsonObject.getString("country_id");

                                if (country_id.equals("101")) {
                                    State_dto categoryDetailDto = new State_dto();
                                    categoryDetailDto.setName(name);
                                    categoryDetailDto.setCountryId(country_id);
                                    categoryDetailDto.setId(id);
                                    stateDtoList.add(categoryDetailDto);
                                }
                            }

                            for (State_dto categoryDetailDto:stateDtoList){
                                statenameList.add(categoryDetailDto.getName());
                            }

                            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(context,R.layout.custom_spinner,statenameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            arrayAdapter.notifyDataSetChanged();
                            binding.spinnerState.setAdapter(arrayAdapter);


                            binding.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    strState=adapterView.getSelectedItem().toString();

                                    State_dto sb=stateDtoList.get(i);
                                    strStateId=sb.getId();
                                    getDistrict(sb.getId());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sdfdsfsf",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
//                        pd.dismiss();
                        Log.e("rtuygjmnb",anError.getMessage()+"");
                    }
                });

    }

    private void getDistrict(String stateid) {
        Log.e("stateid", stateid );
        AndroidNetworking.post(BaseUrl.DISTRICT)
                .addBodyParameter("state_id",stateid)
                .setTag("DISTRICT")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response_dist",response.toString()+"");
                        try {

                            districtDtoList=new ArrayList<>();
                            districtnameList=new ArrayList<>();

                            for (int i=0;i<response.length();i++){
                                JSONObject jsonObject=response.getJSONObject(i);
                                String distid=jsonObject.getString("id");
                                String name=jsonObject.getString("name");
                                String state_id=jsonObject.getString("state_id");

                                if (stateid.equals(state_id)) {
                                    District_dto districtDto = new District_dto();
                                    districtDto.setName(name);
                                    districtDto.setStateId(state_id);
                                    districtDto.setId(distid);
                                    districtDtoList.add(districtDto);
                                }
                            }

                            for (District_dto dis:districtDtoList){
                                districtnameList.add(dis.getName());
                            }

                            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(context,R.layout.custom_spinner,districtnameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            arrayAdapter.notifyDataSetChanged();
                            binding.spinnerDist.setAdapter(arrayAdapter);

                            binding.spinnerDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    strDist=adapterView.getSelectedItem().toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sdfdsfsf",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("rtuygjmnb",anError.getMessage()+"");
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    binding.ivLogo.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            binding.ivLogo.setImageBitmap(thumbnail);
            saveImage(thumbnail);
        }
    }
}