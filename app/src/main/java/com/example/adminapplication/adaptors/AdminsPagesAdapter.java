package com.example.adminapplication.adaptors;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.adminapplication.fragments.AdminsFragment;
import com.example.adminapplication.fragments.AdminsRequestsFragment;


public class AdminsPagesAdapter extends FragmentStatePagerAdapter {

    public AdminsPagesAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: return AdminsFragment.newInstance();
            case 1: return AdminsRequestsFragment.newInstance();
            default: return AdminsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
