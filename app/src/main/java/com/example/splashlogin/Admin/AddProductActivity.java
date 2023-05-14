package com.example.splashlogin.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.Models.UserModel;
import com.example.splashlogin.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class AddProductActivity extends AppCompatActivity {

    TextInputEditText productName, productPrice, productQuantity, productCategory, productType;
    ImageView imageUpload;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask storageTask;
    FirebaseFirestore firebaseFirestore;

    private Uri uri;
    private static final int PICK_IMAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        mStorageRef = FirebaseStorage.getInstance().getReference("Products");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
        firebaseFirestore = FirebaseFirestore.getInstance();

        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productQuantity = findViewById(R.id.productQuantity);
        productCategory = findViewById(R.id.productCategory);
        productType = findViewById(R.id.productType);
        imageUpload = findViewById(R.id.imageUpload);

        imageUpload.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE);
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data.getData()!=null) {
            uri = data.getData();
            imageUpload.setImageURI(uri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public void addProduct(View view) {

        String name = productName.getText().toString().trim();
        int price = Integer.parseInt(productPrice.getText().toString().trim());
        int quantity = Integer.parseInt(productQuantity.getText().toString().trim());
        String category = productCategory.getText().toString().trim();
        String type = productType.getText().toString().trim();

        if(uri != null && name != null && price != 0 && quantity != 0 && category != null && type != null) {
            StorageReference fileReference =
                    mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(uri));
            if(storageTask != null && storageTask.isInProgress()) {
                Toast.makeText(this, "Upload In Progress", Toast.LENGTH_SHORT).show();
            } else {
                storageTask = fileReference.putFile(uri)
                        .addOnSuccessListener(taskSnapshot -> {
                            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                ProductsModel productsModel = new ProductsModel(
                                        name,
                                        price,
                                        uri.toString(),
                                        quantity,
                                        category,
                                        type,
                                        fileReference.getName()
                                );
                                firebaseFirestore.collection("Products")
                                        .add(productsModel);

                                String uploadId = mDatabaseRef.push().getKey();
                                mDatabaseRef.child(uploadId).setValue(productsModel)
                                        .addOnSuccessListener(unused -> {
                                            Toast.makeText(AddProductActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AddProductActivity.this, ProductsActivity.class));
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                            });
                        })
                        .addOnFailureListener(e -> Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show())
                        .addOnProgressListener(snapshot -> {

                        });
            }
        } else {
            Toast.makeText(this, "No File Selected/Missing Fields", Toast.LENGTH_SHORT).show();
        }

    }
}