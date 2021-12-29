package com.example.cryptocoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    public EditText rFullName, rEmail, rPassword;
    public Button rRegister;
    FirebaseAuth fAuth;
    String userid;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        rFullName=findViewById(R.id.nameBox);
        rEmail=findViewById(R.id.emailBox);
        rPassword=findViewById(R.id.passwordBox);
        rRegister=findViewById(R.id.createNewBtn);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        rRegister.setOnClickListener(view -> {
            String email=rEmail.getText().toString().trim();
            String password=rPassword.getText().toString().trim();
            String name=rFullName.getText().toString();

            if(TextUtils.isEmpty(email)){
                rEmail.setError("Email is required!");
            }

            else if(TextUtils.isEmpty(password)){
                rPassword.setError("Password is required!");
            }

            else if(password.length()<8){
                rPassword.setError("Password Should have minimum 8 letter!");
            }
            else{

                //REGISTER USER FIREBASE

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userid= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                        DocumentReference documentReference=fStore.collection("users").document(userid);
                        Map<String,Object> user= new HashMap<>();
                        user.put("name",name);
                        user.put("email",email);
                        user.put("coins",",");
                        documentReference.set(user).addOnSuccessListener(aVoid -> Toast.makeText(this, "Welcome to CryptoCoin 🙏", Toast.LENGTH_SHORT).show());
                        DocumentReference documentReference2=fStore.collection("coins").document(userid);
                        Map<String,Object> blank= new HashMap<>();
                        documentReference2.set(blank).addOnSuccessListener(aVoid -> Log.d("as","success"));
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Some error occurred " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void login(View view) {
        startActivity(new Intent(this,Login.class));
    }
}