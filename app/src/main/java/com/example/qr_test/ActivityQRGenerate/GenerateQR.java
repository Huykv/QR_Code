package com.example.qr_test.ActivityQRGenerate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qr_test.MainActivity;
import com.example.qr_test.R;
import com.example.qr_test.ActivityStatistic.Statistic;
import com.example.qr_test.ActivityListProduct.ListTypeProductActivity;
import com.example.qr_test.Entity.TypeProduct;
import com.example.qr_test.Adapter.TypeProductAdapter;
import com.example.qr_test.api.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateQR extends AppCompatActivity {
    TextView danhSach;
    TextView thongKe;
    TextView dangXuat;
    private ListView lvTypeProduct;
    private ArrayList<TypeProduct> typeProductList;
    private List<String> tPs;
    public static String typeGenerateP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        getTPs();

        danhSach = findViewById(R.id.list);
        thongKe = findViewById(R.id.thongke);
        dangXuat = findViewById(R.id.logout);

        thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerateQR.this, Statistic.class);
                startActivity(intent);
                finish();
            }
        });

        danhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerateQR.this, ListTypeProductActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerateQR.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getTPs() {
        API.API.getTypeProducts().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                tPs = response.body();
                if (tPs != null) {
                    lvTypeProduct = findViewById(R.id.listViewTypeProduct);
                    typeProductList = new ArrayList<>();
                    for (String type:tPs) {
                        switch (type) {
                            case "Sữa":
                                typeProductList.add(new TypeProduct("Sữa", R.drawable.sua));
                                break;
                            case "Đồ ăn vặt":
                                typeProductList.add(new TypeProduct("Đồ ăn vặt", R.drawable.snack));
                                break;
                            case "Thực phẩm khô":
                                typeProductList.add(new TypeProduct("Thực phẩm khô", R.drawable.thuc_pham_kho));
                                break;
                            case "Nước giải khát":
                                typeProductList.add(new TypeProduct("Nước giải khát", R.drawable.nuoc_dong_chai));
                                break;
                            case "Kem":
                                typeProductList.add(new TypeProduct("Kem", R.drawable.kem));
                                break;
                            default:
                                typeProductList.add(new TypeProduct(type, R.drawable.back_button));
                                break;
                        }
                    }

                    TypeProductAdapter typeProductAdapter = new TypeProductAdapter(
                            GenerateQR.this,
                            R.layout.line_type_product,
                            typeProductList
                    );

                    lvTypeProduct.setAdapter(typeProductAdapter);

                    lvTypeProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent;
                            typeGenerateP = typeProductList.get(position).getName();
                            switch (typeGenerateP) {
                                case "Sữa":
                                    intent = new Intent(GenerateQR.this, Milk_QRGenerated.class);
                                    break;
                                case "Đồ ăn vặt":
                                    intent = new Intent(GenerateQR.this, Snack_QRGenerated.class);
                                    break;
                                case "Thực phẩm khô":
                                    intent = new Intent(GenerateQR.this, DryFood_QRGenerated.class);
                                    break;
                                case "Nước giải khát":
                                    intent = new Intent(GenerateQR.this, Beverage_QRGenerated.class);
                                    break;
                                case "Kem":
                                    intent = new Intent(GenerateQR.this, IceCream_QRGenerated.class);
                                    break;
                                default:
                                    intent = new Intent(GenerateQR.this, MainActivity.class);
                                    break;
                            }
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable throwable) {
                Toast.makeText(GenerateQR.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}