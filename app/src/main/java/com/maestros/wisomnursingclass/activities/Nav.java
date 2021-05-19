package com.maestros.wisomnursingclass.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.activities.ui.gallery.SyllabusFragment;
import com.maestros.wisomnursingclass.activities.ui.home.HomeFragment;
import com.maestros.wisomnursingclass.activities.ui.slideshow.MessageFragment;
import com.maestros.wisomnursingclass.databinding.ActivityNavBinding;
import com.maestros.wisomnursingclass.fragments.BlogFragment;
import com.maestros.wisomnursingclass.utils.SharedPref;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Nav extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityNavBinding binding;
    private View view;
    private Context context;
    Spinner type;

    String[] type_spin = { "Competition Exam", "College"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        binding=ActivityNavBinding.inflate(getLayoutInflater());
        view=binding.getRoot();
        setContentView(view);

        context=this;

        Toolbar toolbar = findViewById(R.id.ntoolbar);
        type = findViewById(R.id.type);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    binding.drawerLayout.closeDrawer(GravityCompat.END);
                }
            }
        });

        type.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,R.layout.custom_spinner,type_spin);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(aa);

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new HomeFragment());

        binding.navHead.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,UpdateProfile.class));
            }
        });


            Glide.with(context)
                    .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.wisdom_logo))
                    .load(SharedPref.getData(context, SharedPref.PATH) + "/" + SharedPref.getData(context, SharedPref.IMAGE))
                    .into(binding.navHead.imageView);


        binding.navHead.tvName.setText(SharedPref.getData(context,SharedPref.NAME));
        binding.navHead.tvEmail.setText(SharedPref.getData(context,SharedPref.EMAIL));
        binding.navHead.tvMobile.setText(SharedPref.getData(context,SharedPref.MOBILE));

        binding.layoutDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }

                startActivity(new Intent(context,Downloads.class));

            }
        });

        binding.layoutUsageHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }

                startActivity(new Intent(context,UserHistory.class));
            }
        });

        binding.layoutPurchaseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }

                startActivity(new Intent(context,PurchaseHistory.class));
            }
        });

        binding.layoutOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }

                startActivity(new Intent(context,OfflineBatchActivity.class));
            }
        });

        binding.layoutSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
//                startActivity(new Intent(context,CBTActivity.class));
            }
        });

        binding.layoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }

                startActivity(new Intent(context,ContactUsActivity.class));
            }
        });


        binding.linSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }

               startActivity(new Intent(getApplicationContext(),SettingActivity.class));

            }
        });

        binding.layoutInvite.setOnClickListener(view -> {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Wisdom Nursing Class");
            sendIntent.setType("text/plain");
            try {
                startActivity(Intent.createChooser(sendIntent, "Choose one"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Please Install App", Toast.LENGTH_LONG).show();
            }

        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.logout(context,SharedPref.USERID);
                startActivity(new Intent(context,LoginActivity.class));
                finish();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.menu_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.menu_feed:
                    fragment = new SyllabusFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.menu_messages:
                    fragment = new BlogFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        getSupportFragmentManager().popBackStack();
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.wisdom_logo);
        requestOptions.fitCenter();

        Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(SharedPref.getData(context, SharedPref.PATH) + "/" + SharedPref.getData(context, SharedPref.IMAGE))
                .into(binding.navHead.imageView);

        binding.navHead.tvName.setText(SharedPref.getData(context,SharedPref.NAME));
        binding.navHead.tvEmail.setText(SharedPref.getData(context,SharedPref.EMAIL));
        binding.navHead.tvMobile.setText(SharedPref.getData(context,SharedPref.MOBILE));
    }
}