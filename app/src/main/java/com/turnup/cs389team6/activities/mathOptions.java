package com.turnup.cs389team6.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.turnup.cs389team6.R;

/**
 * Created by Taylor White on 3/26/2015.
 */
public class mathOptions extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameoptions);
    }
    public void mathButtons(View v){
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()){
            case R.id.radioButton:
                Toast.makeText(this,"You have selected difficulty 1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton2:
                Toast.makeText(this,"You have selected difficulty 2",Toast.LENGTH_SHORT).show();
                break;



        }



    }
}