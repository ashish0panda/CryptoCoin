package com.example.cryptocoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    public TextView name;
    String userId;
    DocumentReference documentReference;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        name=findViewById(R.id.name);

        //show_name
        userId= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (documentSnapshot, e) -> {
            assert documentSnapshot != null;
            if (documentSnapshot.exists()) {
                name.setText(documentSnapshot.getString("name"));

            } else {
                Log.d("tag", "onEvent: Document do not exists");
            }
        });



        //navigation
        BottomNavigationView btn=findViewById(R.id.tab);
        btn.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()){
                case R.id.all:
                    fm.replace(R.id.container,new ListFragment());
                    fm.commit();
                    break;
                case R.id.fav:
                    fm.replace(R.id.container,new FavFragment());
                    fm.commit();

            }
            return true;
        });


        if (savedInstanceState == null) {
            btn.setSelectedItemId(R.id.all);
        }
    }

    public void logout(View view) {
        documentReference=null;
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, Login.class));
        finish();
    }
}