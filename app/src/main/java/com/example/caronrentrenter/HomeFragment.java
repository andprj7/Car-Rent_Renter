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
import com.facebook.shimmer.ShimmerFrameLayout;
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
    private DatabaseReference usersRef;

    private RecyclerView recyclerViewPopular, recyclerViewNew;

    private FloatingActionButton fab;
    private ShimmerFrameLayout shimmerFrameLayout,shimmerFrameLayout1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cat_sports = view.findViewById(R.id.cat_sports);
        cat_wedding = view.findViewById(R.id.cat_wedding);
        cat_tour = view.findViewById(R.id.cat_tour);
        cat_all = view.findViewById(R.id.cat_all);

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, requireActivity().MODE_PRIVATE);

        emailShare = sharedPreferences.getString(KEY_NAME, null);
        imgProfile = view.findViewById(R.id.imagProfile);
        txtName = view.findViewById(R.id.txtName);

        String desiredUsername = emailShare;

        usersRef = FirebaseDatabase.getInstance().getReference("Renters");

        usersRef.orderByChild("email").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    Glide.with(HomeFragment.this).load(imageUrl).into(imgProfile);

                    name = userSnapshot.child("name").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
                }

                txtName.setText(name);
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

        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        shimmerFrameLayout1 = view.findViewById(R.id.shimmer_1);

        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DataClass> dataList = new ArrayList<>();

                shimmerFrameLayout.startShimmer();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                DataClass dataClass = dataSnapshot3.getValue(DataClass.class);
                                dataList.add(dataClass);
                            }
                        }
                    }
                }

                adapter = new ItemAdapter(getContext(), dataList);
                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerViewPopular.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        databaseReference_High1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ReadWriteUserDetails> dataList1 = new ArrayList<>();

                shimmerFrameLayout1.startShimmer();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (!dataSnapshot.getKey().equals(mob)) {
                        ReadWriteUserDetails dataClass = dataSnapshot.getValue(ReadWriteUserDetails.class);
                        databaseReference_High1.child(dataSnapshot.getKey()).setValue(dataClass);
                        dataList1.add(dataClass);
                    }
                }

                adapter1 = new RenterAdapter(getContext(), dataList1);
                recyclerViewNew.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();

                shimmerFrameLayout1.stopShimmer();
                shimmerFrameLayout1.setVisibility(View.GONE);
                recyclerViewNew.setVisibility(View.VISIBLE);
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
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });

        cat_wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Wedding";
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });

        cat_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Tour";
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });

        cat_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "General";
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });

        return view;
    }
}
