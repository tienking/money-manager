package com.example.nguyet.moneymanager3;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * Created by tk04w on 11/30/2017.
 */

public class FirstLogin extends AppCompatActivity{

    TextView tv;
    EditText ed;
    Button bt;
    ImageView im1,im2,im3,im4;
    ScrollView sv;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login);
        tv = (TextView) findViewById(R.id.textViewHD);
        ed = (EditText) findViewById(R.id.editFirstAdd);
        bt= (Button) findViewById(R.id.btnFirstAdd);
        sv = (ScrollView) findViewById(R.id.ScrollView);
        im1 = (ImageView) findViewById(R.id.imageView1);
        im2 = (ImageView) findViewById(R.id.imageView2);
        im3 = (ImageView) findViewById(R.id.imageView3);
        im4 = (ImageView) findViewById(R.id.imageView4);
        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(800);
        imagePopup.setWindowWidth(800);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setFullScreen(true);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(im1.getDrawable());
                imagePopup.viewPopup();

            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(im2.getDrawable());
                imagePopup.viewPopup();

            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(im3.getDrawable());
                imagePopup.viewPopup();

            }
        });
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(im4.getDrawable());
                imagePopup.viewPopup();

            }
        });
        setUserManual ();
        ed.addTextChangedListener(onTextChangedListener());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String str[] = ed.getText().toString().split(",");
                    String tmp = str[0];
                    for(int i =1;i< str.length;i++)
                        tmp= tmp + str[i];
                    int money = Integer.parseInt(tmp);

                    myRef.child("vitien").child("tienbandau").setValue(money);
                    Intent intent = new Intent(FirstLogin.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e)
                {
                    Toast.makeText(FirstLogin.this, "Hãy nhập số tiền", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setUserManual()
    {
        String huongdan= "\nĐây là phần mềm giúp bạn quản lí ví tiền của mình một cách hiệu quả.\n" +
                "- Đầu tiên hãy nhập vào số tiền hiện tại của bạn.\n" +
                "- Sau đó bạn sẽ đến với giao diện quản lí thu chi.\n" +
                "- Nhấp vào mục EXPENSE để xem và thêm thông tin Chi hàng ngày\n" +
                "- Nhấp vào mục INCOME để xem và thêm thông tin Thu hàng ngày\n" +
                "- Nhấp vào mục CHART và chọn năm để xem biểu đồ thống kê thu chi theo từng tháng trong năm.\n";
        tv.setText(huongdan);
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
                ed.removeTextChangedListener(this);

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
                    ed.setText(formattedString);
                    ed.setSelection(ed.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                ed.addTextChangedListener(this);
            }
        };
    }


}



