package com.example.caronrentrenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_Ui extends AppCompatActivity {

    Button editButton;
    LinearLayout settingCall;
    ImageView img_profile_user;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    TextView txtName,txtEmailUser,txtPassWord,txtMo,txtGend,txtCit,txtDll;
    String emailShare,name,email,pass,mob,gender,city,dl;

    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ui);

        editButton = findViewById(R.id.editButton);
        settingCall = findViewById(R.id.settingCall);
        img_profile_user = findViewById(R.id.img_profile_user);
        txtName = findViewById(R.id.txtUname);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        txtEmailUser = findViewById(R.id.txtEmailUser);
        txtPassWord = findViewById(R.id.txtPassWord);
        txtMo = findViewById(R.id.txtMo);
        txtGend = findViewById(R.id.txtGend);
        txtCit = findViewById(R.id.txtCit);
        txtDll = findViewById(R.id.txtDll);

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

                    Glide.with(Profile_Ui.this).load(imageUrl).into(img_profile_user);

                    name = userSnapshot.child("name").getValue(String.class);
                    email = userSnapshot.child("email").getValue(String.class);

                    pass = userSnapshot.child("pass").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
                    city = userSnapshot.child("city").getValue(String.class);
                    dl = userSnapshot.child("dll").getValue(String.class);
                    gender = userSnapshot.child("gender").getValue(String.class);


//                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }


                txtName.setText(name);
                txtEmailUser.setText(email);
                txtPassWord.setText(pass);
                txtMo.setText(mob);
                txtGend.setText(gender);
                txtCit.setText(city);
                txtDll.setText(dl);
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


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_Ui.this,EditProfile.class));
            }
        });


        settingCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_Ui.this, Settings.class));
            }
        });

    }
}