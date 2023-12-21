package com.example.caronrentrenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;

import com.example.caronrentrenter.Adapter.MenuAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Car_Menu extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<DataClass> dataList;
    TextView txtdrj;
    private MenuAdapter adapter;
    String type;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_menu);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new MenuAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        searchView=findViewById(R.id.search);
        searchView.clearFocus();
        Intent intent = getIntent();

        type = intent.getStringExtra("car");



        if(type.toString().equals("General")){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Car").child("General");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()) {
                                for (DataSnapshot dataSnapshot3: dataSnapshot2.getChildren()) {

                                        DataClass dataClass = dataSnapshot3.getValue(DataClass.class);
                                        dataList.add(dataClass);




                                }


                            }

                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Car").child(type);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()) {
                                DataClass dataClass = dataSnapshot2.getValue(DataClass.class);
                                dataList.add(dataClass);
                            }

                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }
    public void searchList(String text){
        ArrayList<DataClass> searchList=new ArrayList<>();
        for (DataClass dataClass:dataList){
            if(dataClass.getModelName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }
}