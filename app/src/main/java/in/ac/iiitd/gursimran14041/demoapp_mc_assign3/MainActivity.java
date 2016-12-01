package in.ac.iiitd.gursimran14041.demoapp_mc_assign3;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById (R.id.viewPager);
        mTabLayout = (TabLayout) findViewById (R.id.tabLayout);

        mViewPager.setAdapter (new CustomViewPagerAdapter (getSupportFragmentManager(), getApplicationContext()));
        mTabLayout.setupWithViewPager (mViewPager);

        Toolbar actionBar = (Toolbar) findViewById (R.id.actionBar);
        setSupportActionBar (actionBar);
        actionBar.setTitleTextColor (Color.WHITE);
        actionBar.setNavigationIcon (null);
        actionBar.setSubtitle("2014041");
        actionBar.setSubtitleTextColor (Color.WHITE);

    }

    private class CustomViewPagerAdapter extends FragmentPagerAdapter {
        String[] fragmentTitles = {"Shared Pref", "Storage", "SQLite"};

        public CustomViewPagerAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super (supportFragmentManager);
        }

        @Override
        public Fragment getItem (int position) {
            switch (position) {
                case 0:
                    return new SharedPrefFragment ();
                case 1:
                    return new StorageFragment ();
                case 2:
                    return new SQLiteFragment ();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle (int position) {
            switch (position) {
                case 0:
                    return fragmentTitles[0];
                case 1:
                    return fragmentTitles[1];
                case 2:
                    return fragmentTitles[2];
                default:
                    return null;
            }
        }
    }
}
