package com.turnup.cs389team6.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.turnup.cs389team6.R;

/**
 * Created by Dheer on 3/24/2015.
 */
public class NavigationActivity extends BaseActivity {

    private Button mathButton, geographyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


    }

    public void mathActivity(View v) {
        Intent gameDifficulty = new Intent(this, MathOptionsActivity.class);
        startActivity(gameDifficulty);


    }


}
