package com.example.nguyet.moneymanager3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyet.moneymanager3.Budget.Budget_List;
import com.example.nguyet.moneymanager3.R;

import java.util.List;

/**
 * Created by Nguyet on 11/8/2017.
 */

public class ListViewAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<Budget_List> arrayBudget;


    public ListViewAdapter(Context context, int layout, List<Budget_List> budget_lists) {
        myContext = context;
        myLayout = layout;
        arrayBudget = budget_lists;

    }

    @Override
    public int getCount() {
        return arrayBudget.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(myLayout, null);

        //Ánh xạ và gán giá trị

        ImageView imgIcon = (ImageView) view.findViewById(R.id.imageAva);
        imgIcon.setImageResource(arrayBudget.get(position).getIconimage());

        TextView txtmoney = (TextView) view.findViewById(R.id.textView_money);
        txtmoney.setText(String.valueOf((arrayBudget.get(position).getTien())));


        TextView txttitle = (TextView) view.findViewById(R.id.textView_Title);
        txttitle.setText(arrayBudget.get(position).getDanhMuc());


        TextView txtdate = (TextView) view.findViewById(R.id.textView_Date);
        txtdate.setText(arrayBudget.get(position).getNgay());



        return view;
    }


}