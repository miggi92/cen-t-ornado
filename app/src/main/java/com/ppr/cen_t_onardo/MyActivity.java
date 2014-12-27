package com.ppr.cen_t_onardo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import java.util.HashMap;


public class MyActivity extends Activity {

    private EditText EstCost;
    private TextView Sub;
    private Button One;
    private Button Two;
    private Button Five;
    private Button Ten;
    private Button Twenty;
    private Button Fifty;
    private Button LowFive;
    private Button LowTen;
    private Button TwentyFive;
    private Button ClearAll;
    private Button ClearAmount;
    private double DblEstCost = 0.00;
    private double Number;
    private boolean Clear = false;

    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-56135998-3";

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    private static final String TEST_DEVICE_ID = "TEST_EMULATOR";
    //Google Analytics
    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
    public Tracker tracker;
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        GoogleAnalytics.getInstance(this).newTracker(PROPERTY_ID);
        GoogleAnalytics.getInstance(this).getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        tracker = getTracker(TrackerName.APP_TRACKER);
        tracker.send(new HitBuilders.AppViewBuilder().build());
        tracker.enableAdvertisingIdCollection(true);
        setContentView(R.layout.activity_my);


        // The "loadAdOnCreate" and "testDevices" XML attributes no longer available.
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)
                .build();
        adView.loadAd(adRequest);

        EstCost = (EditText) findViewById(R.id.editText);
        Sub = (TextView) findViewById(R.id.textView3);
        One = (Button) findViewById(R.id.One);
        Two = (Button) findViewById(R.id.Two);
        Five = (Button) findViewById(R.id.Five);
        Ten = (Button) findViewById(R.id.Ten);
        Twenty = (Button) findViewById(R.id.Twenty);
        LowFive = (Button) findViewById(R.id.LowFive);
        LowTen = (Button) findViewById(R.id.LowTen);
        TwentyFive = (Button) findViewById(R.id.TwentyFive);
        Fifty = (Button) findViewById(R.id.Fifty);
        ClearAll = (Button) findViewById(R.id.ClearAll);
        ClearAmount = (Button) findViewById(R.id.ClearAmount);

        Sub.setVisibility(View.INVISIBLE);

        LowFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 0.05;
                Calculate(Number);
            }
        });
        LowTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 0.10;
                Calculate(Number);
            }
        });
        TwentyFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 0.25;
                Calculate(Number);
            }
        });
        Fifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 0.50;
                Calculate(Number);
            }
        });
        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 1.00;
                Calculate(Number);
            }
        });
        Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 2.00;
                Calculate(Number);
            }
        });
        Five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 5.00;
                Calculate(Number);
            }
        });
        Ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 10.00;
                Calculate(Number);
            }
        });
        Twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 20.00;
                Calculate(Number);
            }
        });

        ClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DblEstCost = 0.00;
                Number = 0.00;
                Clear = false;
                EstCost.setText("0.00");
            }
        });
        ClearAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear = true;
                Sub.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), getString(R.string.ClearAmnt), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Calculate(double number) {
        if (Clear == true){
            number = number * -1;
            Clear = false;
            Sub.setVisibility(View.INVISIBLE);
        }
        DblEstCost = DblEstCost + number;
        DblEstCost = DblEstCost * 100;
        DblEstCost = Math.round(DblEstCost);
        DblEstCost = DblEstCost / 100;
        EstCost.setText(Double.toString(DblEstCost));
    }

}
