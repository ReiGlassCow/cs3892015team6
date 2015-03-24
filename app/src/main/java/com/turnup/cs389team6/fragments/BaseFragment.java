package com.turnup.cs389team6.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dheer on 3/24/2015.
 */
public abstract class BaseFragment extends Fragment{

    //All screens should be implemented in a fragment so we can later optimize for tablet layouts

    //similar lifecycle methods, with some personal modifications


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    public abstract int getLayoutId();

    /*
     * In this method, all your views that you need to do anything with (buttons, lists, etc.)
     * should be assigned.
     */
    public abstract void initializeViews(View view);


}
