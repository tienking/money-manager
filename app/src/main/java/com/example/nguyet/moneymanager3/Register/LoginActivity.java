package com.example.nguyet.moneymanager3.Register;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyet.moneymanager3.*;
import com.example.nguyet.moneymanager3.FirstLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="LoginActivity";
    private FirebaseAuth mAuth;

    private EditText mEmail, mPassword;
    private Button btnLogin;
    private TextView createAccount;
    private CheckBox showPw;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        createAccount = (TextView) findViewById (R.id.createAccount);
        mEmail = (EditText)findViewById(R.id.login_email);
        mPassword =(EditText)findViewById(R.id.login_password);
        btnLogin = (Button) findViewById(R.id.loginBtn);
        showPw = (CheckBox) findViewById (R.id.show_hide_password);
        mPassword.setTransformationMethod(new PasswordTransformationMethod ());

        mAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Login();

            }
        });

        showPw.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    private void Login(){
        final String email= mEmail.getText().toString();
        final String password= mPassword.getText().toString();
        final Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
        final Intent intent2 = new Intent(LoginActivity.this, FirstLogin.class);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            myRef.child("vitien").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild("tienbandau"))
                                    {
                                        startActivity(intent1);
                                        finish();
                                    }
                                    else
                                    {
                                        startActivity(intent2);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println(databaseError.getMessage());
                                }
                            });
                            Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(LoginActivity.this,"Login Fail",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
