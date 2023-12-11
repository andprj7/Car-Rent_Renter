package com.example.caronrentrenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrentrenter.Adapter.ItemAdapter;
import com.example.caronrentrenter.Domain.ItemDomain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ItemAdapter adapter;
//    final private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Car").child("General").child("Company").child("Audi");
    final private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Car").child("General").child("Company");


    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Renters");

    private DatabaseReference usersRef;


    private RecyclerView.Adapter adapterPopular, adapterNew;
    private RecyclerView recyclerViewPopular, recyclerViewNew;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initRecyclerView();


        usersRef = FirebaseDatabase.getInstance().getReference("users");

        recyclerViewPopular = findViewById(R.id.viewPopular);
        recyclerViewNew = findViewById(R.id.viewNew);
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewNew.setHasFixedSize(true);
        recyclerViewNew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<DataClass> dataList=new ArrayList<>();
                //for each lagavvu
                // snapnot.getChildern()


                snapshot.getChildren();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        DataClass dataClass = dataSnapshot1.getValue(DataClass.class);
                        dataList.add(dataClass);
                    }

                }
                adapter = new ItemAdapter(MainActivity.this, dataList);
                recyclerViewPopular.setAdapter(adapter);
                recyclerViewNew.setAdapter(adapter);






//                recyclerViewNew.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Car_item_add.class);
                startActivity(intent);

            }
        });








    }







//
//
//    private void initRecyclerView() {
//        ArrayList<ItemDomain> ItemArrayList = new ArrayList<>();
//
//        ItemArrayList.add(new ItemDomain("DMD", "Opera ",
//                "Devang Sarvaiya \n" + "Meet Ramani \n" + "Deep Moradiya",
//                2, 1, 100000000, "all"));
//
//        ItemArrayList.add(new ItemDomain("DMD", "Opera",
//                "Devang Sarvaiya \n" + "Meet Ramani \n" + "Deep Moradiya",
//                3, 2, 200000000, "bg"));
//
//        ItemArrayList.add(new ItemDomain("DMD", "Opera ",
//                "Devang Sarvaiya \n" + "Meet Ramani \n" + "Deep Moradiya",
//                4, 3, 300000000, "start"));
//
//        ItemArrayList.add(new ItemDomain("DMD", "Opera ",
//                "Devang Sarvaiya \n" + "Meet Ramani \n" + "Deep Moradiya",
//                5, 4, 400000000, "user1"));
//
//        ItemArrayList.add(new ItemDomain("DMD", "Opera ",
//                "Devang Sarvaiya \n" + "Meet Ramani \n" + "Deep Moradiya",
//                6, 5, 500000000, "bg"));
//
//        ItemArrayList.add(new ItemDomain("DMD", "Opera ",
//                "Devang Sarvaiya \n" + "Meet Ramani \n" + "Deep Moradiya",
//                7, 6, 600000000, "img"));
//
//
//        recyclerViewPopular = findViewById(R.id.viewPopular);
//        recyclerViewNew = findViewById(R.id.viewNew);
//
//        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewNew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
////        adapterNew = new ItemAdapter(ItemArrayList);
////        adapterPopular = new ItemAdapter(ItemArrayList);
//
//        recyclerViewNew.setAdapter(adapterNew);
//        recyclerViewPopular.setAdapter(adapterPopular);
//
//
//
//
//    }
}