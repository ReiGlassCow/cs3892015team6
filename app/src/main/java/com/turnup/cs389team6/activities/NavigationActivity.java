package com.turnup.cs389team6.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.turnup.cs389team6.R;

/**
 * Created by Dheer on 3/24/2015.
 */
public class NavigationActivity extends BaseActivity{

    private Button mathButton, geographyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mathButton = (Button) findViewById(R.id.button_math);
        geographyButton = (Button) findViewById(R.id.button_geo);

        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v instanceof Button){
                    Button cast = (Button) v;
                    Toast.makeText(
                            NavigationActivity.this,
                            getString(R.string.this_starts_activity, cast.getText()),
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        mathButton.setOnClickListener(mOnClickListener);
        geographyButton.setOnClickListener(mOnClickListener);
    }




}
