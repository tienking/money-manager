package com.example.nguyet.moneymanager3.Add_Ex_Income;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nguyet.moneymanager3.Adapter.DanhMucThuAdapter;
import com.example.nguyet.moneymanager3.R;
import com.example.nguyet.moneymanager3.entities.DanhMucThu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class AddIncomeActivity extends AppCompatActivity {


    EditText editText_date;
    EditText editText_note;
    EditText editText_money;
    Button btnsubmit,btnBack;
    Spinner spinnerDanhMuc;
    String money="",item="";
    DanhMucThu dmt;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    HashMap<String, Integer> hmt = new HashMap<String, Integer>();
    int dd=0,mm=0,yy=0,tongthu=0;
    int chartvalue=0;


    private void mapDanhMucThu() {

        hmt.put("Tiền lương", R.drawable.tienluong);
        hmt.put("Tiền thưởng", R.drawable.tienthuong);
        hmt.put("Khác", R.drawable.khac1);
    }

    public void getTongthu()
    {
        myRef.child("vitien").child("tongtienthu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String a = dataSnapshot.getValue().toString();
                    tongthu = Integer.parseInt(a);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getChartData(final int mn, final String y , final String m)
    {
        myRef.child("bieudo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(y).exists())
                {
                    if(dataSnapshot.child(y).child(m).exists()) {
                        String a = dataSnapshot.child(y).child(m).getValue().toString();
                        chartvalue = Integer.parseInt(a);
                        chartvalue = chartvalue + mn;
                        myRef.child("bieudo").child(y).child(m).setValue(chartvalue);
                    }
                    else myRef.child("bieudo").child(y).child(m).setValue(mn);
                }
                else myRef.child("bieudo").child(y).child(m).setValue(mn);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        editText_date = (EditText) findViewById(R.id.edittext_date);
        editText_note = (EditText) findViewById(R.id.edittext_note);
        editText_money = (EditText) findViewById(R.id.edittext_money);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnsubmit = (Button) findViewById(R.id.btnSubmit);
        editText_money.addTextChangedListener(onTextChangedListener());
        spinnerDanhMuc =(Spinner)findViewById(R.id.spinner_danhmucthu);

        getTongthu();
        final ArrayList<DanhMucThu> arrayDanhMucThu = new ArrayList<DanhMucThu>();
        mapDanhMucThu();
        for(String key: hmt.keySet())
        {
            arrayDanhMucThu.add(new DanhMucThu(hmt.get(key),key));
        }

        DanhMucThuAdapter danhMucThuAdapter = new DanhMucThuAdapter(this,R.layout.custom_danhmuc, arrayDanhMucThu);
        spinnerDanhMuc.setAdapter(danhMucThuAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//Set click vào item
        spinnerDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dmt = arrayDanhMucThu.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDate();

            }
        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str[] = editText_money.getText().toString().split(",");
                String tmp = str[0];
                for(int i =1;i< str.length;i++)
                    tmp= tmp + str[i];
                money = tmp;
                item=dmt.Danhmucthu;
                String d="",m="";
                String y="";
                try{
                    y = Integer.toString(yy);
                    if(mm>=0 && dd >0 && yy>0) {
                        if(dd<10) {
                            d = "0" + Integer.toString(dd);
                        } else {d = Integer.toString(dd);}
                        if(mm<9) {
                            m = "0" + Integer.toString(mm+1);
                        } else {m = Integer.toString(mm+1);}
                        final String date = y+"-"+m+"-"+d;
                        final int mn = Integer.parseInt(money);
                        myRef.addListenerForSingleValueEvent (new ValueEventListener () {
                            int m;
                            public void onDataChange (DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child ("danhmucthu").child(date).child (item).hasChild("sotien")) {
                                    m = mn + dataSnapshot.child ("danhmucthu").child (date).child (item).child ("sotien").getValue (Integer.class);
                                    myRef.child("danhmucthu").child(date).child(item).child("sotien").setValue(m);
                                }
                                else myRef.child("danhmucthu").child(date).child(item).child("sotien").setValue(mn);
                            }

                            @Override
                            public void onCancelled (DatabaseError databaseError) {

                            }
                        });
                        String note = editText_note.getText().toString();
                        if (!note.isEmpty()) {
                            myRef.child("danhmucthu").child(date).child(item).child("note").setValue(note);
                        }
                        tongthu = tongthu + mn;
                        myRef.child("vitien").child("tongtienthu").setValue(tongthu);
                        Toast.makeText(AddIncomeActivity.this, "Success", Toast.LENGTH_LONG).show();
                        getChartData(mn,y,m);
                        finish();
                    }
                    else Toast.makeText(AddIncomeActivity.this, "Failed", Toast.LENGTH_LONG).show();

                } catch(Exception e)
                {
                    Toast.makeText(AddIncomeActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void PickDate() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editText_date.setText(simpleDateFormat.format(calendar.getTime()));
                yy=i;
                mm=i1;
                dd=i2;
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText_money.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);
                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);
                    editText_money.setText(formattedString);
                    editText_money.setSelection(editText_money.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText_money.addTextChangedListener(this);
            }
        };
    }
}
