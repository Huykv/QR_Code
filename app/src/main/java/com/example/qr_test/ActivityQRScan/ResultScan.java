package com.example.qr_test.ActivityQRScan;

import static com.example.qr_test.ActivityQRScan.QR_Screen.productScan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_test.R;
import com.example.qr_test.api.API;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultScan extends AppCompatActivity {
    private static final String TAG = "ResultScan";
    Button btnBack;
    ImageView imgProduct;
    TextView tvName, tvPrice, tvflavor, tvbrand, tvorigin, tvingredient, tvdescription, tvpackage, tvunit, tvcapacity, tvweight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scan);

        initUI();
        setDataUI();

        API.API.scanQR(productScan.getId()).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (productScan.getId() == response.body()) {
                    Log.d(TAG, "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultScan.this, QR_Screen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initUI() {
        btnBack = findViewById(R.id.buttonBackScan);
        imgProduct = findViewById(R.id.imageResultScan);
        tvName = findViewById(R.id.textViewResultScanName);
        tvPrice = findViewById(R.id.textViewResultScanPrice);
        tvflavor = findViewById(R.id.textViewResultScanFlavor);
        tvbrand = findViewById(R.id.textViewResultScanBrand);
        tvorigin = findViewById(R.id.textViewResultScanOrigin);
        tvingredient = findViewById(R.id.textViewResultScanIngredient);
        tvdescription = findViewById(R.id.textViewResultScanDescription);
        tvpackage = findViewById(R.id.textViewResultScanPack);
        tvunit = findViewById(R.id.textViewResultScanUnit);
        tvcapacity = findViewById(R.id.textViewResultScanCapacity);
        tvweight = findViewById(R.id.textViewResultScanWeight);
    }

    public void setDataUI() {
        setImg();
        tvName.setText(productScan.getName());
        tvPrice.setText(productScan.getPrice().toString());
        tvflavor.setText("Hương vị: " + productScan.getFlavor());
        tvbrand.setText("Thương hiệu: " + productScan.getBrand());
        tvorigin.setText("Xuất xứ: " + productScan.getOrigin());
        tvingredient.setText("Thành phần: " + productScan.getIngredient());
        if (productScan.getDescription().equals("")) {
            tvdescription.setVisibility(View.GONE);
        } else {
            tvdescription.setText("Mô tả: " + productScan.getDescription());
        }
        if (productScan.getPack() == null) {
            tvpackage.setVisibility(View.GONE);
        } else {
            tvpackage.setText("Đóng gói: " + productScan.getPack());
        }
        if (productScan.getUnit() == null) {
            tvunit.setVisibility(View.GONE);
        } else {
            tvunit.setText("Đơn vị: " + productScan.getUnit());
        }
        if (productScan.getCapacity() == null) {
            tvcapacity.setVisibility(View.GONE);
        } else {
            tvcapacity.setText("Dung tích: " + productScan.getCapacity());
        }
        if (productScan.getWeight() == null) {
            tvweight.setVisibility(View.GONE);
        } else {
            tvweight.setText("Khối lượng: " + productScan.getWeight());
        }
    }

    public void setImg() {
        API.API.getImageProduct(productScan.getId()).enqueue(new Callback<ResponseBody>() {
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
                    imgProduct.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
