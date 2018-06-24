package com.example.nguyet.moneymanager3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyet.moneymanager3.R;
import com.example.nguyet.moneymanager3.entities.DanhMucChi;

import java.util.List;

/**
 * Created by Nguyet on 11/8/2017.
 */

public class DanhMucChiAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    List<DanhMucChi> arrayListDanhMucChi;

    public DanhMucChiAdapter(Context context, int myLayout, List<DanhMucChi> arrayListDanhMucChi) {
        this.context = context;
        this.myLayout = myLayout;
        this.arrayListDanhMucChi = arrayListDanhMucChi;
    }

    @Override
    public int getCount() {
        return arrayListDanhMucChi.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(myLayout,null);

        TextView txtDanhMuc =(TextView)view.findViewById(R.id.txtDanhmuc);
        txtDanhMuc.setText(arrayListDanhMucChi.get(i).Danhmucchi);

        ImageView imageHinh =(ImageView) view.findViewById(R.id.imageView_listdanhmuc);
        imageHinh.setImageResource(arrayListDanhMucChi.get(i).Hinhchi);

        return view;
    }
}
