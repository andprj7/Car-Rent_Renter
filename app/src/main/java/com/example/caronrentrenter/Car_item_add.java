package com.example.caronrentrenter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class Car_item_add extends AppCompatActivity {

    Spinner spinner,spinner1;

    List<String> items;
    String item;
  List<String> items1;
    String item1;

    RadioGroup radioGroup;
    RadioButton rdb_mannual, rdb_auto;

    private FloatingActionButton uploadButton;
    private ImageView uploadImage;
    EditText modelName, modelDescription, rentPerDay, maximumSpeed, Fuel, carRating, numberPassengers;
    ProgressBar progressBar;
    private Uri imageUri;
    DatabaseReference databaseReference;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_item_add);

        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner1);

        items = new ArrayList<>();
        items.add(0, "Car Category");
        items.add("Sports");
        items.add("Wedding");
        items.add("Tour");
        items.add("Off roding");
        items.add("Transport");
        items.add("Luxuries");
        items.add("General");
        spinner.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //1st Method
                //item = items.get(position);

                //2nd Method
                if (!(spinner.getSelectedItem().toString() == "Car Category")) {
                    item = spinner.getSelectedItem().toString();
                    Toast.makeText(Car_item_add.this, item, Toast.LENGTH_SHORT).show();

                } else {
//                    valid();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        items1 = new ArrayList<>();
        items1.add(0, "Car Company Category");
        items1.add("Tata");
        items1.add("Mahindra");
        items1.add("Maruti");
        items1.add("Lamborghini");
        items1.add("Audi");
        items1.add("Bmw");
        items1.add("Skoda");
        items1.add("Others");

        spinner1.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items1));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //1st Method
                //item = items.get(position);

                //2nd Method
                if (!(spinner1.getSelectedItem().toString() == "Car Company Category")) {
                    item1 = spinner1.getSelectedItem().toString();
                    Toast.makeText(Car_item_add.this, item1, Toast.LENGTH_SHORT).show();

                } else {
//                    valid();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadButton = findViewById(R.id.uploadButton);
        modelName = findViewById(R.id.modelName);
        modelDescription = findViewById(R.id.modelDescription);

        rentPerDay = findViewById(R.id.rentPerDay);
        maximumSpeed = findViewById(R.id.maximumSpeed);
        Fuel = findViewById(R.id.Fuel);
        carRating = findViewById(R.id.carRating);

        radioGroup = findViewById(R.id.radioGroup1);
        rdb_mannual = findViewById(R.id.rdb_mannual);
        rdb_auto = findViewById(R.id.rdb_auto);

        numberPassengers = findViewById(R.id.numberPassengers);

        uploadImage = findViewById(R.id.uploadImage);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);

                if (radioButton != null) {
                    selectedOption = radioButton.getText().toString();
                    Toast.makeText(Car_item_add.this, "Selected Gear Mode: " + selectedOption, Toast.LENGTH_SHORT).show();
                }
            }
        });




        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            uploadImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(Car_item_add.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    uploadToFirebase(imageUri);
                } else {
                    Toast.makeText(Car_item_add.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
                if (!valid()) {
                    valid();
                }
            }
        });
    }
    //Outside onCreate

    private void uploadToFirebase(Uri imageUri) {


            String Model = modelName.getText().toString();
            String Description = modelDescription.getText().toString();
            //String Rent = rentPerDay.getText().toString();
            String Rent = rentPerDay.getText().toString();
//        Double Rent = Double.valueOf(rentPerDay.getText().toString());
            String MaxSpeed = maximumSpeed.getText().toString();
            String FuelStatus = Fuel.getText().toString();
            String Rate = carRating.getText().toString();
            String Passengers = numberPassengers.getText().toString();
            String gearMode = selectedOption;
            String CarCompany = spinner1.getSelectedItem().toString();
            String CarType = spinner.getSelectedItem().toString();

//        Double Rate = Double.valueOf(carRating.getText().toString());

            StorageReference imageReference = storageReference.child("general/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(Model.toString(), uri.toString(), Description.toString(), Rent.toString(), MaxSpeed.toString(), FuelStatus.toString(), Rate.toString(), Passengers.toString(), gearMode.toString(), CarCompany.toString());
                            databaseReference = FirebaseDatabase.getInstance().getReference("Car").child("General").child("Company").child(CarCompany.toString());
                            String key = databaseReference.push().getKey();
                            databaseReference.child(Model).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Car_item_add.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Car_item_add.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Car_item_add.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });


//            StorageReference imageReference2 = storageReference.child("sports/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));
            StorageReference imageReference2 = storageReference.child(CarType.toString() + "/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference2.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(Model.toString(), uri.toString(), Description.toString(), Rent.toString(), MaxSpeed.toString(), FuelStatus.toString(), Rate.toString(), Passengers.toString(), gearMode.toString(), CarCompany.toString());
                            databaseReference = FirebaseDatabase.getInstance().getReference("Car").child(CarType.toString()).child("Company").child(CarCompany.toString());

                            String key = databaseReference.push().getKey();
                            databaseReference.child(Model).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Car_item_add.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Car_item_add.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Car_item_add.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });


    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    boolean check;

    boolean valid() {
        String Model = modelName.getText().toString();
        String Description = modelDescription.getText().toString();
        //String Rent = rentPerDay.getText().toString();
        String Rent = rentPerDay.getText().toString();
//        Double Rent = Double.valueOf(rentPerDay.getText().toString());
        String MaxSpeed = maximumSpeed.getText().toString();
        String FuelStatus = Fuel.getText().toString();
        String Rate = carRating.getText().toString();
//        String CarCompany = spinner.getSelectedItem().toString();


        String Passengers = numberPassengers.getText().toString();


        if (Model.isEmpty()) {
            modelName.setError("Enter a Model name");
            check = false;
        } else {
            check = true;
        }
        if (Description.isEmpty()) {
            modelDescription.setError("Enter a Description");
            check = false;
        } else {
            check = true;
        }
        if (Rent.isEmpty()) {
            rentPerDay.setError("Enter a Rent per day");
            check = false;
        } else {
            check = true;
        }
        if (MaxSpeed.isEmpty()) {
            maximumSpeed.setError("Enter a Maximum Speed");
            check = false;
        } else {
            check = true;
        }
        if (FuelStatus.isEmpty()) {
            Fuel.setError("Enter a Fuel");
            check = false;
        } else {
            check = true;
        }
        if (Rate.isEmpty()) {
            carRating.setError("Enter a Rating");
            check = false;
        } else {
            check = true;
        }
        if (Passengers.isEmpty()) {
            numberPassengers.setError("Enter Passengers");
            check = false;
        } else {
            check = true;
        }
        if (spinner.getSelectedItem().toString() == "Car Category") {
            Toast.makeText(Car_item_add.this,"Please select Category", Toast.LENGTH_SHORT).show();
            check=false;
        }
        else {
            check=true;
        }
        if (spinner.getSelectedItem().toString() == "Car Company Category") {
            Toast.makeText(Car_item_add.this,"Please select Company Category", Toast.LENGTH_SHORT).show();
            check=false;
        }
        else {
            check=true;
        }

        return check;
    }
}

