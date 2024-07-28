package com.example.qr_test.ActivityListProduct;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_test.Adapter.TypeProductAdapter;
import com.example.qr_test.Entity.TypeProduct;
import com.example.qr_test.ActivityQRGenerate.GenerateQR;
import com.example.qr_test.MainActivity;
import com.example.qr_test.R;
import com.example.qr_test.ActivityStatistic.Statistic;
import com.example.qr_test.api.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTypeProductActivity extends AppCompatActivity {
    private static final String TAG = "ListTypeProductActivity";
    private ListView lvTypeProduct;
    private ArrayList<TypeProduct> typeProductList;
    private List<String> tPs;
    public static String nowType = "";

    TextView quetMa;
    TextView thongKe;
    TextView dangXuat;

    public ListTypeProductActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        getTPs();

        quetMa = findViewById(R.id.tao_ma);
        thongKe = findViewById(R.id.thongke);
        dangXuat= findViewById(R.id.logout);
        quetMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTypeProductActivity.this, GenerateQR.class);
                startActivity(intent);
                finish();
            }
        });

        thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTypeProductActivity.this, Statistic.class);
                startActivity(intent);
                finish();
            }
        });

        dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTypeProductActivity.this, MainActivity.class);
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
                            ListTypeProductActivity.this,
                            R.layout.line_type_product,
                            typeProductList
                    );

                    lvTypeProduct.setAdapter(typeProductAdapter);

                    lvTypeProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            switch (typeProductList.get(position).getName()) {
                                case "Nước giải khát":
                                    nowType = "beverage";
                                    break;
                                case "Thực phẩm khô":
                                    nowType = "dry_food";
                                    break;
                                case "Kem":
                                    nowType = "ice_cream";
                                    break;
                                case "Sữa":
                                    nowType = "milk";
                                    break;
                                case "Đồ ăn vặt":
                                    nowType = "snack";
                                    break;
                            }
                            Intent intent = new Intent(ListTypeProductActivity.this, ListProduct.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
