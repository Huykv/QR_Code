package com.example.qr_test.ActivityQRScan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qr_test.Entity.Product;
import com.example.qr_test.MainActivity;
import com.example.qr_test.R;
import com.example.qr_test.api.API;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QR_Screen extends AppCompatActivity {


    private static final String TAG = "QR_Screen";
    Button scan_btn;

    TextView textView;

    TextView dangNhap;
    public static Product productScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_screen);

        scan_btn = findViewById(R.id.scanner);
        textView = findViewById(R.id.text);
        dangNhap = findViewById(R.id.dang_nhap);

        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QR_Screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(QR_Screen.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            String contents = intentResult.getContents();
            if(contents != null){
                Long product_id = Long.valueOf(contents.toString());
                API.API.getProduct(product_id).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        productScan = response.body();
                        if (productScan != null) {
                            Intent intent = new Intent(QR_Screen.this, ResultScan.class);
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}