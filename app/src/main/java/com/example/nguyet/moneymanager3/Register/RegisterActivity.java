package com.example.nguyet.moneymanager3.Register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyet.moneymanager3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Nguyet on 9/21/2017.
 */

public class RegisterActivity  extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmail, mPassword;
    private Button btnSignUp;
    private TextView already_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText)findViewById(R.id.userEmailId);
        mPassword =(EditText)findViewById(R.id.password);
        btnSignUp  = (Button) findViewById(R.id.signUpBtn);
        already_user = (TextView) findViewById (R.id.already_user);

        already_user.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    private void Register() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete (@NonNull Task< AuthResult > task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"Register Success",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Register Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
