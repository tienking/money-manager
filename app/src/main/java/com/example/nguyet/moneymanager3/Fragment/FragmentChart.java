package com.example.nguyet.moneymanager3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nguyet.moneymanager3.R;
import com.example.nguyet.moneymanager3.ViewChart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Nguyet on 10/13/2017.
 */

public class FragmentChart extends Fragment {

    Spinner sp;
    Button bty;
    private View rootView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    ArrayList<String> arrayNam = new ArrayList<>();
    String nam;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        return rootView;
    }

    public void getListYear()
    {
        arrayNam.clear ();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("bieudo")) {
                    for (DataSnapshot data : dataSnapshot.child("bieudo").getChildren())
                    {
                        arrayNam.add(data.getKey().toString());
                    }
                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sp = (Spinner) rootView.findViewById(R.id.spinnerYear) ;
        bty = (Button) rootView.findViewById(R.id.btYear);
        getListYear();
        arrayNam.add("Chọn năm");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,arrayNam);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nam = sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getActivity(), ViewChart.class);
                    intent.putExtra("Year", nam);
                    getActivity().startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

