package com.example.caronrentrenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.Adapter.ItemAdapter;
import com.example.caronrentrenter.Adapter.RenterAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ConstraintLayout cat_sports,cat_wedding,cat_tour,cat_all;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";

    String emailShare,name,mob;


    ImageView imgProfile;
    TextView txtName;


    private ItemAdapter adapter;
    private RenterAdapter adapter1;
    //    final private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Car").child("General").child("Company").child("Audi");
    final private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Car").child("General");
    final private DatabaseReference databaseReference_High1 = FirebaseDatabase.getInstance().getReference("Renters");


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


        cat_sports = findViewById(R.id.cat_sports);
        cat_wedding = findViewById(R.id.cat_wedding);
        cat_tour = findViewById(R.id.cat_tour);
        cat_all = findViewById(R.id.cat_all);



        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


        emailShare = sharedPreferences.getString(KEY_NAME, null);
        imgProfile = findViewById(R.id.imagProfile);
        txtName = findViewById(R.id.txtName);
//        txtName.setText(emailShare);


//        editEmail.setText(demo.toString());


        String desiredUsername = emailShare;

        usersRef = FirebaseDatabase.getInstance().getReference("Renters");

        // Retrieve the user's image URL
        usersRef.orderByChild("email").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    Glide.with(MainActivity.this).load(imageUrl).into(imgProfile);

                    name = userSnapshot.child("name").getValue(String.class);
//                    email = userSnapshot.child("email").getValue(String.class);
//                    pass = userSnapshot.child("pass").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
//                    city = userSnapshot.child("city").getValue(String.class);
//                    driving = userSnapshot.child("dll").getValue(String.class);
//                    gender = userSnapshot.child("gender").getValue(String.class);


//                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }


                txtName.setText(name);
//                editPassword.setText(pass);
//                editMobile.setText(mob);
//                editCity.setText(city);
//                editDriving.setText(driving);
//                editGender.setText(gender);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        imgProfile = findViewById(R.id.imagProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile_Ui.class);
                startActivity(intent);
            }
        });



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

                ArrayList<DataClass> dataList = new ArrayList<>();
                //for each lagavvu
                // snapnot.getChildern()


                snapshot.getChildren();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3: dataSnapshot2.getChildren()) {
                                DataClass dataClass = dataSnapshot3.getValue(DataClass.class);
                                dataList.add(dataClass);
                            }
                        }
                    }
                }
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                        DataClass dataClass = dataSnapshot.getValue(DataClass.class);
//                        dataList.add(dataClass);
//
//
//                }
                adapter = new ItemAdapter(MainActivity.this, dataList);
//                recyclerViewPopular.setAdapter(adapter);
//                recyclerViewNew.setAdapter(adapter);


                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        databaseReference_High1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                ArrayList<DataClass> dataList = new ArrayList<>();
                ArrayList<ReadWriteUserDetails> dataList1 = new ArrayList<>();
                //for each lagavvu
                // snapnot.getChildern()


//                snapshot.getChildren();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(!dataSnapshot.getKey().toString().equals(mob)){
                        ReadWriteUserDetails dataClass = dataSnapshot.getValue(ReadWriteUserDetails.class);
                        databaseReference_High1.child(dataSnapshot.getKey().toString()).setValue(dataClass);
                        dataList1.add(dataClass);
                    }
//                    databaseReference_High1.child(dataSnapshot.getKey().toString()).child("city").setValue("okok");

                }

//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
////                        DataClass dataClass = dataSnapshot1.getValue(DataClass.class);
////                        dataList.add(dataClass);
//                        ReadWriteUserDetails dataClass = dataSnapshot1.getValue(ReadWriteUserDetails.class);
//                        dataList1.add(dataClass);
//                    }
//
//                }
//                adapter = new ItemAdapter(MainActivity.this, dataList);
                adapter1 = new RenterAdapter(MainActivity.this, dataList1);
//                recyclerViewPopular.setAdapter(adapter);
                recyclerViewNew.setAdapter(adapter1);


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



        cat_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Car_Menu.class);
                String path = "Sports";
                intent.putExtra("car",path);
                startActivity(intent);
            }
        });
        cat_wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Car_Menu.class);
                String path = "Wedding";
                intent.putExtra("car",path);
                startActivity(intent);
            }
        });

        cat_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Car_Menu.class);
                String path = "Tour";
                intent.putExtra("car",path);
                startActivity(intent);
            }
        });

        cat_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Car_Menu.class);
                String path = "General";
                intent.putExtra("car",path);
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