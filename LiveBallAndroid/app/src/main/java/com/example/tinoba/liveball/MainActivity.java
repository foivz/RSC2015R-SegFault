package com.example.tinoba.liveball;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tinoba.liveball.fragments.MainFragment;
import com.example.tinoba.liveball.fragments.OtherFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter selectionsPagerAdapter;
    private ViewPager viewPager;
    Timer timer;
    TimerTask timerTask;
    Random rand;

    final Handler handler = new Handler();
    private LocationManager locationManager;
    private String provider;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        selectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        rand = new Random();

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(selectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pridružio si se igri", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getActiveFragment(ViewPager container, int position) {
            String name = makeFragmentName(container.getId(), position);
            return  getSupportFragmentManager().findFragmentByTag(name);
        }

        private String makeFragmentName(int viewId, int index) {
            return "android:switcher:" + viewId + ":" + index;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0) return new MainFragment();
            else if(position == 1) return new MapFragment(getApplicationContext());
            else return new OtherFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "POČETNI";
                case 1:
                    return "MAPA";
                case 2:
                    return "OSTALO";
            }
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        mNfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, intent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter.getDefaultAdapter(this) != null)
            mNfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("KEFKEF","Intent uhvaćen");
        MainFragment fragment = (MainFragment) selectionsPagerAdapter.getActiveFragment(viewPager, 0);
        fragment.setNfcTextView(rand.nextInt(4));
    }
}
