package com.example.caronrentrenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

//    private DetailClass object;
    private DataClass object;
//    private ReadWriteUserDetails object;
    TextView titleTxt,txtRating,txtPassengers,txtGear,txtMaxSpeed;
    ImageView img;


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

        getBundle();

//        ImageSlider imageSlider = findViewById(R.id.imageslider);
//
//
//
//        ArrayList<SlideModel> slideModels=new ArrayList<>();
//        slideModels.add(new SlideModel(R.drawable.bg, ScaleTypes.FIT));
//        slideModels.add(new SlideModel(R.drawable.bmw, ScaleTypes.FIT));
//        slideModels.add(new SlideModel(R.drawable.skoda, ScaleTypes.FIT));
//        slideModels.add(new SlideModel(R.drawable.lambo, ScaleTypes.FIT));
//
//        imageSlider.setImageList(slideModels,ScaleTypes.FIT);


//        recyclerViewPopular = findViewById(R.id.viewPopular);
////        recyclerViewNew = findViewById(R.id.viewNew);
//        recyclerViewPopular.setHasFixedSize(true);
//        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


//        databaseReference_High.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                ArrayList<DetailClass> dataList = new ArrayList<>();
//                //for each lagavvu
//                // snapnot.getChildern()
//
//
//                snapshot.getChildren();
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        DetailClass dataClass = dataSnapshot1.getValue(DetailClass.class);
//                        dataList.add(dataClass);
//                    }
//
//                }
//                adapter = new CarDetailAdapter(Detail.this, dataList);
////                recyclerViewPopular.setAdapter(adapter);
////                recyclerViewNew.setAdapter(adapter);
//
//
//                recyclerViewPopular.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//


    }




    private void getBundle() {




//        object = (DetailClass) getIntent().getSerializableExtra("object1");
        object = (DataClass) getIntent().getSerializableExtra("object1");
//        object = (ReadWriteUserDetails) getIntent().getSerializableExtra("object1");


        String firebaseImageUrl = object.getImageURL();

        Glide.with(this)
                .load(firebaseImageUrl)
                .into(img);

        if (object != null) {
                String modelName = object.getModelName();
            String rate = object.getCarRating();

            if (modelName != null) {
                titleTxt.setText(modelName);
                txtRating.setText(rate);
                txtPassengers.setText(object.getNumberPassengers().toString() + " Passengers");
                txtGear.setText(object.getGearMode().toString());
                txtMaxSpeed.setText(object.getMaximumSpeed());
            } else {
                Toast.makeText(this, "Model name is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Object is null", Toast.LENGTH_SHORT).show();
        }
    }


}