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
                    //Start difficulty activity
                    switch (v.getId()){
                        case R.id.button_math:
                            mathActivity(null);
                            break;
                    }
                }
            }
        };

        mathButton.setOnClickListener(mOnClickListener);
        geographyButton.setOnClickListener(mOnClickListener);


    }
    public void mathActivity(View v){
        Intent gameDifficulty = new Intent(this,MathOptionsActivity.class);
        startActivity(gameDifficulty);


    }




}
