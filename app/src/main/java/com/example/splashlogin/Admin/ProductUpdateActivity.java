package com.example.splashlogin.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProductUpdateActivity extends AppCompatActivity {

    TextInputEditText productName, productPrice, productQuantity, productCategory, productType;
    ImageView image;

    private Uri uri;
    private static final int PICK_IMAGE=1;
    String key;
    String storageKey;
    String imageUrl;

    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);



        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        int price = extras.getInt("price", 0);
        int quantity = extras.getInt("quantity", 0);
        String category = extras.getString("category");
        String type = extras.getString("type");
        key = extras.getString("key");
        storageKey = extras.getString("storageKey");
        imageUrl = extras.getString("imageUrl");

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Products").child(storageKey);


        image = findViewById(R.id.image);
        productName = findViewById(R.id.name);
        productPrice = findViewById(R.id.price);
        productQuantity = findViewById(R.id.quantity);
        productCategory = findViewById(R.id.category);
        productType = findViewById(R.id.type);


        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(image);
        productName.setText(name);
        productPrice.setText(price + "");
        productQuantity.setText(quantity + "");
        productCategory.setText(category);
        productType.setText(type);

        image.setOnClickListener(view -> {
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
            image.setImageURI(uri);
        }
    }

    public void updateProduct(View view) {
        String name = productName.getText().toString().trim();
        int price = Integer.parseInt(productPrice.getText().toString().trim());
        int quantity = Integer.parseInt(productQuantity.getText().toString().trim());
        String category = productType.getText().toString().trim();
        String type = productType.getText().toString().trim();

        if(uri != null) {
            if(name != null && price != 0 && quantity != 0 && category != null && type != null) {
                storageReference.putFile(uri)
                        .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            ProductsModel productsModel = new ProductsModel();
                            productsModel.setProductName(name);
                            productsModel.setProductPrice(price);
                            productsModel.setCategory(category);
                            productsModel.setQuantity(quantity);
                            productsModel.setType(type);
                            productsModel.setImg(uri.toString());
                            productsModel.setmKey(databaseReference.getKey());
                            productsModel.setStorageKey(storageReference.getName());
                            databaseReference.child(key).setValue(productsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProductUpdateActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ProductUpdateActivity.this, ProductsActivity.class));
                                }
                            }).addOnFailureListener(e -> Toast.makeText(ProductUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                        }).addOnFailureListener(e -> Toast.makeText(ProductUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()))
                        .addOnFailureListener(e -> Toast.makeText(ProductUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        } else {
            if(name != null && price != 0 && quantity != 0 && category != null && type != null) {
                ProductsModel productsModel = new ProductsModel();
                productsModel.setProductName(name);
                productsModel.setProductPrice(price);
                productsModel.setQuantity(quantity);
                productsModel.setCategory(category);
                productsModel.setType(type);
                if(uri != null) {
                    productsModel.setImg(uri.toString());
                }
                else {
                    productsModel.setImg(imageUrl);
                }
                productsModel.setmKey(databaseReference.getKey());
                productsModel.setStorageKey(storageReference.getName());
                databaseReference.child(key).setValue(productsModel).addOnSuccessListener(unused ->
                        Toast.makeText(ProductUpdateActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(ProductUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                startActivity(new Intent(ProductUpdateActivity.this, ProductsActivity.class));
            } else {
                Toast.makeText(this, "Please Provide All Fields", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void removeProduct(View view) {
        StorageReference imageRef = firebaseStorage.getReferenceFromUrl(imageUrl);
        imageRef.delete().addOnSuccessListener(unused -> {
            databaseReference.child(key).removeValue();
            Toast.makeText(ProductUpdateActivity.this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProductUpdateActivity.this, ProductsActivity.class));
        }).addOnFailureListener(e -> Toast.makeText(ProductUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

}