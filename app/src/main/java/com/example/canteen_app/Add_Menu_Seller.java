package com.example.canteen_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Add_Menu_Seller extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadFoodName, uploadFoodPrice, uploadVariant1, uploadVariant2;
    String imageURL;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_seller);

        uploadImage=findViewById(R.id.uploadImg);
        saveButton = findViewById(R.id.saveButton);
        uploadFoodName = findViewById(R.id.foodNameEditText);
        uploadFoodPrice = findViewById(R.id.foodPriceEditText);
        uploadVariant1 = findViewById(R.id.variant1EditText);
        uploadVariant2 = findViewById(R.id.variant2EditText);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(Add_Menu_Seller.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
            public void saveData() {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                        .child(uri.getLastPathSegment());

                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Menu_Seller.this);
                builder.setCancelable(false);
//            builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri urlImage = uriTask.getResult();
                        imageURL = urlImage.toString();
                        uploadData();
                        dialog.dismiss();
                    }
//
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                    }
                });
            }

            public void uploadData() {
                String name = uploadFoodName.getText().toString();
                String price = uploadFoodPrice.getText().toString();
                String var1 = uploadVariant1.getText().toString();
                String var2 = uploadVariant2.getText().toString();

                DataClass dataClass = new DataClass(imageURL, name, price, var1, var2);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("/Canteen2/Seller/Menu").document(name).set(dataClass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Add_Menu_Seller.this, "Saved", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Add_Menu_Seller.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Add_Menu_Seller.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }
}