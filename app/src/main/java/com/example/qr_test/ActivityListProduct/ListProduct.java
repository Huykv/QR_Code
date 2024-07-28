package com.example.qr_test.ActivityListProduct;

import static com.example.qr_test.ActivityListProduct.ListTypeProductActivity.nowType;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_test.Adapter.ProductAdapter;
import com.example.qr_test.Entity.Product;
import com.example.qr_test.R;
import com.example.qr_test.api.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProduct extends AppCompatActivity {

    private static final String TAG = "ListProduct";
    private ListView lvProduct;
    private List<Product> products;
    private Button buttonBack;
    public static Product product = new Product();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        getPs();
        buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListProduct.this, ListTypeProductActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void getPs() {
        API.API.getProducts(nowType).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();
                if (products != null) {
                    lvProduct = findViewById(R.id.listViewProduct);
                    ProductAdapter productAdapter = new ProductAdapter(
                            ListProduct.this,
                            R.layout.line_product,
                            products
                    );

                    lvProduct.setAdapter(productAdapter);

                    lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            product = products.get(position);
                            Intent intent = new Intent(ListProduct.this, DetailProduct.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
