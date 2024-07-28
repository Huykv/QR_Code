package com.example.qr_test.ActivityListProduct;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_test.Entity.Product;
import com.example.qr_test.R;
import com.example.qr_test.api.API;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduct extends AppCompatActivity {
    private static final String TAG = "DetailProduct";
    ImageView imageP;
    TextView nameP, priceP, descriptionP, packageP, unitP, capacityP, weightP;
    EditText edtFlavor, edtBrand, edtOrigin, edtIngredient, edtDescription, edtPackage, edtUnit, edtCapacity, edtWeight;
    Button btnBack, btnDelete, btnEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initUI();
        getProduct();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProduct.this, ListProduct.class);
                startActivity(intent);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProduct.this, EditProduct.class);
                startActivity(intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    public void initUI() {
        imageP = findViewById(R.id.imageViewDetail);
        nameP = findViewById(R.id.textViewDetailName);
        priceP = findViewById(R.id.textViewDetailPrice);
        descriptionP = findViewById(R.id.textViewDetailDescription);
        packageP = findViewById(R.id.textViewDetailPack);
        unitP = findViewById(R.id.textViewDetailUnit);
        capacityP = findViewById(R.id.textViewDetailCapacity);
        weightP = findViewById(R.id.textViewDetailWeight);
        btnBack = findViewById(R.id.buttonBackDetail);
        btnDelete = findViewById(R.id.buttonDelete);
        btnEdit = findViewById(R.id.buttonEdit);


        edtFlavor = findViewById(R.id.editTextDetailFlavor);
        edtBrand = findViewById(R.id.editTextDetailBrand);
        edtOrigin = findViewById(R.id.editTextDetailOrigin);
        edtIngredient = findViewById(R.id.editTextDetailIngredient);
        edtDescription = findViewById(R.id.editTextDetailDescription);
        edtPackage = findViewById(R.id.editTextDetailPack);
        edtUnit = findViewById(R.id.editTextDetailUnit);
        edtCapacity = findViewById(R.id.editTextDetailCapacity);
        edtWeight = findViewById(R.id.editTextDetailWeight);
    }

    public void getProduct() {
        API.API.getProduct(ListProduct.product.getId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                if (product != null) {
                    setImageP(imageP, ListProduct.product.getId());
                    nameP.setText(product.getName());
                    priceP.setText(product.getPrice().toString() + " VND");
                    edtFlavor.setText(product.getFlavor());
                    edtBrand.setText(product.getBrand());
                    edtOrigin.setText(product.getOrigin());
                    edtIngredient.setText(product.getIngredient());
                    if (product.getDescription().equals("")) {
                        descriptionP.setVisibility(View.GONE);
                        edtDescription.setVisibility(View.GONE);
                    } else {
                        edtDescription.setText(product.getDescription());
                    }
                    if (product.getPack() == null) {
                        packageP.setVisibility(View.GONE);
                        edtPackage.setVisibility(View.GONE);
                    } else {
                        edtPackage.setText(product.getPack());
                    }
                    if (product.getUnit() == null) {
                        unitP.setVisibility(View.GONE);
                        edtUnit.setVisibility(View.GONE);
                    } else {
                        edtUnit.setText( product.getUnit());
                    }
                    if (product.getCapacity() == null) {
                        capacityP.setVisibility(View.GONE);
                        edtCapacity.setVisibility(View.GONE);
                    } else {
                        edtCapacity.setText(product.getCapacity());
                    }
                    if (product.getWeight() == null) {
                        weightP.setVisibility(View.GONE);
                        edtWeight.setVisibility(View.GONE);
                    } else {
                        edtWeight.setText(product.getWeight());
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    public void setImageP(ImageView imageP, long id) {
        API.API.getImageProduct(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] image_data;
                try {
                    image_data = response.body().bytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (image_data != null) {
                    // Convert byte array to Bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image_data, 0, image_data.length);
                    // Set the Bitmap to the ImageView
                    imageP.setImageBitmap(bitmap);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void showConfirmationDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custome_alert_dialog, null);
        TextView messageTextView = dialogView.findViewById(R.id.message);
        Button positiveButton = dialogView.findViewById(R.id.positive_button);
        Button negativeButton = dialogView.findViewById(R.id.negative_button);

        messageTextView.setText("Bạn có chắc chắn muốn xoá sản phẩm này không?");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.API.deleteProduct(ListProduct.product.getId()).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Toast.makeText(DetailProduct.this, "Xoá thành công sản phẩm", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailProduct.this, ListProduct.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
