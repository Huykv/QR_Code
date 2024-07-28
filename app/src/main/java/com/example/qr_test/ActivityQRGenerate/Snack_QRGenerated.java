package com.example.qr_test.ActivityQRGenerate;

import static com.example.qr_test.ActivityQRGenerate.GenerateQR.typeGenerateP;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.qr_test.Entity.Snack;
import com.example.qr_test.R;
import com.example.qr_test.api.API;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Snack_QRGenerated extends AppCompatActivity {
    public static final String TAG = "Snack_QRGenerated";
    Button backButton, buttonSelectImg, buttonIn;
    EditText editThuongHieuSP, editMoTaSP, editThanhPhanSP, editTenSP, editXuatSuSP, editHuongViSP, editGiaThanhSP, editKhoiLuongSP;
    CardView cardView, cardViewQR;
    ImageView imageViewP;
    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_qrgenerated);

        initUI();

        Button button = findViewById(R.id.buttonGenerate);
        ImageView imageView = findViewById(R.id.qr_code);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Snack_QRGenerated.this, GenerateQR.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(Snack_QRGenerated.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTenSP.getText().toString();
                String brand = editThuongHieuSP.getText().toString();
                String description = editMoTaSP.getText().toString();
                String ingredient = editThanhPhanSP.getText().toString();
                String origin = editXuatSuSP.getText().toString();
                Double price = Double.valueOf(editGiaThanhSP.getText().toString());
                String weight = editKhoiLuongSP.getText().toString();
                String flavor = editHuongViSP.getText().toString();

                Snack newSnack = new Snack(name, typeGenerateP, brand, origin, price, flavor, ingredient, description, weight);

                API.API.createSnack(newSnack).enqueue(new Callback<Snack>() {
                    @Override
                    public void onResponse(Call<Snack> call, Response<Snack> response) {
                        if (response.body() != null) {
                            Long idProduct = response.body().getId();
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            try {
                                BitMatrix bitMatrix = multiFormatWriter.encode(idProduct.toString(), BarcodeFormat.QR_CODE,300,300);

                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                                imageView.setImageBitmap(bitmap);
                                cardViewQR.setVisibility(View.VISIBLE);
                                buttonIn.setVisibility(View.VISIBLE);
                            }catch (WriterException e){
                                throw  new RuntimeException(e);
                            }
                            if (imgUri != null) {
                                File file = new File(imgUri.getPath());
                                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                                API.API.uploadImage(body, idProduct).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Log.d(TAG, "onResponse: img");
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable throwable) {
                                        Log.d(TAG, "onFailure: img");
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Snack> call, Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                    }
                });


            }
        });
    }

    private void initUI() {
        backButton = findViewById(R.id.buttonBackGenerate);
        editThuongHieuSP = findViewById(R.id.snack_thuongHieu);
        editMoTaSP   = findViewById(R.id.snack_moTaSP);
        editThanhPhanSP   = findViewById(R.id.snack_thanhPhan);
        editTenSP   = findViewById(R.id.snack_tenSP);
        editXuatSuSP   = findViewById(R.id.snack_xuatsuSP);
        editHuongViSP = findViewById(R.id.snack_huongVi);
        editGiaThanhSP = findViewById(R.id.snack_giaThanh);
        editKhoiLuongSP = findViewById(R.id.snack_khoiLuong);
        buttonSelectImg = findViewById(R.id.buttonSelectImg);
        imageViewP = findViewById(R.id.imageViewGenerate);
        cardView = findViewById(R.id.cardView);
        cardViewQR = findViewById(R.id.cardViewQR);
        buttonIn = findViewById(R.id.buttonIn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgUri = data.getData();
        imageViewP.setImageURI(imgUri);
        cardView.setVisibility(View.VISIBLE);
    }
}