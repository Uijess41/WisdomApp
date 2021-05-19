package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.databinding.ActivityDownloadsBinding;
import com.maestros.wisomnursingclass.databinding.ActivityPurchaseHistoryBinding;
import com.maestros.wisomnursingclass.fragments.OldClassFragment;
import com.maestros.wisomnursingclass.fragments.PdfFragment;
import com.maestros.wisomnursingclass.fragments.UpcomingClassFragment;
import com.maestros.wisomnursingclass.fragments.VideoFragment;

public class Downloads extends AppCompatActivity {
    private ActivityDownloadsBinding binding;
    private View view;
    private Context context;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDownloadsBinding.inflate(getLayoutInflater());
        view=binding.getRoot();

        setContentView(view);

        context=this;

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    tabLayout=(TabLayout)findViewById(R.id.tabLayout);
    viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Video"));
        tabLayout.addTab(tabLayout.newTab().setText("Pdf"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    final MyAdapterDownload adapter = new MyAdapterDownload(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });

}
}

class MyAdapterDownload extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapterDownload(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                VideoFragment upcomingClassFragment = new VideoFragment();
                return upcomingClassFragment;
            case 1:
                PdfFragment oldClassFragment = new PdfFragment();
                return oldClassFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}