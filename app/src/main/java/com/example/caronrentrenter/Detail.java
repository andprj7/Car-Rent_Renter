package com.example.caronrentrenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.Adapter.CarDetailAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";

    String emailShare,name;

//    private DetailClass object;
        private DataClass object;
    private DatabaseReference usersRef,usersRef1,usersRef2;

    String rmob,mobiii,mob1, imageUrl;
    private ReadWriteUserDetails object1;
    TextView titleTxt,txtRating,txtPassengers,txtGear,txtMaxSpeed;
    TextView txtRname,txtMob;
    String mob;
    String firebaseImageUrl;
    ImageView img,img_renter,img_favourite;
    String ddl1;


    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Renters");


    private CarDetailAdapter adapter;
    final private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Car").child("General").child("Company");
//    final private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Renters");
    private RecyclerView recyclerViewPopular, recyclerViewNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        titleTxt = findViewById(R.id.txtcname);
        txtRating = findViewById(R.id.txtrating);
        txtPassengers = findViewById(R.id.txtPassengers);
        txtGear = findViewById(R.id.txtgear);
        txtMaxSpeed = findViewById(R.id.txtMaxSpeed);
        img = findViewById(R.id.img);
        img_renter = findViewById(R.id.img_renter);
        img_favourite = findViewById(R.id.img_favourite);


        txtRname = findViewById(R.id.txtrname);
        txtMob = findViewById(R.id.txtMob);




        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


        emailShare = sharedPreferences.getString(KEY_NAME, null);

//        imgProfile = findViewById(R.id.imagProfile);
//        txtName = findViewById(R.id.txtName);
//        txtName.setText(emailShare);
        String desiredUsername = emailShare;
        usersRef1 = FirebaseDatabase.getInstance().getReference("Renters");

        // Retrieve the user's image URL
        usersRef1.orderByChild("email").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
//                    Glide.with(Detail_Of_Car.this).load(imageUrl).into(img_renter);

                    mob1 = userSnapshot.child("mobile").getValue(String.class);
                }
//                txtRname.setText(name);
//                txtMob.setText(rmob);

                mobiii = mob1;
//                Toast.makeText(Detail_Of_Car.this, mobiii, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });



        getBundle();


//        object = (DetailClass) getIntent().getSerializableExtra("object1");
        object = (DataClass) getIntent().getSerializableExtra("object1");
//        object = (ReadWriteUserDetails) getIntent().getSerializableExtra("object1");


         firebaseImageUrl = object.getImageURL();

        Glide.with(this)
                .load(firebaseImageUrl)
                .into(img);

        titleTxt.setText(object.getModelName());
        txtRating.setText(object.getCarRating());
        txtPassengers.setText(object.getNumberPassengers().toString() + " Passengers");
        txtGear.setText(object.getGearMode().toString());
        txtMaxSpeed.setText(object.getMaximumSpeed());

        txtRname.setText(object.getRenterMobile());

         mob = object.getRenterMobile();


        usersRef = FirebaseDatabase.getInstance().getReference("Renters");

        // Retrieve the user's image URL
        usersRef.orderByChild("mobile").equalTo(mob).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    Glide.with(Detail.this).load(imageUrl).into(img_renter);

                    name = userSnapshot.child("name").getValue(String.class);
                    rmob = userSnapshot.child("mobile").getValue(String.class);


//                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }


                txtRname.setText(name);
                txtMob.setText(rmob);
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





        img_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usersRef1 = FirebaseDatabase.getInstance().getReference("Renters");


                    String dd = String.valueOf(databaseReference.child(mob).child("Favorite").child("name"));
                // Retrieve the user's image URL
                usersRef1.orderByChild("mobile").equalTo(mobiii).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            ddl1 = userSnapshot.child("Favorite").child("name").getValue(String.class);
//                             imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
//                             imageUrl = userSnapshot.child("imageURL").getValue(String.class);

//                            Toast.makeText(Detail.this, ddl1, Toast.LENGTH_SHORT).show();


//
//                            databaseReference.child(mob).child("Favorite").child(object.getModelName()).child("ImageUrlDemo").setValue(firebaseImageUrl.toString());
//                            databaseReference.child(mob).child("Favorite").child(object.getModelName()).child("Name").setValue(object.getModelName().toString());
//                            databaseReference.child(mob).child("Favorite").child(object.getModelName()).child("Rent").setValue(object.getRentPerDay().toString());
//                            databaseReference.child(mob).child("Favorite").child(object.getModelName()).child("Rate").setValue(object.getCarRating().toString());
//                            databaseReference.child(mob).child("Favorite").child(object.getModelName()).child("TopSpeed").setValue(object.getMaximumSpeed().toString());

                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("imageURL").setValue(firebaseImageUrl.toString());
                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("modelName").setValue(object.getModelName().toString());
                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("rentPerDay").setValue(object.getRentPerDay().toString());
                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("carRating").setValue(object.getCarRating().toString());
                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("maximumSpeed").setValue(object.getMaximumSpeed().toString());

                            startActivity(new Intent(Detail.this,Favorite.class));
                            // This is for remove data in firebase
//                            databaseReference.child(mob).child("Favorite").child(object.getModelName()).removeValue();






                            //           }


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle the error, if any.
                    }
                });

            }
        });





    }








    private void getBundle() {

//





    }



}