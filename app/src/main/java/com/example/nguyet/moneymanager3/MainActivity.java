package com.example.nguyet.moneymanager3;

import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.example.nguyet.moneymanager3.Adapter.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {
    private ViewPager viewPager;
    TextView txtTong;
    int tongthu=0,tongchi=0,tong;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    public void getSoTien()
    {
        symbols.setDecimalSeparator(',');
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
        myRef.child("vitien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("tongtienthu")) {
                    tongthu = dataSnapshot.child("tongtienthu").getValue(Integer.class);
                }
                if (dataSnapshot.hasChild("tongtienchi")) {
                    tongchi = dataSnapshot.child("tongtienchi").getValue(Integer.class);
                }
                tong = dataSnapshot.child("tienbandau").getValue(Integer.class);
                tong = tong +tongthu - tongchi;
                String value = decimalFormat.format(tong);
                txtTong.setText("Số tiền hiện tại: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTong= (TextView) findViewById(R.id.txtTong);
        getSoTien();
        initView();


}

    private void initView(){
    viewPager =(ViewPager)findViewById(R.id.viewpaper);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        TabLayout tabLayout =(TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

}

