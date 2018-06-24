package com.example.nguyet.moneymanager3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nguyet.moneymanager3.Adapter.ListViewAdapter;
import com.example.nguyet.moneymanager3.Add_Ex_Income.AddExpenseActivity;
import com.example.nguyet.moneymanager3.Budget.Budget_List;
import com.example.nguyet.moneymanager3.R;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nguyet on 10/13/2017.
 */

public class FragmentExpense extends Fragment {

    private View rootView;
    Button btnAdd;
    Button bt;
    EditText ed;
    ListView listItem;
    ArrayList<Budget_List> manglist;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int month = 0;
    HashMap<String, Integer> hmc = new HashMap<String, Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(com.example.nguyet.moneymanager3.R.layout.fragment_expense, container, false);
        return rootView;
    }

    private void mapDanhMucChi() {
        hmc.put("Tiền điện", R.drawable.tiendien);
        hmc.put("Tiền nước", R.drawable.tiennuoc);
        hmc.put("Tiền cafe", R.drawable.coffee);
        hmc.put("Tiền ăn", R.drawable.tienan);
        hmc.put("Tiền đi chơi", R.drawable.tiendichoi);
        hmc.put("Tiền di chuyển", R.drawable.tiendichuyen);
        hmc.put("Tiền điện thoại", R.drawable.tiendienthoai);
        hmc.put("Tiền gas", R.drawable.tiengas);
        hmc.put("Tiền giải trí", R.drawable.tiengiaitri);
        hmc.put("Tiền học", R.drawable.tienhoc);
        hmc.put("Tiền mua sắm", R.drawable.tienmuasam);
        hmc.put("Tiền sách", R.drawable.tiensach);
        hmc.put("Tiền sửa chữa", R.drawable.tiensuachua);
        hmc.put("Tiền sức khoẻ", R.drawable.tiensuckhoe);
        hmc.put("Tiền thể thao", R.drawable.tienthethao);
        hmc.put("Tiền xăng", R.drawable.tienxang);
        hmc.put("Khác", R.drawable.khac2);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listItem = (ListView) rootView.findViewById(R.id.list_item);
        manglist = new ArrayList<Budget_List>();
        mapDanhMucChi();
        final ListViewAdapter adapter = new ListViewAdapter(getActivity(), R.layout.listview_layout, manglist);
        bt = (Button) rootView.findViewById(R.id.btnSearch);
        ed = (EditText) rootView.findViewById(R.id.edittext_search);
        listItem.setAdapter(adapter);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month = Integer.parseInt(ed.getText().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        manglist.clear();
                        for (DataSnapshot data : dataSnapshot.child("danhmucchi").getChildren()) {
                            String[] str = data.getKey().toString().split("-");
                            if (Integer.parseInt(str[1]) == month) {
                                for (DataSnapshot data2 : data.getChildren()) {
                                    if (hmc.containsKey(data2.getKey().toString())) {
                                        int value = Integer.parseInt(data2.child("sotien").getValue().toString());
                                        String key, note = "";
                                        key = data2.getKey().toString();
                                        if(data2.hasChild("note")) {
                                            note = "(" + data2.child("note").getValue().toString()+ ")";
                                        }
                                        manglist.add(new Budget_List(key + " " + note, value, data.getKey().toString(), hmc.get(key)));
                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                ed.getText().clear();
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                manglist.clear();
                for (DataSnapshot data : dataSnapshot.child("danhmucchi").getChildren()) {
                        for (DataSnapshot data2 : data.getChildren()) {
                            if (hmc.containsKey(data2.getKey().toString())) {
                                int value = Integer.parseInt(data2.child("sotien").getValue().toString());
                                String key, note = "";
                                key = data2.getKey().toString();
                                if(data2.hasChild("note")) {
                                    note = "(" + data2.child("note").getValue().toString()+ ")";
                                }
                                manglist.add(new Budget_List(key + "\t" + note, value, data.getKey().toString(), hmc.get(key)));
                            }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnAdd = (Button) rootView.findViewById(R.id.btnAddChi);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }
}



