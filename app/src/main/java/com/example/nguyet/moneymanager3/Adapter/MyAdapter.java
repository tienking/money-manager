package com.example.nguyet.moneymanager3.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.nguyet.moneymanager3.Fragment.FragmentChart;
import com.example.nguyet.moneymanager3.Fragment.FragmentExpense;
import com.example.nguyet.moneymanager3.Fragment.FragmentIncome;

/**
 * Created by Nguyet on 10/13/2017.
 */

public class MyAdapter extends FragmentStatePagerAdapter{
    private String listTab[] ={"EXPENSE","INCOME","CHART"};
    private FragmentExpense fragmentExpense;
    private FragmentIncome fragmentIncome;
    private FragmentChart fragmentChart;
    public MyAdapter(FragmentManager fm) {
        super(fm);
        fragmentExpense = new FragmentExpense();
        fragmentIncome = new FragmentIncome();
        fragmentChart = new FragmentChart();
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return fragmentExpense;
        }else if(position==1){
            return fragmentIncome;
        }else if (position==2){
            return fragmentChart;
        }

        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  listTab[position];
    }
}
