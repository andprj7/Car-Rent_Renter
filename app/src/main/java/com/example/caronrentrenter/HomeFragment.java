package com.example.caronrentrenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.Adapter.ItemAdapter;
import com.example.caronrentrenter.Adapter.RenterAdapter;
import com.example.caronrentrenter.Car_Menu;
import com.example.caronrentrenter.Car_item_add;
import com.example.caronrentrenter.DataClass;
import com.example.caronrentrenter.Profile_Ui;
import com.example.caronrentrenter.R;
import com.example.caronrentrenter.ReadWriteUserDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ConstraintLayout cat_sports, cat_wedding, cat_tour, cat_all;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private String emailShare, name, mob;

    private ImageView imgProfile;
    private TextView txtName;

    private ItemAdapter adapter;
    private RenterAdapter adapter1;
    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Car").child("General");
    private final DatabaseReference databaseReference_High1 = FirebaseDatabase.getInstance().getReference("Renters");
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Renters");
    private DatabaseReference usersRef;

    private RecyclerView recyclerViewPopular, recyclerViewNew;

    private FloatingActionButton fab;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cat_sports = view.findViewById(R.id.cat_sports);
        cat_wedding = view.findViewById(R.id.cat_wedding);
        cat_tour = view.findViewById(R.id.cat_tour);
        cat_all = view.findViewById(R.id.cat_all);



        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, requireActivity().MODE_PRIVATE);


        emailShare = sharedPreferences.getString(KEY_NAME, null);
        imgProfile = view.findViewById(R.id.imagProfile);
        txtName = view.findViewById(R.id.txtName);
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

                    Glide.with(HomeFragment.this).load(imageUrl).into(imgProfile);

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


        imgProfile = view.findViewById(R.id.imagProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Profile_Ui.class);
                startActivity(intent);
            }
        });



        recyclerViewPopular = view.findViewById(R.id.viewPopular);
        recyclerViewNew = view.findViewById(R.id.viewNew);
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewNew.setHasFixedSize(true);
        recyclerViewNew.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));


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
                adapter = new ItemAdapter( getContext(),dataList);
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
//                adapter = new ItemAdapter(HomeFragment.this, dataList);
                adapter1 = new RenterAdapter( getContext(),dataList1);
//                recyclerViewPopular.setAdapter(adapter);
                recyclerViewNew.setAdapter(adapter1);


//                recyclerViewNew.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_item_add.class);
                startActivity(intent);

            }
        });



        cat_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Sports";
                intent.putExtra("car",path);
                startActivity(intent);
            }
        });
        cat_wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Wedding";
                intent.putExtra("car",path);
                startActivity(intent);
            }
        });

        cat_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Tour";
                intent.putExtra("car",path);
                startActivity(intent);
            }
        });

        cat_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "General";
                intent.putExtra("car",path);
                startActivity(intent);
            }
        });


        return view;
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