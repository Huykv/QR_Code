package com.example.qr_test.ActivityListProduct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProduct extends AppCompatActivity {
    private static final String TAG = "EditProduct";
    ImageView image;
    Button btnCancel, btnSave;
    TextView descriptionP, packageP, unitP, capacityP, weightP;
    EditText edtName, edtPrice, edtFlavor, edtBrand, edtOrigin, edtIngredient, edtDescription, edtPackage, edtUnit, edtCapacity, edtWeight;
    Uri imgUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        initUI();
        getProduct();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProduct.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgUri != null) {
                    File file = new File(imgUri.getPath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                    API.API.uploadImage(body, ListProduct.product.getId()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d(TAG, "onResponse: img");
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable throwable) {
                            Log.d(TAG, "onFailure: ");
                        }
                    });
                }
                
                Product product = new Product(
                        edtName.getText().toString(),
                        ListProduct.product.getType(),
                        edtBrand.getText().toString(),
                        edtOrigin.getText().toString(),
                        Double.parseDouble(edtPrice.getText().toString()),
                        edtFlavor.getText().toString(),
                        edtIngredient.getText().toString(),
                        edtDescription.getText().toString(),
                        edtPackage.getText().toString(),
                        edtUnit.getText().toString(),
                        edtCapacity.getText().toString(),
                        edtWeight.getText().toString()
                );
                switch (product.getType()) {
                    case "Nước giải khát":
                        API.API.updateBeverage(ListProduct.product.getId(), product).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                Toast.makeText(EditProduct.this, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable throwable) {
                                Log.d(TAG, "onFailure: ");
                            }
                        });
                        break;
                    case "Thực phẩm khô":
                        API.API.updateDryFood(ListProduct.product.getId(), product).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                Toast.makeText(EditProduct.this, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable throwable) {
                                Log.d(TAG, "onFailure: ");
                            }
                        });
                        break;
                    case "Kem":
                        API.API.updateIceCream(ListProduct.product.getId(), product).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                Toast.makeText(EditProduct.this, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable throwable) {
                                Log.d(TAG, "onFailure: ");
                            }
                        });
                        break;
                    case "Sữa":
                        API.API.updateMilk(ListProduct.product.getId(), product).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                Toast.makeText(EditProduct.this, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable throwable) {
                                Log.d(TAG, "onFailure: ");
                            }
                        });
                        break;
                    case "Đồ ăn vặt":
                        API.API.updateSnack(ListProduct.product.getId(), product).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                Toast.makeText(EditProduct.this, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable throwable) {
                                Log.d(TAG, "onFailure: ");
                            }
                        });
                        break;
                    default:
                        Log.d(TAG, "Find type product fail");
                        break;
                }

                Intent intent = new Intent(EditProduct.this, ListProduct.class);
                startActivity(intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProduct.this, DetailProduct.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgUri = data.getData();
        image.setImageURI(imgUri);
    }

    public void initUI() {
        image = findViewById(R.id.imageViewEdit);
        descriptionP = findViewById(R.id.textViewEditDescription);
        packageP = findViewById(R.id.textViewEditPack);
        unitP = findViewById(R.id.textViewEditUnit);
        capacityP = findViewById(R.id.textViewEditCapacity);
        weightP = findViewById(R.id.textViewEditWeight);
        btnCancel = findViewById(R.id.buttonCancel);
        btnSave = findViewById(R.id.buttonSave);


        edtName = findViewById(R.id.editTextEditName);
        edtPrice = findViewById(R.id.editTextEditPrice);
        edtFlavor = findViewById(R.id.editTextEditFlavor);
        edtBrand = findViewById(R.id.editTextEditBrand);
        edtOrigin = findViewById(R.id.editTextEditOrigin);
        edtIngredient = findViewById(R.id.editTextEditIngredient);
        edtDescription = findViewById(R.id.editTextEditDescription);
        edtPackage = findViewById(R.id.editTextEditPack);
        edtUnit = findViewById(R.id.editTextEditUnit);
        edtCapacity = findViewById(R.id.editTextEditCapacity);
        edtWeight = findViewById(R.id.editTextEditWeight);
    }

    public void getProduct() {
        API.API.getProduct(ListProduct.product.getId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                if (product != null) {
                    setImageP(image, ListProduct.product.getId());
                    edtName.setText(product.getName());
                    edtPrice.setText(product.getPrice().toString());
                    edtFlavor.setText(product.getFlavor());
                    edtBrand.setText(product.getBrand());
                    edtOrigin.setText(product.getOrigin());
                    edtIngredient.setText(product.getIngredient());
                    edtDescription.setText(product.getDescription());
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
                        edtUnit.setText(product.getUnit());
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
                Log.d(TAG, image_data.toString());
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

}
