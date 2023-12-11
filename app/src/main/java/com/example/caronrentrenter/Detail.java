package com.example.caronrentrenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.caronrentrenter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Detail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageSlider imageSlider = findViewById(R.id.imageslider);
        final List<SlideModel> remoteimage = new ArrayList<>();

//        FirebaseDatabase.getInstance().getReference().child("Car")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot data : snapshot.getChildren())
//                            remoteimage.add(new SlideModel(data.child("imageURL").getValue().toString(),ScaleTypes.FIT));
//
//
//                        imageSlider.setImageList(remoteimage, ScaleTypes.FIT);
//
//                        imageSlider.setItemClickListener(new ItemClickListener() {
//                            @Override
//                            public void onItemSelected(int i) {
//                                Toast.makeText(getApplicationContext(),remoteimage.get(i).toString(),Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.bg, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.bmw, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.skoda, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.lambo, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);


    }
}