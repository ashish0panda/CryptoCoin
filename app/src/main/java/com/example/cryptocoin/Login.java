package com.example.cryptocoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText lEmail,lPassword;
    Button lLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        fAuth=FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        lEmail=findViewById(R.id.emailBox);
        lPassword=findViewById(R.id.passwordBox);
        lLogin=findViewById(R.id.submitBtn);
        lLogin.setOnClickListener(view -> {
            String email=lEmail.getText().toString().trim();
            String password=lPassword.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                lEmail.setError("Email is required!");
            }

            else if(TextUtils.isEmpty(password)){
                lPassword.setError("Password is required!");
            }
            else{
                //Authenticate

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this, "Welcome back to CryptoCoin 🙏", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(this, "Some error occurred " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void signup(View view) {
        startActivity(new Intent(this,SignUp.class));
    }


}