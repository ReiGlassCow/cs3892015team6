package com.turnup.cs389team6.activities;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import com.turnup.cs389team6.R;

/**
 * Created by Reilly on 4/20/2015.
 */
public class SplashScreen extends BaseActivity {

    public final int TIME_OUT = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent navigation = new Intent(SplashScreen.this, NavigationActivity.class);
                SplashScreen.this.startActivity(navigation);
                SplashScreen.this.finish();
            }
        }, TIME_OUT);
    }
}