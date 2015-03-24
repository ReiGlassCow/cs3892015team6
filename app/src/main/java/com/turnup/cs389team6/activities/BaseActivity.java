package com.turnup.cs389team6.activities;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Dheer on 3/24/2015.
 */
public abstract class BaseActivity extends Activity {

    //BaseClass for all of our activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stub
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    //later by system
    //usually you do nothing here.
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
