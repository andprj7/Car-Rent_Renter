package com.example.caronrentrenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    Button editButton;
    LinearLayout settingCall;
    ImageView img_profile_user;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    TextView txtName, txtEmailUser, txtPassWord, txtMo, txtGend, txtCit, txtDll;
    String emailShare, name, email, pass, mob, gender, city, dl;

    private DatabaseReference usersRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editButton = view.findViewById(R.id.editButton);
        settingCall = view.findViewById(R.id.settingCall);
        img_profile_user = view.findViewById(R.id.img_profile_user);
        txtName = view.findViewById(R.id.txtUname);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, requireActivity().MODE_PRIVATE);
        txtEmailUser = view.findViewById(R.id.txtEmailUser);
        txtPassWord = view.findViewById(R.id.txtPassWord);
        txtMo = view.findViewById(R.id.txtMo);
        txtGend = view.findViewById(R.id.txtGend);
        txtCit = view.findViewById(R.id.txtCit);
        txtDll = view.findViewById(R.id.txtDll);

        emailShare = sharedPreferences.getString(KEY_NAME, null);

        String demo = emailShare;

        String desiredUsername = demo;

        usersRef = FirebaseDatabase.getInstance().getReference("Renters");

        // Retrieve the user's image URL
        usersRef.orderByChild("email").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    if (imageUrl != null) {
                        Glide.with(requireActivity()).load(imageUrl).into(img_profile_user);
                    }

                    name = userSnapshot.child("name").getValue(String.class);
                    email = userSnapshot.child("email").getValue(String.class);

                    pass = userSnapshot.child("pass").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
                    city = userSnapshot.child("city").getValue(String.class);
                    dl = userSnapshot.child("dll").getValue(String.class);
                    gender = userSnapshot.child("gender").getValue(String.class);
                }

                txtName.setText(name);
                txtEmailUser.setText(email);
                txtPassWord.setText(pass);
                txtMo.setText(mob);
                txtGend.setText(gender);
                txtCit.setText(city);
                txtDll.setText(dl);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), EditProfile.class));
            }
        });

        settingCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), Settings.class));
            }
        });
    }
}
