package com.example.caronrentrenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    static final String TAG = "SignUp";
    Spinner spinner;
    TextInputEditText txtname, txtmobile, txtemail, txtpass, txtconpass, txtdl;
    //    RadioGroup radiogender;
//    RadioButton male, female, other;
    Button btnregister;
    String[] city = {"Surat", "Vadodara", "Ahmedabad", "Rajkot", "Bhavnagar"};

    private ImageButton uploadUserDl, Profilepic;
    private ProgressBar progressBar;
    //    private FirebaseAuth authProfile;
//    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private static final int PIC_IMAGE_REQUEST = 1;
    private FirebaseAuth Fauth;
    private Uri imageUri, imageUri1;
    //final  private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Renters");
    RadioGroup radioGroup;
    String selectedOption;
    RadioButton rdb_male, rdb_female, rdb_others;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //mine

        Toast.makeText(this, "You can register now", Toast.LENGTH_LONG).show();
        txtname = findViewById(R.id.txtname);
        txtmobile = findViewById(R.id.txtmobile);
        txtemail = findViewById(R.id.txtemail);
        txtpass = findViewById(R.id.txtpass);
        txtconpass = findViewById(R.id.txtconpass);
        txtdl = findViewById(R.id.txtDl);
//        radiogender = findViewById(R.id.radioGroup);
//        radiogender.clearCheck();
        btnregister = findViewById(R.id.btnregister);
        spinner = findViewById(R.id.spinner);


        uploadUserDl = findViewById(R.id.uploadUserDl);
        Profilepic = findViewById(R.id.Profilepic);

//        authProfile = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        rdb_male = findViewById(R.id.rdb_male);
        rdb_female = findViewById(R.id.rdb_female);
        rdb_others = findViewById(R.id.rdb_others);

        radioGroup = findViewById(R.id.radioGroup);
        Fauth = FirebaseAuth.getInstance();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);

                if (radioButton != null) {
                    selectedOption = radioButton.getText().toString();
                    Toast.makeText(SignUp.this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
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
                            uploadUserDl.setImageURI(imageUri);

                        } else {
                            Toast.makeText(SignUp.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadUserDl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item, city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String value =parent.getItemAtPosition(position).toString();
//                Toast.makeText(SignUp.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    progressBar.setVisibility(View.VISIBLE);
                    String email = String.valueOf(txtemail.getText());
                    String password = String.valueOf(txtpass.getText());

                    Fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                                uploadUserDetails();
                            } else {
                                handleAuthException(task.getException());
                            }
                        }
                    });
                } else {
                    valid(); // It seems like you want to display a message here, not actually validate again
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser user = Fauth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(SignUp.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Email not sent" + e.getMessage());
                }
            });
        }
    }

    private void handleAuthException(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            Toast.makeText(SignUp.this, "Weak password, please choose a stronger one", Toast.LENGTH_SHORT).show();
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            Toast.makeText(SignUp.this, "Invalid email format", Toast.LENGTH_SHORT).show();
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            Toast.makeText(SignUp.this, "Email is already in use", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SignUp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadUserDetails() {
        // Upload user details to Firebase
        if (imageUri != null) {
            final StorageReference imageReference = storageReference.child("renters/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));

            imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Retrieve other user details
                            String Name = txtname.getText().toString();
                            String Email = txtemail.getText().toString();
                            String Pass = txtpass.getText().toString();
                            String Mobile = txtmobile.getText().toString();
                            String City = city[spinner.getSelectedItemPosition()];
                            String Dll = txtdl.getText().toString();
                            String Gender = selectedOption;

                            // Create HelperClass and upload to Firebase
                            ReadWriteUserDetails helperClass = new ReadWriteUserDetails(Name, Email, Pass, Mobile, City, Dll, Gender, uri.toString());
                            reference.child(Mobile).setValue(helperClass);

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUp.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(SignUp.this, Login.class);
//                            startActivity(intent);
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
                    Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(SignUp.this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    boolean check;

    boolean valid() {
        int selectedGenderid = radioGroup.getCheckedRadioButtonId();
        rdb_male = findViewById(selectedGenderid);

        String txtName = txtname.getText().toString();
        String txtMobile = txtmobile.getText().toString();
        String txtEmail = txtemail.getText().toString();
        String txtPass = txtpass.getText().toString();
        String txtConPass = txtconpass.getText().toString();
        String txtDll = txtdl.getText().toString();
        String City = city[spinner.getSelectedItemPosition()];

        String txtGender;

//        String mobileregex = "[6-9][0-9][9]";
//        String mobileregex = "^[6-9]\\d{9}$";
        String mobileregex="[6-9][0-9]{9}";
        Matcher mobilematcher;
        Pattern mobilepattern;
        mobilepattern = Pattern.compile(mobileregex);
        mobilematcher = mobilepattern.matcher(txtMobile);

        if (TextUtils.isEmpty(txtName)) {
            Toast.makeText(SignUp.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            txtname.setError("Name is required");
            txtname.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(txtMobile)) {
            Toast.makeText(SignUp.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
            txtmobile.setError("Mobile Number is required");
            txtmobile.requestFocus();
            check = false;
        } else if (txtMobile.length() != 10) {
            Toast.makeText(SignUp.this, "Please Re-Enter Mobile Number", Toast.LENGTH_SHORT).show();
            txtmobile.setError("Mobile no. should be 10 digits");
            txtmobile.requestFocus();
            check = false;
        } else if (!mobilematcher.find()) {
            Toast.makeText(SignUp.this, "Please Re-Enter Mobile Number", Toast.LENGTH_SHORT).show();
            txtmobile.setError("Mobile no. is not valid");
            txtmobile.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(txtEmail)) {
            Toast.makeText(SignUp.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            txtemail.setError("Email is required");
            txtemail.requestFocus();
            check = false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
            Toast.makeText(SignUp.this, "Please Re-Enter Email", Toast.LENGTH_SHORT).show();
            txtemail.setError("Valid Email is required");
            txtemail.requestFocus();}
        else if (TextUtils.isEmpty(txtPass)) {
            Toast.makeText(SignUp.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            txtpass.setError("Password is required");
            txtpass.requestFocus();
            check = false;
        } else if (txtPass.length() < 8) {
            Toast.makeText(SignUp.this, "Password should be at least 8 digits", Toast.LENGTH_SHORT).show();
            txtpass.setError("Password should be 8 digits");
            txtpass.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(txtConPass)) {
            Toast.makeText(SignUp.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            txtconpass.setError("Password is required");
            txtconpass.requestFocus();
            check = false;
        } else if (!txtPass.equals(txtConPass)) {
            Toast.makeText(SignUp.this, "Please enter right password", Toast.LENGTH_SHORT).show();
            txtpass.setError("Same password required");
            txtpass.requestFocus();
            txtpass.clearComposingText();
            txtconpass.clearComposingText();
            check = false;
        } else if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(SignUp.this, "Please select your gender", Toast.LENGTH_SHORT).show();
            rdb_male.setError("Gender is required");
            rdb_male.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(txtDll)) {
            Toast.makeText(SignUp.this, "Please Enter Driving license no.", Toast.LENGTH_SHORT).show();
            txtconpass.setError("Driving license no. is required");
            txtconpass.requestFocus();
            check = false;
        } else {
            txtGender = rdb_male.getText().toString();
//                    registeruser(txtName, txtMobile, txtEmail, txtPass, txtDll,City,txtGender);
            check = true;
        }

        return check;

    }
}
